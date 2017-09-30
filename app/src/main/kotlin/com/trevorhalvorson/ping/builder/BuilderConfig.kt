package com.trevorhalvorson.ping.builder

import com.google.gson.annotations.SerializedName

open class BuilderConfig(
        @SerializedName("titleText") private val titleText: String,
        @SerializedName("titleTextSize") private val titleTextSize: String,
        @SerializedName("titleTextColor") private val titleTextColor: String,
        @SerializedName("imageUrl") private val imageUrl: String,
        @SerializedName("imageWidth") private val imageWidth: String,
        @SerializedName("imageHeight") private val imageHeight: String,
        @SerializedName("imageScaleType") private val imageScaleType: String,
        @SerializedName("copyText") private val copyText: String,
        @SerializedName("copyTextSize") private val copyTextSize: String,
        @SerializedName("copyTextColor") private val copyTextColor: String,
        @SerializedName("sendButtonText") private val sendButtonText: String,
        @SerializedName("sendButtonTextColor") private val sendButtonTextColor: String,
        @SerializedName("sendButtonBackgroundColor") private val sendButtonBackgroundColor: String,
        @SerializedName("phoneInputTextColor") private val phoneInputTextColor: String,
        @SerializedName("phoneInputBackgroundColor") private val phoneInputBackgroundColor: String,
        @SerializedName("numPadTextColor") private val numPadTextColor: String,
        @SerializedName("numPadBackgroundColor") private val numPadBackgroundColor: String,
        @SerializedName("backgroundColor") private val backgroundColor: String,
        @SerializedName("pin") private val pin: String,
        @SerializedName("message") private val message: String,
        @SerializedName("messagingUrlBase") private val messagingUrlBase: String,
        @SerializedName("messagingUrlPath") private val messagingUrlPath: String,
        @SerializedName("builderUrlBase") private val builderUrlBase: String,
        @SerializedName("builderUrlPath") private val builderUrlPath: String
) {

    private var configsMap: MutableMap<String, String> = HashMap()

    init {
        configsMap.put("titleText", titleText)
        configsMap.put("titleText", titleText)
        configsMap.put("titleTextSize", titleTextSize)
        configsMap.put("titleTextColor", titleTextColor)
        configsMap.put("imageUrl", imageUrl)
        configsMap.put("imageWidth", imageWidth)
        configsMap.put("imageHeight", imageHeight)
        configsMap.put("imageScaleType", imageScaleType)
        configsMap.put("copyText", copyText)
        configsMap.put("copyTextSize", copyTextSize)
        configsMap.put("copyTextColor", copyTextColor)
        configsMap.put("sendButtonText", sendButtonText)
        configsMap.put("sendButtonTextColor", sendButtonTextColor)
        configsMap.put("sendButtonBackgroundColor", sendButtonBackgroundColor)
        configsMap.put("phoneInputTextColor", phoneInputTextColor)
        configsMap.put("phoneInputBackgroundColor", phoneInputBackgroundColor)
        configsMap.put("numPadTextColor", numPadTextColor)
        configsMap.put("numPadBackgroundColor", numPadBackgroundColor)
        configsMap.put("backgroundColor", backgroundColor)
        configsMap.put("pin", pin)
        configsMap.put("message", message)
        configsMap.put("messagingUrlBase", messagingUrlBase)
        configsMap.put("messagingUrlPath", messagingUrlPath)
        configsMap.put("builderUrlBase", builderUrlBase)
        configsMap.put("builderUrlPath", builderUrlPath)
    }

}