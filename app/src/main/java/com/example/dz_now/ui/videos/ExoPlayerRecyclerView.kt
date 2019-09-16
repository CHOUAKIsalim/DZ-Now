package com.example.dz_now.ui.videos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.bumptech.glide.RequestManager
import com.example.dz_now.R
import com.example.dz_now.entites.Article
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.util.ArrayList
import java.util.Objects


class ExoPlayerRecyclerView(private var myContext: Context, private val attrs: AttributeSet? = null) : RecyclerView(myContext,attrs) {

    private val TAG : String = "ExoPlayerRecyclerView"
    private val AppName: String = "DZ-NOW"

    /**
     * PlayerViewHolder UI component
     * Watch PlayerViewHolder class
     */
    private var mediaCoverImage: ImageView? = null
    private var volumeControl : ImageView? = null
    private var progressBar: ProgressBar? = null
    private var viewHolderParent : View? = null
    private var mediaContainer: FrameLayout? = null
    private var videoSurfaceView: PlayerView? = null
    private var videoPlayer: SimpleExoPlayer? = null

    /**
     * variable declaration
     */
    // Media List
    private var mediaObjects: List<Article>  = ArrayList()
    private var videoSurfaceDefaultHeight: Int  = 0
    private var screenDefaultHeight: Int = 0
    private var playPosition: Int  = -1
    private var isVideoViewAdded: Boolean? = null
    private var requestManager: RequestManager? = null
    // controlling volume state
    private var volumeState: VolumeState? = null

    private val videoViewClickListener : OnClickListener = OnClickListener() {
        @Override
        fun onClick(v: View) {
            toggleVolume()
        }
    }

    init {
        init(myContext)
    }


    private fun init(context: Context) {
        this.myContext = context.applicationContext
        val display: Display = ( Objects.requireNonNull(
            getContext().getSystemService(Context.WINDOW_SERVICE)) as WindowManager).defaultDisplay
        val point : Point = Point()
        display.getSize(point)

        videoSurfaceDefaultHeight = point.x
        screenDefaultHeight = point.y

        videoSurfaceView = PlayerView(this.context)
        videoSurfaceView!!.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory =
            AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector =
            DefaultTrackSelector(videoTrackSelectionFactory)

        //Create the player using ExoPlayerFactory
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Disable Player Control
        videoSurfaceView!!.setUseController(false)
        // Bind the player to the view.
        videoSurfaceView!!.setPlayer(videoPlayer)
        // Turn on Volume
        setVolumeControl(VolumeState.ON)

        addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView , newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mediaCoverImage != null) {
                        // show the old thumbnail
                        mediaCoverImage!!.setVisibility(VISIBLE)
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo(true)
                    } else {
                        playVideo(false)
                    }
                }
            }
        })

        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {

            override fun onChildViewAttachedToWindow(@NonNull view: View ) {

            }

            override fun onChildViewDetachedFromWindow(@NonNull view: View) {
                if (viewHolderParent != null && viewHolderParent!! == view) {
                    resetVideoView()
                }
            }
        })

        videoPlayer?.addListener(object:  Player.EventListener {

            override fun onTimelineChanged(timeline: Timeline, @Nullable manifest: Any?, reason: Int) {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray,
                                         trackSelections: TrackSelectionArray) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING ->
                        if (progressBar != null) {
                            progressBar!!.visibility = VISIBLE
                        }

                    Player.STATE_ENDED ->
                        videoPlayer!!.seekTo(0)

                    Player.STATE_READY -> {
                        if (progressBar != null) {
                            progressBar!!.visibility = GONE
                        }
                        if (isVideoViewAdded==null || !isVideoViewAdded!!) {
                            addVideoView()
                        }
                    }
                }
            }


            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onPlayerError(error: ExoPlaybackException) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

            }

            override fun onSeekProcessed() {

            }
        })
    }

    fun playVideo(isEndOfList: Boolean) {

        var targetPosition: Int? = null

        if (!isEndOfList) {
            val startPosition : Int = ( Objects.requireNonNull(
                layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()

            var endPosition: Int = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                val startPositionVideoHeight: Int = getVisibleVideoSurfaceHeight(startPosition)
                val endPositionVideoHeight: Int = getVisibleVideoSurfaceHeight(endPosition)

                val tmp : Boolean = startPositionVideoHeight > endPositionVideoHeight
                targetPosition = if (tmp) startPosition else endPosition
            } else {
                targetPosition = startPosition
            }
        } else {
            targetPosition = mediaObjects.size - 1
        }

        // video is already playing so return
        if (targetPosition == playPosition) {
            return
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition
        if (videoSurfaceView == null) {
            return
        }

        // remove any old surface views from previously playing videos
        videoSurfaceView!!.visibility = INVISIBLE
        removeVideoView(videoSurfaceView!!)

        val currentPosition: Int =
            targetPosition - (Objects.requireNonNull(
                layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()

        val child: View = getChildAt(currentPosition) ?: return

        val holder: PlayerViewHolder =  child.tag as PlayerViewHolder
        if (holder == null) {
            playPosition = -1
            return
        }
        mediaCoverImage = holder.mediaCoverImage
        progressBar = holder.progressBar
        volumeControl = holder.volumeControl
        viewHolderParent = holder.itemView
        requestManager = holder.requestManager
        mediaContainer = holder.mediaContainer

        videoSurfaceView!!.player = videoPlayer
        viewHolderParent!!.setOnClickListener(videoViewClickListener)

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, AppName))


        val mExtractor = @SuppressLint("StaticFieldLeak")
        object : YouTubeExtractor(context) {
            override fun onExtractionComplete(
                sparseArray: SparseArray<YtFile>?,
                videoMeta: VideoMeta
            ) {
                if (sparseArray != null) {
                    val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                        context, Util.getUserAgent(context, AppName))
                    if (sparseArray != null) {
                        val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(sparseArray.get(sparseArray.keyAt(0)).url))
                        videoPlayer?.prepare(videoSource)
                        videoPlayer?.playWhenReady = true
                    }
                }
            }
        }

        if(mediaObjects[targetPosition].video !=null && mediaObjects[targetPosition].video !="") {
            val mediaUrl: String = mediaObjects[targetPosition].video
            mExtractor.extract(mediaUrl, true, true)
        }

    }



    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     */
    private fun getVisibleVideoSurfaceHeight(playPosition: Int): Int {
        val at: Int  = playPosition - ( Objects.requireNonNull(
            layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()


        val child: View = getChildAt(at) ?: return 0

        val location = IntArray(2)
        child.getLocationInWindow(location)

        return if (location[1] < 0) {
            location[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location[1]
        }
    }

    // Remove the old player
    private fun removeVideoView(videoView : PlayerView) {
        if(videoView!=null && videoView.parent !=null)
        {
            val parent: ViewGroup = videoView.parent as ViewGroup

            val index : Int = parent.indexOfChild(videoView)
            if (index >= 0) {
                parent.removeViewAt(index)
                isVideoViewAdded = false
                viewHolderParent?.setOnClickListener(null)
            }

        }
    }

    private fun addVideoView() {
        mediaContainer?.addView(videoSurfaceView)
        isVideoViewAdded = true
        videoSurfaceView?.requestFocus()
        videoSurfaceView?.visibility = VISIBLE
        videoSurfaceView?.alpha = 1.0F
        mediaCoverImage?.visibility = GONE
    }

    private fun resetVideoView() {
        if (isVideoViewAdded!=null && isVideoViewAdded!!) {
            removeVideoView(videoSurfaceView!!)
            playPosition = -1
            videoSurfaceView!!.visibility = INVISIBLE
            mediaCoverImage?.visibility = VISIBLE
        }
    }


    fun releasePlayer() {
        if (videoPlayer != null) {
            videoPlayer!!.release()
            videoPlayer = null
        }
        viewHolderParent = null
    }

    fun onPausePlayer() {
        videoPlayer?.stop(true)
    }

    private fun toggleVolume() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                setVolumeControl(VolumeState.ON)
            } else if (volumeState == VolumeState.ON) {
                setVolumeControl(VolumeState.OFF)
            }
        }
    }

    private fun setVolumeControl(state: VolumeState) {
        volumeState = state
        if (state == VolumeState.OFF) {
            videoPlayer?.volume = 0f
            animateVolumeControl()
        } else if (state == VolumeState.ON) {
            videoPlayer?.volume = 1f
            animateVolumeControl()
        }
    }

    private fun animateVolumeControl() {
        if (volumeControl != null) {
            volumeControl!!.bringToFront()
            if (volumeState == VolumeState.OFF) {
                requestManager?.load(R.drawable.ic_volume_off)
                    ?.into(volumeControl!!)
            } else if (volumeState == VolumeState.ON) {
                requestManager?.load(R.drawable.ic_volume_on)
                    ?.into(volumeControl!!)
            }
            volumeControl!!.animate().cancel()

            volumeControl!!.alpha = 1f

            volumeControl!!.animate()
                .alpha(0f)
                .setDuration(600).startDelay = 1000
        }
    }

    fun setMediaObjects(mediaObjects : List<Article> ) {
        this.mediaObjects = mediaObjects
    }

    /**
     * Volume ENUM
     */
    private enum class VolumeState {
        ON, OFF
    }
}