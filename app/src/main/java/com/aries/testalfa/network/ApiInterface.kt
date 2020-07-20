package com.aries.testalfa.network

import com.aries.testalfa.network.response.News
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{

    @GET(ApiConstant.BASEURL +"top-headlines?country=id")
    fun getNews(
            @Query("category") category: String,
            @Query("apiKey") apiKey: String
    ): Observable<Response<News>>

    @GET(ApiConstant.BASEURL +"top-headlines?country=id")
    fun getSearchNews(
            @Query("category") category: String,
            @Query("q") q: String,
            @Query("apiKey") apiKey: String
    ): Observable<Response<News>>
}