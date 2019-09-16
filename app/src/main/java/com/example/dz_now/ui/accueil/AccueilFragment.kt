package com.example.dz_now.ui.accueil

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dz_now.ui.article_list.ArticleListAdapter
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

    private var root:View? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Keep the container
        this.container = container

        //Attach the layout
        val root = inflater.inflate(R.layout.fragment_accueil, container, false)

        this.root = root
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

        val adapter =
            ArticleListAdapter(container!!.context, articles, {partItem : Article -> onClick(partItem)})
        recyclerView!!.adapter = adapter
        spinner!!.visibility = View.GONE
    }

    private fun onClick(article:Article) {
        val intent = Intent(activity, ArticleDetails::class.java)
        ArticleDetails.article = article
        activity?.startActivityForResult(intent, Activity.RESULT_OK)
    }
}

