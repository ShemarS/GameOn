package com.example.gameon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.rawg.io/api/"
    private val TAG = "MAN WHAT"
    private val API_KEY = "5010f16954ea41dbbbd5dcbcf63fe830"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define an array to store a list of users
        val gameList = ArrayList<Results>()

        // specify a viewAdapter for the dataset
        val adapter = GamesAdapter(gameList)

        // Store the the recyclerView widget in a variable
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.adapter = adapter

        // use a linear layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val GamesAPI = retrofit.create(GameService::class.java)

        // Using enqueue method allows to make asynchronous call without blocking/freezing main thread
        // randomUserAPI.getUserInfo("us").enqueue  // this end point gets one user only
        // getMultipleUserInfoWithNationality end point gets multiple user info with nationality as parameters
        GamesAPI.gameSearch("$API_KEY").enqueue(object : Callback<GameData> {

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

                // The following log messages are just for testing purpose
                Log.d(TAG, ": ${body.results.get(0).name}")
                Log.d(TAG, ": ${body.results.get(0).metacritic}")
                Log.d(TAG, ": ${body.results.get(0).released}")


                // Update the adapter with the new data
                gameList.addAll(body.results)
                adapter.notifyDataSetChanged()
            }

        })





    }
}