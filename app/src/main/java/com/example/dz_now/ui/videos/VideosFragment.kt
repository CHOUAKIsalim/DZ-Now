package com.example.dz_now.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.RequestManager
import android.os.Looper
import android.os.Handler
import android.util.Log
import com.example.dz_now.*
import com.example.dz_now.entites.Article
import com.example.dz_now.services.ArticleService
import com.example.dz_now.services.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class VideosFragment : Fragment() {

    //Reference to recyclerview
    private var recyclerView: ExoPlayerRecyclerView? = null

    //The service to get the categories
    private lateinit var articleApi: ArticleService

    // Observer
    private lateinit var compositeDisposable: CompositeDisposable

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

        //Init the api
        val retrofit = RetrofitClient.instance
        articleApi = retrofit.create(ArticleService::class.java)

        compositeDisposable = CompositeDisposable()

        getArticles()

        return root
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    private fun getArticles() {
        compositeDisposable.add(articleApi.articlesWithVideos
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{articles->displayArticles(articles)}
        )
    }

    private fun displayArticles(articles : List<Article>) {
        articles.forEach{
            if(it.video !=null && it.video != "")
            {
                val mediaObject = MediaObject()
                mediaObject.setId(it.id)
                mediaObject.setUserHandle(it.source)
                mediaObject.setTitle(it.title)
                mediaObject.setCoverUrl(it.image)
                mediaObject.setUrl(it.video+".mp4")
                mediaObjectList.add(mediaObject)
            }
        }
        //set data object
        recyclerView!!.setMediaObjects(mediaObjectList)
        mAdapter = MediaRecyclerAdapter(mediaObjectList, initGlide())

        recyclerView!!.adapter = mAdapter

        if(firstTime) {
            Handler(Looper.getMainLooper()).post(Runnable { recyclerView!!.playVideo(false) })
            firstTime = false
        }

    }


}