package com.shahzaib.weatherforecast

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class WeatherForecastResponse {
    @SerializedName("cod")
    lateinit var statusCode: String
    @SerializedName("message")
    var message: Int = 0
    @SerializedName("cnt")
    var cnt: Int = 0
    @SerializedName("list")
    lateinit var weatherList: ArrayList<ThreeHourWeatherForecast>
    @SerializedName("city")
    lateinit var city: City

    class ThreeHourWeatherForecast{
        @SerializedName("dt")
        var dt: Long = 0
        @SerializedName("main")
        lateinit var main: Main
        @SerializedName("weather")
        lateinit var weather: ArrayList<WeatherResponse.Weather>
        @SerializedName("clouds")
        lateinit var clouds: Clouds
        @SerializedName("wind")
        lateinit var wind: Wind
        @SerializedName("visibility")
        var visibility: Int = 0
        @SerializedName("pop")
        var pop: Float = 0.0f
        @SerializedName("sys")
        lateinit var sys: Sys
        @SerializedName("dt_txt")
        lateinit var time: String
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
        @SerializedName("sea_level")
        var sea_level: Int = 0
        @SerializedName("grnd_level")
        var grnd_level: Int = 0
        @SerializedName("humidity")
        var humidity: Int = 0
        @SerializedName("temp_kf")
        var temp_kf: Float = 0.0f
    }

    class Clouds {
        @SerializedName("all")
        var all: Int = 0
    }

    class Wind {
        @SerializedName("speed")
        var speed: Float = 0.0f
        @SerializedName("deg")
        var degrees: Int = 0
    }

    class Sys {
        @SerializedName("pod")
        lateinit var pod: String
    }

    class City {
        @SerializedName("id")
        var ID: Int = 0
        @SerializedName("name")
        lateinit var name: String
        @SerializedName("coord")
        lateinit var coordinates: WeatherResponse.Coordinates
        @SerializedName("country")
        lateinit var country: String
        @SerializedName("population")
        var population: Int = 0
        @SerializedName("timezone")
        var timezone: Int = 0
        @SerializedName("sunrise")
        var sunrise: Int = 0
        @SerializedName("sunset")
        var sunset: Int = 0
    }
}