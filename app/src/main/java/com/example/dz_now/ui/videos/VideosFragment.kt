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
import com.example.dz_now.*
import com.example.dz_now.entites.Article
import com.example.dz_now.services.ArticleService
import com.example.dz_now.services.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class VideosFragment : Fragment() {


    // To keep the container
    private var container: ViewGroup? = null

    //Reference to recyclerview
    private var recyclerView: ExoPlayerRecyclerView? = null

    //The service to get the categories
    private lateinit var articleApi: ArticleService

    // Observer
    private lateinit var compositeDisposable: CompositeDisposable

    private var mAdapter: MediaRecyclerAdapter? = null
    private var firstTime = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Keep the container
        this.container = container

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


        for(article in articles){
            article.video = article.video.replace("embed/","watch?v=",true)
            article.video = article.video.replace("?feature=oembed","",true)
        }
        //set data object
        val article = Article(1,"title","resume","content","date","source","https://img.lemde.fr/2019/04/22/0/191/1619/1079/688/0/60/0/e39da8d_2FIads9h8wB-0SwSgxVaVWsp.jpg","","link",
            "https://www.youtube.com/watch?v=yBXkVcwd7WI","category")
        val articles2 = listOf<Article>(article)
//        articles2.add(article)
     //   articles2.add(article)
    //    articles2.add(article)
     //   articles2.add(article)
      //  articles2.add(article)

//         mAdapter = VideoAdapter(container!!.context, articles2)
       // articles2.add(articles[0])
        mAdapter= MediaRecyclerAdapter(articles, initGlide())
        recyclerView!!.setMediaObjects(articles)
        recyclerView!!.adapter = mAdapter

        if(firstTime) {
            Handler(Looper.getMainLooper()).post(Runnable { recyclerView!!.playVideo(false) })
            firstTime = false
        }

    }


}