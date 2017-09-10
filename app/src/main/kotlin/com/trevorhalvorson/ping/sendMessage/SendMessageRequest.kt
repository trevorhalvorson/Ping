package com.trevorhalvorson.ping.sendMessage

import com.google.gson.annotations.SerializedName

data class SendMessageRequest constructor(@SerializedName("message") val message: String)