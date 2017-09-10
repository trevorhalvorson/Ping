package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BuildConfig
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SendMessageApi {

    @POST(BuildConfig.URL_PATH)
    fun postMessage(@Body message: SendMessageRequest): Observable<SendMessageResponse>

}