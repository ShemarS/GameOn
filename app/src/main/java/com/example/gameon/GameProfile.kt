package com.example.gameon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameProfile : AppCompatActivity() {

    private val TAG = "MAN WHAT"
    private val BASE_URL = "https://api.rawg.io/api/"
    private val API_KEY = "5010f16954ea41dbbbd5dcbcf63fe830"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_profile)


        val title = intent.getStringExtra("title")


        Log.d(TAG, "Passed title: $title")


        val selectedGame = ArrayList<Results>()
        val adapter = ProfileAdapter(selectedGame)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gamesAPI = retrofit.create(GameService::class.java)
        if (title != null) {
            gamesAPI.gameSearch("$API_KEY", title,"20,100", true, true, true).enqueue(object :
                Callback<GameData> {

                override fun onFailure(call: Call<GameData>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                }

                override fun onResponse(call: Call<GameData>, response: Response<GameData>) {
                    Log.d(TAG, "onResponse: $response")

                    val body = response.body()

                    if (body == null){
                        Log.w(TAG, "Valid response was not received")
                        return
                    }


                    selectedGame.addAll(body.results)
                    adapter.notifyDataSetChanged()
                }

            })
        }


    }


    fun saveFavorite(view: View) {
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

    }

}