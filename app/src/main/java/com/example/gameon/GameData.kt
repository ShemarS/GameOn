package com.example.gameon

import com.google.gson.annotations.SerializedName

data class GameData(
    val count: Int,
    val results: List<Results>
)

data class Results(
    val id: Int,
    val name: String,
    val released: String,
    val background_image: String,
    val metacritic: Int
)




