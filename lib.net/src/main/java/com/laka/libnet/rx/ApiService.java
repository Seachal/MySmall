package com.laka.libnet.rx;

import com.laka.libnet.rx.response.ResponseInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public interface ApiService {

    @GET("s?wd=今日新鲜事&tn=SE_PclogoS_8whnvm25&sa=ire_dl_gh_logo&rsv_dl=igh_logo_pcs")
    Observable<String> getUserInfo(@QueryMap HashMap<String,String> params);

    @POST()
    @FormUrlEncoded
    Observable<ResponseInfo> postRequest(@Url() String url, @FieldMap Map<String, String> params);

    @POST()
    Observable<ResponseInfo> postRequest(@Url() String url);

    @GET()
    @FormUrlEncoded
    Observable<ResponseInfo> getRequest(@Url() String url, @QueryMap Map<String, String> params);

    @GET()
    Observable<ResponseInfo> getRequest(@Url() String url);

}
