package com.example.gameon


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GameService {



    //"https://api.rawg.io/api/"

    @GET("games")
    fun gameLoad(
        //@Query("search") searchTerm: String,
        @Query("key") APIkey: String) : Call<GameData>


    @GET("games")
    fun gameSearch(
        @Query("key") APIkey: String,
        @Query("search") searchTerm: String,
        @Query("metacritic") searchRange: String,
        @Query("search_exact") searchPrecise: Boolean,
        @Query("exclude_additions") excludeAdditions: Boolean): Call<GameData>
}