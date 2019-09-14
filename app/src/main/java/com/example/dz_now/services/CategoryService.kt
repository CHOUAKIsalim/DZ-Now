package com.example.dz_now.services

import com.example.dz_now.entites.Category
import io.reactivex.Observable
import retrofit2.http.GET

interface CategoryService {

    @get:GET("category")
    val categories:Observable<List<Category>>

}