package com.example.dz_now.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dz_now.ExoPlayerRecyclerView
import com.example.dz_now.R
import com.example.dz_now.MediaRecyclerAdapter
import com.example.dz_now.MediaObject
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.RequestManager
import android.os.Looper
import android.os.Handler


class VideosFragment : Fragment() {

    //Reference to recyclerview
    private var recyclerView: ExoPlayerRecyclerView? = null

    private val mediaObjectList : ArrayList<MediaObject> = ArrayList()
    private var mAdapter: MediaRecyclerAdapter? = null
    private var firstTime = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_videos, container, false)

        recyclerView = root.findViewById(R.id.exoPlayerRecyclerView) as ExoPlayerRecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val dividerDrawable = ContextCompat.getDrawable(activity!!, R.drawable.divider_drawable)
        recyclerView!!.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        //prepare content
        prepareVideoList()

        //set data object
        recyclerView!!.setMediaObjects(mediaObjectList)
        mAdapter = MediaRecyclerAdapter(mediaObjectList, initGlide())

        recyclerView!!.adapter = mAdapter

        if(firstTime) {
            Handler(Looper.getMainLooper()).post(Runnable { recyclerView!!.playVideo(false) })
            firstTime = false
        }

        return root
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    private fun prepareVideoList() {
        val mediaObject = MediaObject()
        mediaObject.setId(1)
        mediaObject.setUserHandle("@h.pandya")
        mediaObject.setTitle(
            "Do you think the concept of marriage will no longer exist in the future?"
        )
        mediaObject.setCoverUrl(
            "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-1.png"
        )
        mediaObject.setUrl("https://androidwave.com/media/androidwave-video-1.mp4")

        val mediaObject2 = MediaObject()
        mediaObject2.setId(2)
        mediaObject2.setUserHandle("@hardik.patel")
        mediaObject2.setTitle(
            "If my future husband doesn't cook food as good as my mother should I scold him?"
        )
        mediaObject2.setCoverUrl(
            "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-2.png"
        )
        mediaObject2.setUrl("https://androidwave.com/media/androidwave-video-2.mp4")

        val mediaObject3 = MediaObject()
        mediaObject3.setId(3)
        mediaObject3.setUserHandle("@arun.gandhi")
        mediaObject3.setTitle("Give your opinion about the Ayodhya temple controversy.")
        mediaObject3.setCoverUrl(
            "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-3.png"
        )
        mediaObject3.setUrl("https://androidwave.com/media/androidwave-video-3.mp4")

        val mediaObject4 = MediaObject()
        mediaObject4.setId(4)
        mediaObject4.setUserHandle("@sachin.patel")
        mediaObject4.setTitle("When did kama founders find sex offensive to Indian traditions")
        mediaObject4.setCoverUrl(
            "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-4.png"
        )
        mediaObject4.setUrl("https://androidwave.com/media/androidwave-video-6.mp4")

        val mediaObject5 = MediaObject()
        mediaObject5.setId(5)
        mediaObject5.setUserHandle("@monika.sharma")
        mediaObject5.setTitle("When did you last cry in front of someone?")
        mediaObject5.setCoverUrl(
            "https://androidwave.com/media/images/exo-player-in-recyclerview-in-android-5.png"
        )
        mediaObject5.setUrl("https://androidwave.com/media/androidwave-video-5.mp4")

        mediaObjectList.add(mediaObject)
        mediaObjectList.add(mediaObject2)
        mediaObjectList.add(mediaObject3)
        mediaObjectList.add(mediaObject4)
        mediaObjectList.add(mediaObject5)
        mediaObjectList.add(mediaObject)
        mediaObjectList.add(mediaObject2)
        mediaObjectList.add(mediaObject3)
        mediaObjectList.add(mediaObject4)
        mediaObjectList.add(mediaObject5)
    }
}