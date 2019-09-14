package com.example.dz_now.services

import com.example.dz_now.entites.Article
import io.reactivex.Observable
import retrofit2.http.GET

interface ArticleService {

    @get:GET("news")
    val articles:Observable<List<Article>>

    @get:GET("news/?category=videos")
    val articlesWithVideos:Observable<List<Article>>

}