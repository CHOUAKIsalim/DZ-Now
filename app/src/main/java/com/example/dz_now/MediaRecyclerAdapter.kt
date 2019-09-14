package com.example.dz_now

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import java.util.ArrayList

/**
 * Created by Morris on 03,June,2019
 */
class MediaRecyclerAdapter(private val mediaObjects : ArrayList<MediaObject> ,private val requestManager: RequestManager ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



  @NonNull
  override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup , i: Int) : RecyclerView.ViewHolder {
    return PlayerViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.video_list_item, viewGroup, false))
  }

  override fun onBindViewHolder(@NonNull viewHolder: RecyclerView.ViewHolder , i: Int) {
    ( viewHolder as PlayerViewHolder).onBind(this.mediaObjects[i], requestManager)
  }

  override fun getItemCount() : Int {
    return mediaObjects.size
  }
}