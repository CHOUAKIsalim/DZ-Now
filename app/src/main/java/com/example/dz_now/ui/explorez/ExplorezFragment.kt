package com.example.dz_now.ui.explorez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dz_now.CategoryListAdapter
import com.example.dz_now.R
import com.example.dz_now.entites.Category
import com.example.dz_now.services.CategoryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.example.dz_now.services.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import android.widget.ProgressBar




class ExplorezFragment : Fragment() {

    //Recyclerview for displaying catégories
    private var recyclerView: RecyclerView? = null

    //The service to get the categories
    private lateinit var categoryApi:CategoryService

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
        val root = inflater.inflate(R.layout.fragment_explorez, container, false)
        recyclerView= root.findViewById(R.id.recyclerView)
        spinner = root.findViewById(R.id.progressBar)

        //Make the spinner visible
        spinner!!.visibility = View.VISIBLE

        //Setting a Grid layout manager to the recyclerview to display two elements for a row
        val layoutManager = GridLayoutManager(activity,2)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager




        //Init the api
        val retrofit = RetrofitClient.instance
        categoryApi = retrofit.create(CategoryService::class.java)

        compositeDisposable = CompositeDisposable()

        getCategories()


        return root
    }

    private fun getCategories() {
        compositeDisposable.add(categoryApi.categories
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{categories->displayCategories(categories)}
        )

    }

    private fun displayCategories(categories : List<Category>) {
        val adapter = CategoryListAdapter(container!!.context, categories)
        recyclerView!!.adapter = adapter
        spinner!!.visibility = View.GONE
    }
}