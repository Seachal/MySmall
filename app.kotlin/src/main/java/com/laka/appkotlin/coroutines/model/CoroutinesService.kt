package com.laka.appkotlin.coroutines.model

import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Url

interface CoroutinesService {

    @GET()
    suspend fun getBaiDuHomeInfo(@Url url: String): JSONObject
}