package com.trevorhalvorson.ping.builder

import com.google.gson.annotations.SerializedName

open class BuilderConfig(
        @SerializedName("titleText") private var titleText: String,
        @SerializedName("titleTextSize") private var titleTextSize: String,
        @SerializedName("titleTextColor") private val titleTextColor: String,
        @SerializedName("imageUrl") private var imageUrl: String,
        @SerializedName("imageWidth") private var imageWidth: String,
        @SerializedName("imageHeight") private var imageHeight: String,
        @SerializedName("imageScaleType") private var imageScaleType: String,
        @SerializedName("copyText") private var copyText: String,
        @SerializedName("copyTextSize") private var copyTextSize: String,
        @SerializedName("copyTextColor") private val copyTextColor: String,
        @SerializedName("sendButtonText") private var sendButtonText: String,
        @SerializedName("sendButtonTextColor") private val sendButtonTextColor: String,
        @SerializedName("sendButtonBackgroundColor") private val sendButtonBackgroundColor: String,
        @SerializedName("phoneInputTextColor") private val phoneInputTextColor: String,
        @SerializedName("phoneInputBackgroundColor") private val phoneInputBackgroundColor: String,
        @SerializedName("numPadTextColor") private val numPadTextColor: String,
        @SerializedName("numPadBackgroundColor") private val numPadBackgroundColor: String,
        @SerializedName("backgroundColor") private val backgroundColor: String,
        @SerializedName("pin") private var pin: String,
        @SerializedName("message") private var message: String,
        @SerializedName("messagingUrlBase") private var messagingUrlBase: String,
        @SerializedName("messagingUrlPath") private var messagingUrlPath: String,
        @SerializedName("builderUrlBase") private var builderUrlBase: String,
        @SerializedName("builderUrlPath") private var builderUrlPath: String,
        @SerializedName("email") private var email: String
) {

    init {
        titleText = "\"$titleText\""
        titleTextSize = "${titleTextSize}F"
        imageUrl = "\"$imageUrl\""
        imageWidth = "${imageWidth}F"
        imageHeight = "${imageHeight}F"
        imageScaleType = "\"$imageScaleType\""
        copyText = "\"$copyText\""
        copyTextSize = "${copyTextSize}F"
        sendButtonText = "\"$sendButtonText\""
        pin = "\"$pin\""
        message = "\"$message\""
        messagingUrlBase = "\"$messagingUrlBase\""
        messagingUrlPath = "\"$messagingUrlPath\""
        builderUrlBase = "\"$builderUrlBase\""
        builderUrlPath = "\"$builderUrlPath\""
        email = "\"$email\""
    }

}
