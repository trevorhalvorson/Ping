package com.trevorhalvorson.ping.sendMessage

import com.google.gson.annotations.SerializedName

data class SendMessageResponse constructor(
        @SerializedName("success") val success: Boolean,
        @SerializedName("message") val message: String
)