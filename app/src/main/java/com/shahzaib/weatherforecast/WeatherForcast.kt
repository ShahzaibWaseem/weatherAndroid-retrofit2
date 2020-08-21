package com.shahzaib.weatherforecast

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class WeatherForecast: AppCompatActivity() {
    private lateinit var location: String

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.weather)

    }

    companion object {
        const val baseURL = R.string.base_url
        const val apiKey = R.string.api_key
    }
}