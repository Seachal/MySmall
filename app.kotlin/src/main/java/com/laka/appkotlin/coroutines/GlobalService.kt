package com.laka.appkotlin.coroutines

import retrofit2.http.GET

interface GlobalService {

    @GET("")
    suspend fun getLatestNews2(): String
}