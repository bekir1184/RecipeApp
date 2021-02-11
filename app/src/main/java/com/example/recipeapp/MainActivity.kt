package com.example.recipeapp

import RecipeAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
    val app_key :String = "02494636fe008fe01c17ea4f0b087f8e"
    val app_id :String = "89033108"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var search_bar:EditText = findViewById(R.id.edit_text)
        search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                findRecipe(search_bar.text.toString())

                return@OnKeyListener true
            }
            false
        })




    }
     fun findRecipe(query:String){
        RetrofitClient.getClient().create(RecipeService::class.java)
                .getRecipe(query,app_id,app_key).enqueue(object :retrofit2.Callback<Model>{
                    override fun onFailure(call: Call<Model>, t: Throwable) {
                        Toast.makeText(this@MainActivity,"Failure",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        val model =response.body()
                        var arrayList: ArrayList<Recipe> = ArrayList<Recipe>()
                        if (model != null) {
                            for( i in model.hits.indices){
                                arrayList.add(model.hits[i].recipe)
                            }
                        }


                        val recipesRecyclerView: RecyclerView =findViewById(R.id.recipesRecyclerView)
                        recipesRecyclerView.layoutManager = LinearLayoutManager(context)
                        recipesRecyclerView.adapter = RecipeAdapter(arrayList,context )
                    }

                })
    }


}

