package com.example.gameon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.rawg.io/api/"
    private val TAG = "MAN WHAT"
    private val API_KEY = "5010f16954ea41dbbbd5dcbcf63fe830"
    private val REQUEST_CODE = 123
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callAPI("", null)
    }


    private fun openSecondActivity(){
        val myIntent = Intent(this, SearchActivity::class.java)
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Search -> {
                openSecondActivity()
            }
            R.id.SignOut -> {
                FirebaseAuth.getInstance().signOut()
                finish()
            }
            R.id.Playing -> {
                TODO("Add Playing page!")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            val searchTerm = data?.getStringExtra("Search").toString()
            callAPI("Search",searchTerm )
        }
    }

    private fun callAPI(callType: String, searchTerm: String?) {

        val gameList = ArrayList<Results>()
        val adapter = GamesAdapter(gameList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gamesAPI = retrofit.create(GameService::class.java)
        val x: Call<GameData>

        if (callType == "Search" && searchTerm != null) {

           x = gamesAPI.gameSearch("$API_KEY", searchTerm,"20,100", true, true, true)
        } else {
            x = gamesAPI.gameLoad("$API_KEY", 1, 20)
        }



            x.enqueue(object : Callback<GameData> {

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


                gameList.addAll(body.results)
                adapter.notifyDataSetChanged()
            }

        })

    }
}
