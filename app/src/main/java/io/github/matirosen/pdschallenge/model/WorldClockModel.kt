package io.github.matirosen.pdschallenge.model

import com.google.gson.annotations.SerializedName

data class WorldClockModel(
    @SerializedName("year")
    private val year: Int,

    @SerializedName("month")
    private val month: Int,

    @SerializedName("day")
    private val day: Int,

    @SerializedName("hour")
    private val hour: Int,

    @SerializedName("minute")
    private val minute: Int,

    @SerializedName("seconds")
    private val seconds: Int,

    @SerializedName("milliSeconds")
    private val milliSeconds: Int,

    @SerializedName("dateTime")
    private val dateTime: String,

    @SerializedName("date")
    private val date: String,

    @SerializedName("time")
    private val time: String,

    @SerializedName("timeZone")
    private val timeZone: String,

    @SerializedName("dayOfWeek")
    private val dayOfWeek: String,

    @SerializedName("dstActive")
    private val dstActive: Boolean
)