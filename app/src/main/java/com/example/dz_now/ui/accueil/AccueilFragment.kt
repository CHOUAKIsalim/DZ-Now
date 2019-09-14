package com.example.dz_now.ui.accueil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dz_now.ArticleListAdapter
import com.example.dz_now.R
import com.example.dz_now.entites.Article
import com.example.dz_now.services.ArticleService
import com.example.dz_now.services.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class AccueilFragment : Fragment() {


    //Reference to recyclerview
    private var recyclerView: RecyclerView? = null


    //The service to get the categories
    private lateinit var articleApi:ArticleService

    // Observer
    private lateinit var compositeDisposable: CompositeDisposable

    // To keep the container
    private var container: ViewGroup? = null

    //Reference to the progressBar
    private var spinner: ProgressBar? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Keep the container
        this.container = container

        //Attach the layout
        val root = inflater.inflate(R.layout.fragment_accueil, container, false)
        recyclerView= root.findViewById(R.id.recyclerView)
        spinner = root.findViewById(R.id.progressBar)

        //Make the spinner visible
        spinner!!.visibility = View.VISIBLE

        //Linear layout manager for the recyclerview
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager


        //Init the api
        val retrofit = RetrofitClient.instance
        articleApi = retrofit.create(ArticleService::class.java)

        compositeDisposable = CompositeDisposable()

        getArticles()
        return root
    }

    private fun getArticles() {
        compositeDisposable.add(articleApi.articles
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{articles->displayArticles(articles)}
        )
    }

    private fun displayArticles(articles : List<Article>) {
        val adapter = ArticleListAdapter(container!!.context, articles)
        recyclerView!!.adapter = adapter
        spinner!!.visibility = View.GONE
    }

}
