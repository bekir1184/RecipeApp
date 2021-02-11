package com.example.recipeapp

import RecipeAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Api.RecipeService
import com.example.recipeapp.Api.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    val context:Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitClient.getClient().create(RecipeService::class.java)
                .getRecipe("pizza","8903310","02494636fe008fe01c17ea4f0b087f8e").enqueue(object :retrofit2.Callback<Model>{
                    override fun onFailure(call: Call<Model>, t: Throwable) {
                        Toast.makeText(this@MainActivity,"Failure",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        val model =response.body()
                        val arrayList: ArrayList<Recipe> = ArrayList<Recipe>()
                        if (model != null) {
                            for( i in model.hits.indices){
                                arrayList.add(model.hits[i].recipe)
                            }
                        }


                        val recipesRecyclerView: RecyclerView =findViewById(R.id.recipesRecyclerView)
                        recipesRecyclerView.layoutManager = LinearLayoutManager(context)
                        recipesRecyclerView.adapter = RecipeAdapter(arrayList,context )

                        if (model != null) {
                            Toast.makeText(this@MainActivity,"Response"+model.hits[0].recipe.label,Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this@MainActivity,"Response",Toast.LENGTH_LONG).show()
                        }


                    }

                })


    }


}

