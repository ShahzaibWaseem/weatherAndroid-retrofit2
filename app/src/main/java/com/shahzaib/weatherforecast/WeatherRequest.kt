package com.shahzaib.weatherforecast

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRequest {
    @GET("/data/2.5/weather?")
    fun getCurrentWeatherData(@Query("q") location: String, @Query("APPID") api: String): Call <WeatherResponse>
}

interface WeatherForecastRequest {
    @GET("/data/2.5/forecast?")
    fun getThreeHourForecast(@Query("q") location: String, @Query("APPID") api: String): Call <WeatherForecastResponse>
}