package com.example.dz_now.ui.videos

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.dz_now.R
import com.example.dz_now.entites.Article


class PlayerViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * below view have public modifier because
     * we have access PlayerViewHolder inside the ExoPlayerRecyclerView
     */
    var mediaContainer: FrameLayout? = null
    var mediaCoverImage: ImageView? = null
    var volumeControl: ImageView? = null
    var progressBar: ProgressBar? = null
    var requestManager: RequestManager? = null
    private var title: TextView? = null
    private var userHandle: TextView? = null
    private var parent: View? = null

    init {
        parent = itemView
        mediaContainer = itemView.findViewById(R.id.mediaContainer)
        mediaCoverImage = itemView.findViewById(R.id.ivMediaCoverImage)
        title = itemView.findViewById(R.id.tvTitle)
        userHandle = itemView.findViewById(R.id.tvUserHandle)
        progressBar = itemView.findViewById(R.id.progressBar)
        volumeControl = itemView.findViewById(R.id.ivVolumeControl)
    }

    fun onBind(mediaObject: Article, requestManager: RequestManager) {
        this.requestManager = requestManager
        parent?.tag = this
        title?.text = mediaObject.title
        userHandle?.text = mediaObject.source
        this.requestManager!!
            .load(mediaObject.image)
            .into(mediaCoverImage!!)
    }
}