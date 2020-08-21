package com.shahzaib.weatherforecast

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class WeatherResponse {
    @SerializedName("coord")
    lateinit var coordinates: Coordinates
    @SerializedName("weather")
    lateinit var weather: ArrayList<Weather>
    @SerializedName("base")
    lateinit var base: String
    @SerializedName("main")
    lateinit var main: Main
    @SerializedName("visibility")
    var visbility: Int = 0
    @SerializedName("wind")
    lateinit var wind: Wind
    @SerializedName("clouds")
    lateinit var clouds: Clouds
    @SerializedName("dt")
    var dt: Long = 0
    @SerializedName("sys")
    lateinit var sys: Sys
    @SerializedName("timezone")
    var timeZone: Int = 0
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("name")
    lateinit var name: String
    @SerializedName("cod")
    var statusCode: Int = 0

    class Coordinates{
        @SerializedName("lat")
        var latitude: Float = 0.0f
        @SerializedName("lon")
        var longitude: Float = 0.0f
    }

    class Weather {
        @SerializedName("id")
        var ID: Int = 0
        @SerializedName("main")
        lateinit var main: String
        @SerializedName("description")
        lateinit var description: String
        @SerializedName("icon")
        lateinit var icon: String
    }

    class Main {
        @SerializedName("temp")
        var temperature: Float = 0.0f
        @SerializedName("feels_like")
        var feelsLike: Float = 0.0f
        @SerializedName("temp_min")
        var tempMin: Float = 0.0f
        @SerializedName("temp_max")
        var tempMax: Float = 0.0f
        @SerializedName("pressure")
        var pressure: Int = 0
        @SerializedName("humidity")
        var humidity: Int = 0
    }

    class Wind {
        @SerializedName("speed")
        var speed: Float = 0.0f
        @SerializedName("deg")
        var degrees: Int = 0
        @SerializedName("gust")
        var gust: Float = 0.0f
    }

    class Clouds {
        @SerializedName("all")
        var all: Int = 0
    }

    class Sys {
        @SerializedName("type")
        var type: Int = 0
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("country")
        lateinit var country: String
        @SerializedName("sunrise")
        var sunrise: Long = 0
        @SerializedName("sunset")
        var sunset: Long = 0
    }
}