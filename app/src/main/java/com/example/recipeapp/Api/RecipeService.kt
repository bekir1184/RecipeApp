package com.example.recipeapp.Api

import com.example.recipeapp.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("search")
        fun getRecipe(@Query("q") q:String, @Query("app_id") app_id :String, @Query("app_key") app_key:String): Call<Model>

}