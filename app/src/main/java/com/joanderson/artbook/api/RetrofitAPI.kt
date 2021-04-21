package com.joanderson.artbook.api

import com.joanderson.artbook.model.ImageResponse
import com.joanderson.artbook.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET(value = "/api/")
    suspend fun imageSearch(
            @Query("q") searchQuery: String,
            @Query("key") apiKey : String = API_KEY
    ) : Response<ImageResponse>
}