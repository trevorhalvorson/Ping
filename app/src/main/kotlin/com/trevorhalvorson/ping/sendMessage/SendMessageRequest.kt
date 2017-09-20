package com.trevorhalvorson.ping.sendMessage

import com.google.gson.annotations.SerializedName

data class SendMessageRequest constructor(
        @SerializedName("phoneNumber") val phoneNumber: String,
        @SerializedName("message") val message: String
)