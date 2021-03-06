package com.example.gameon


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GameService {



    //"https://api.rawg.io/api/"

    @GET("games")
    fun gameLoad(
        @Query("key") APIkey: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        ) : Call<GameData>


    @GET("games")
    fun gameSearch(
        @Query("key") APIkey: String,
        @Query("search") searchTerm: String,
        @Query("metacritic") searchRange: String,
        @Query("search_exact") searchExact: Boolean,
        @Query("search_precise") searchPrecise: Boolean,
        @Query("exclude_additions") excludeAdditions: Boolean): Call<GameData>
}