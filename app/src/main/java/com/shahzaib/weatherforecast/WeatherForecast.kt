package com.shahzaib.weatherforecast

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class WeatherForecast: AppCompatActivity() {
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout

    private lateinit var linearLayout: LinearLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var location: String = "Rawalpindi"
    private lateinit var iconURL: String

    private var list: List<String> = listOf("Rawalpindi", "Islamabad")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
//        pullToRefreshLayout = findViewById(R.id.pullToRefresh)

        viewManager = LinearLayoutManager(this)
        viewAdapter = WeatherHistoryAdapter(list, this)

        getCurrentWeatherData()

//        pullToRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
//            android.R.color.holo_green_light,
//            android.R.color.holo_orange_light,
//            android.R.color.holo_red_light)
//
//        pullToRefreshLayout.setOnRefreshListener {
//            getCurrentWeatherData()
//            pullToRefreshLayout.isRefreshing = false
//        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@WeatherForecast, LinearLayoutManager.HORIZONTAL, true)
            adapter = viewAdapter
        }
    }

    private fun getCurrentWeatherData(){
        iconURL = "https://openweathermap.org/img/wn/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherRequestService: WeatherRequest = retrofit.create(WeatherRequest::class.java)
        val call: Call<WeatherResponse> = weatherRequestService.getCurrentWeatherData(location, apiKey)

        val locationTextView = findViewById<TextView>(R.id.location)
        val weatherIcon = findViewById<ImageView>(R.id.weatherIcon)
        val temperatureText = findViewById<TextView>(R.id.temperature)
        val temperatureDescription = findViewById<TextView>(R.id.weatherDescription)

        call.enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) run {
                    Toast.makeText(this@WeatherForecast,"Data Received....", Toast.LENGTH_SHORT).show()
                    val weatherResponse: WeatherResponse? = response.body()

                    val temperature: Int? = weatherResponse?.main?.temperature?.minus(273.15)?.roundToInt()
                    val temperatureMin: Int? = weatherResponse?.main?.tempMin?.minus(273.15)?.roundToInt()
                    val temperatureMax: Int? = weatherResponse?.main?.tempMax?.minus(273.15)?.roundToInt()
                    val pressure: Int? = weatherResponse?.main?.pressure
                    val humidity: Int? = weatherResponse?.main?.humidity
                    val country: String? = weatherResponse?.sys?.country?.capitalize()
                    val icon: String? = weatherResponse?.weather?.get(0)?.icon
                    val weatherDescription: String? = weatherResponse?.weather?.get(0)?.description?.capitalize()

                    iconURL = "$iconURL$icon@2x.png"

                    Toast.makeText(this@WeatherForecast, "Downloading Icon...", Toast.LENGTH_SHORT).show()
                    DownloadImageTask(weatherIcon).execute(iconURL)

                    locationTextView.text = "$location, $country"
                    if (temperature != null)
                        temperatureText.text = "$temperature°C"
                    if (temperatureMax != null && temperatureMin != null)
                        temperatureDescription.text = "$weatherDescription\n$temperatureMax / $temperatureMin °C"

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(this@WeatherForecast,"Unable to fetch Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val baseURL = "https://api.openweathermap.org"
        const val apiKey = "YOUR_API_KEY"
    }
}