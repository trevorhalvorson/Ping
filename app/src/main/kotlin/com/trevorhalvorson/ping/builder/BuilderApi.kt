package com.trevorhalvorson.ping.builder

import com.trevorhalvorson.ping.BuildConfig
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface BuilderApi {

    @POST(BuildConfig.BUILDER_URL_PATH)
    fun startBuilder(@Body config: BuilderConfig): Observable<BuilderResponse>
    
}