package com.shahzaib.weatherforecast

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.math.roundToInt

class WeatherForecast: AppCompatActivity() {
    private lateinit var pullToRefreshLayout: SwipeRefreshLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private var location: String = "Rawalpindi"
    private lateinit var iconURL: String

    private var list: MutableList<MutableList<String>> =  mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)
        pullToRefreshLayout = findViewById(R.id.pullToRefresh)

        getCurrentWeatherData()
        getWeatherForecast()

        pullToRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        pullToRefreshLayout.setOnRefreshListener {
            getCurrentWeatherData()
            getWeatherForecast()
            pullToRefreshLayout.isRefreshing = false
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@WeatherForecast, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menus, menu)
        return true
    }

    private fun getWeatherForecast(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherForecastRequest: WeatherForecastRequest = retrofit.create(WeatherForecastRequest::class.java)
        val call: Call<WeatherForecastResponse> = weatherForecastRequest.getThreeHourForecast(location, apiKey)

        call.enqueue(object: Callback<WeatherForecastResponse>{
            override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                if (response.code() == 200) run {
                    Toast.makeText(this@WeatherForecast,"Forecast Received....", Toast.LENGTH_SHORT).show()
                    val forecastResponse: WeatherForecastResponse? = response.body()
                    var time: String
                    var icon = "01n"
                    var temperature: Int

                    for (weatherTime in forecastResponse?.weatherList!!){
                        val temperatureData: MutableList<String> = mutableListOf()
                        iconURL = "https://openweathermap.org/img/wn/"
                        iconURL = "$iconURL$icon@2x.png"

                        Log.i("Three Hour Weather", "Downloading weather for " + weatherTime.time + ", " + iconURL)

                        time = weatherTime.time.split(" ")[1].removeRange(5, 8).toString()
                        icon = weatherTime.weather[0].icon
                        temperature = weatherTime.main.temperature.minus(273.15).roundToInt()

                        temperatureData.add(time)
                        temperatureData.add(iconURL)
                        temperatureData.add(temperature.toString())

                        list.add(temperatureData)
                    }

                    Toast.makeText(this@WeatherForecast, "Recycler View Showing...", Toast.LENGTH_SHORT).show()
                    viewAdapter = WeatherHistoryAdapter(list, this@WeatherForecast)
                    recyclerView.adapter = viewAdapter
                }
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                Toast.makeText(this@WeatherForecast,"Unable to fetch Data for Recycler View", Toast.LENGTH_SHORT).show()
            }
        })
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
        val sunriseIcon = findViewById<ImageView>(R.id.sunriseIcon)
        val sunsetIcon = findViewById<ImageView>(R.id.sunsetIcon)
        val sunriseTime = findViewById<TextView>(R.id.sunriseTime)
        val sunsetTime = findViewById<TextView>(R.id.sunsetTime)
        val windTextView = findViewById<TextView>(R.id.wind)
        val perceivedTempTextView = findViewById<TextView>(R.id.perceived_temp)
        val humidityTextView = findViewById<TextView>(R.id.humidity)
        val pressureTextView = findViewById<TextView>(R.id.pressure)

        call.enqueue(object: Callback<WeatherResponse>{
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.code() == 200) run {
                    Toast.makeText(this@WeatherForecast,"Current Weather Received....", Toast.LENGTH_SHORT).show()
                    val weatherResponse: WeatherResponse? = response.body()

                    val temperature: Int? = weatherResponse?.main?.temperature?.minus(273.15)?.roundToInt()
                    val temperatureMin: Int? = weatherResponse?.main?.tempMin?.minus(273.15)?.roundToInt()
                    val temperatureMax: Int? = weatherResponse?.main?.tempMax?.minus(273.15)?.roundToInt()
                    val city: String? = weatherResponse?.name?.capitalize()
                    val country: String? = weatherResponse?.sys?.country?.capitalize()
                    val icon: String? = weatherResponse?.weather?.get(0)?.icon
                    val weatherDescription: String? = weatherResponse?.weather?.get(0)?.description?.capitalize()
                    val sunrise: Date? = weatherResponse?.sys?.sunrise?.times(1000)?.let { Date(it) }
                    val sunset: Date? = weatherResponse?.sys?.sunset?.times(1000)?.let { Date(it) }
                    val pressure: Int? = weatherResponse?.main?.pressure
                    val humidity: Int? = weatherResponse?.main?.humidity
                    val wind: Float? = weatherResponse?.wind?.speed
                    val perceivedTemp: Int? = weatherResponse?.main?.feelsLike?.minus(273.15)?.roundToInt()

                    iconURL = "$iconURL$icon@2x.png"

                    Toast.makeText(this@WeatherForecast, "Downloading Icon...", Toast.LENGTH_SHORT).show()
                    DownloadImageTask(weatherIcon).execute(iconURL)

                    locationTextView.text = "$city, $country"
                    if (temperature != null)
                        temperatureText.text = "$temperature°C"
                    if (temperatureMax != null && temperatureMin != null)
                        temperatureDescription.text = "$weatherDescription\n$temperatureMax / $temperatureMin °C"
                    if (pressure!= null)
                        pressureTextView.text = "$pressure hPa"
                    if (humidity!= null)
                        humidityTextView.text = "$humidity %"
                    if (wind!= null)
                        windTextView.text = "$wind m/s"
                    if (perceivedTemp!= null)
                        perceivedTempTextView.text = "$perceivedTemp °C"


                    if (sunrise != null) {
                        DownloadImageTask(sunriseIcon).execute(sunIconURL + "sunrise-512.png")
                        sunriseTime.text = sunrise.hours.toString() + ":" + sunrise.minutes.toString()
                    }
                    if (sunset != null) {
                        DownloadImageTask(sunsetIcon).execute(sunIconURL + "sunset-512.png")
                        sunsetTime.text = sunset.hours.toString() + ":" + sunset.minutes.toString()
                    }

                    val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
                    tableLayout.visibility = View.VISIBLE
                    val poweredByLayout = findViewById<LinearLayout>(R.id.poweredByView)
                    poweredByLayout.visibility = View.VISIBLE
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
        const val sunIconURL = "https://cdn2.iconfinder.com/data/icons/weather-vol-2-7/512/"
    }
}