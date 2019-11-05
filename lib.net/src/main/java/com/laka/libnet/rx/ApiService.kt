package com.laka.libnet.rx

import com.laka.libnet.rx.response.ResponseInfo
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

interface ApiService {


    @GET("s?wd=今日新鲜事&tn=SE_PclogoS_8whnvm25&sa=ire_dl_gh_logo&rsv_dl=igh_logo_pcs")
    fun getUserInfo(@QueryMap params: HashMap<String, String>): Observable<String>

    @POST
    @FormUrlEncoded
    fun postRequest(@Url url: String, @FieldMap params: Map<String, String>): Observable<ResponseInfo>

    @POST
    fun postRequest(@Url url: String): Observable<ResponseInfo>

    @GET
    @FormUrlEncoded
    fun getRequest(@Url url: String, @QueryMap params: Map<String, String>): Observable<ResponseInfo>

    @GET
    fun getRequest(@Url url: String): Observable<ResponseInfo>

    @GET
    suspend fun getUserInfo(@Url url: String): ResponseInfo

}