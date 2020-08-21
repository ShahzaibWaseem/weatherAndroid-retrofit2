package com.shahzaib.weatherforecast

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherForecast: AppCompatActivity() {
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var location: String = "London"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        pullToRefreshLayout = findViewById(R.id.pullToRefresh)

        getCurrentWeatherData()

        pullToRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        pullToRefreshLayout.setOnRefreshListener {
            getCurrentWeatherData()
            pullToRefreshLayout.isRefreshing = false
        }

//        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }

    }

    private fun getCurrentWeatherData(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherRequestService: WeatherRequest = retrofit.create(WeatherRequest::class.java)
        val call: Call<WeatherResponse> = weatherRequestService.getCurrentWeatherData(location, apiKey)

        call.enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) run {
                    Toast.makeText(this@WeatherForecast,"Data Received....", Toast.LENGTH_SHORT).show()
                    val weatherResponse: WeatherResponse? = response.body()

                    val temperature: Float? = weatherResponse?.main?.temperature
                    val temperatureMin: Float? = weatherResponse?.main?.tempMin
                    val temperatureMax: Float? = weatherResponse?.main?.tempMax
                    val pressure: Int? = weatherResponse?.main?.pressure
                    val humidity: Int? = weatherResponse?.main?.humidity
                    val country: String? = weatherResponse?.sys?.country

                    // TODO Display the data in RecyclerView Lists
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@WeatherForecast,"Unable to fetch Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val baseURL = "https://api.openweathermap.org"
        const val apiKey = "8ad184d5d752abf938dd69b3da8c1fab"
    }
}