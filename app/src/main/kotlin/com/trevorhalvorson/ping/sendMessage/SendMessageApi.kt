package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BuildConfig
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SendMessageApi {

    @Headers("Accept: application/json")
    @POST(BuildConfig.MESSAGING_URL_PATH)
    fun postMessage(@Body body: SendMessageRequest): Observable<SendMessageResponse>

}