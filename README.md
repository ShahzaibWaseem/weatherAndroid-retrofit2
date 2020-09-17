# Weather App in Android (Kotlin) using Retrofit 2
## Description
This application tells the current weather and the weather forecast of a certain city using Retrofit2. It implements a custom adapter for Recycler View which scrolls horizontally and shows the weather forcast (for every 3 hours).

This app sends RESTful requests to the Open Weather Map API and displays the responses sent by Open Weather Map and updates the responses when the layout is swiped from the top.

### Technical Specifications
- AndroidX
- Target SDK Version: 30
- Minimum SDK Version: 23
- Kotlin Version 1.4.0
- Retrofit Version 2.4.0
    - Gson Version 2.8.6
    - OK Http Version 3.12.1

- Design Dependencies
    - Swipe Refresh Layout Version 1.1.0
    - Recycler View Version 1.1.0
    - Drawer Layout Version 1.1.1
    - Android Material Version 1.3.0-alpha02

### Setup
- To get this app up and running go to [WeatherForecast.kt](https://github.com/ShahzaibWaseem/weatherAndroid-retrofit2/blob/master/app/src/main/java/com/shahzaib/weatherforecast/WeatherForecast.kt) and change the `apiKey` to what your API key is. If you do not have your own API key go to [Open Weather Map](https://openweathermap.org/api) to register and get your own API Key, which requests Open Weather map to send particular weather updates.


### APK File
Click this link to download [app-debug.apk](https://github.com/ShahzaibWaseem/weatherAndroid-retrofit2/blob/master/app/build/outputs/apk/debug/app-debug.apk).

## Screenshot
![Design Image](https://raw.githubusercontent.com/ShahzaibWaseem/weatherAndroid-retrofit2/master/Image/Design.jpg)