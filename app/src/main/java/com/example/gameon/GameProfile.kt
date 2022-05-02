package com.example.gameon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_profile.*

class GameProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_profile)

        val myIntent = Intent()

        textViewName.text = myIntent.getStringExtra("first name")




    }

}