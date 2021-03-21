package com.example.androiddevchallenge.data.weather_data

import com.google.gson.Gson

class DataWeather {

    var weatherJSONData = """
        [
   {
      "date":"22, Feb 2021",
      "locality":"Reykjavik",
      "country":"Iceland",
      "currentTemp":13,
      "currentWeather":"thunder",
      "tempInDetail":{
         "precipitation":30,
         "wind":8,
         "humidity":30,
         "pressure":840
      },
      "nearByCity":[
         {
            "locality":"Grindavik",
            "currentTemp":8,
            "currentWeather":"rainy"
         },
         {
            "locality":"Landmannalaugar",
            "currentTemp":2,
            "currentWeather":"cloudy"
         },
         {
            "locality":"Grindavik",
            "currentTemp":8,
            "currentWeather":"heavy_snow"
         },
         {
            "locality":"Landmannalaugar",
            "currentTemp":2,
            "currentWeather":"little_sunny"
         }
      ],
      "tempByTime":[
         {
            "time":"01:00 am",
            "temp":"8",
            "weather":"cloudy"
         },
         {
            "time":"02:00 am",
            "temp":"9",
            "weather":"rainy"
         },
         {
            "time":"03:00 am",
            "temp":"11",
            "weather":"night"
         },
         {
            "time":"04:00 am",
            "temp":"12",
            "weather":"tornado"
         },
         {
            "time":"05:00 am",
            "temp":"11",
            "weather":"sleet"
         },
         {
            "time":"06:00 am",
            "temp":"10",
            "weather":"partial_cloudy"
         },
         {
            "time":"07:00 am",
            "temp":"13",
            "weather":"thunderstorm"
         },
         {
            "time":"08:00 am",
            "temp":"14",
            "weather":"fog"
         },
         {
            "time":"09:00 am",
            "temp":"13",
            "weather":"stormy"
         },
         {
            "time":"10:00 am",
            "temp":"15",
            "weather":"partly_cloudy"
         },
         {
            "time":"11:00 am",
            "temp":"16",
            "weather":"sunny"
         },
         {
            "time":"12:00 pm",
            "temp":"17",
            "weather":"he"
         },
         {
            "time":"01:00 pm",
            "temp":"8",
            "weather":"little_sunny"
         },
         {
            "time":"02:00 pm",
            "temp":"9",
            "weather":"very_rain"
         },
         {
            "time":"03:00 pm",
            "temp":"11",
            "weather":"tornado"
         },
         {
            "time":"04:00 pm",
            "temp":"12",
            "weather":"lightning"
         },
         {
            "time":"05:00 pm",
            "temp":"11",
            "weather":"heavy_snow"
         },
         {
            "time":"06:00 pm",
            "temp":"10",
            "weather":"snow"
         },
         {
            "time":"07:00 pm",
            "temp":"13",
            "weather":"snowflake"
         },
         {
            "time":"08:00 pm",
            "temp":"14",
            "weather":"heavy_snow"
         },
         {
            "time":"09:00 pm",
            "temp":"13",
            "weather":"cloudy"
         },
         {
            "time":"10:00 pm",
            "temp":"15",
            "weather":"cloudy_night"
         },
         {
            "time":"11:00 pm",
            "temp":"16",
            "weather":"cloudy_night"
         },
         {
            "time":"12:00 am",
            "temp":"17",
            "weather":"stormy"
         }
      ]
   },
   {
      "date":"22, Feb 2021",
      "locality":"Kannur",
      "country":"India",
      "currentTemp":31,
      "currentWeather":"sunny",
      "tempInDetail":{
         "precipitation":5,
         "wind":13,
         "humidity":70,
         "pressure":940
      },
      "nearByCity":[
         {
            "locality":"Kozhikode",
            "currentTemp":31,
            "currentWeather":"rainy"
         },
         {
            "locality":"Kochi",
            "currentTemp":32,
            "currentWeather":"cloudy"
         },
         {
            "locality":"Thiruvananthapuram",
            "currentTemp":39,
            "currentWeather":"thunder"
         },
         {
            "locality":"Wayanad",
            "currentTemp":24,
            "currentWeather":"lightning"
         }
      ],
      "tempByTime":[
         {
            "time":"01:00 am",
            "temp":"38",
            "weather":"cloudy"
         },
         {
            "time":"02:00 am",
            "temp":"39",
            "weather":"rainy"
         },
         {
            "time":"03:00 am",
            "temp":"31",
            "weather":"night"
         },
         {
            "time":"04:00 am",
            "temp":"32",
            "weather":"tornado"
         },
         {
            "time":"05:00 am",
            "temp":"31",
            "weather":"sleet"
         },
         {
            "time":"06:00 am",
            "temp":"30",
            "weather":"partial_cloudy"
         },
         {
            "time":"07:00 am",
            "temp":"33",
            "weather":"thunderstorm"
         },
         {
            "time":"08:00 am",
            "temp":"34",
            "weather":"fog"
         },
         {
            "time":"09:00 am",
            "temp":"33",
            "weather":"stormy"
         },
         {
            "time":"10:00 am",
            "temp":"15",
            "weather":"partly_cloudy"
         },
         {
            "time":"11:00 am",
            "temp":"26",
            "weather":"sunny"
         },
         {
            "time":"12:00 pm",
            "temp":"27",
            "weather":"heavy_rain"
         },
         {
            "time":"01:00 pm",
            "temp":"28",
            "weather":"little_sunny"
         },
         {
            "time":"02:00 pm",
            "temp":"29",
            "weather":"very_rain"
         },
         {
            "time":"03:00 pm",
            "temp":"31",
            "weather":"tornado"
         },
         {
            "time":"04:00 pm",
            "temp":"32",
            "weather":"lightning"
         },
         {
            "time":"05:00 pm",
            "temp":"31",
            "weather":"heavy_snow"
         },
         {
            "time":"06:00 pm",
            "temp":"30",
            "weather":"snow"
         },
         {
            "time":"07:00 pm",
            "temp":"33",
            "weather":"snowflake"
         },
         {
            "time":"08:00 pm",
            "temp":"34",
            "weather":"heavy_snow"
         },
         {
            "time":"09:00 pm",
            "temp":"33",
            "weather":"cloudy"
         },
         {
            "time":"10:00 pm",
            "temp":"25",
            "weather":"cloudy_night"
         },
         {
            "time":"11:00 pm",
            "temp":"26",
            "weather":"cloudy_night"
         },
         {
            "time":"12:00 am",
            "temp":"27",
            "weather":"stormy"
         }
      ]
   },
   {
      "date":"22, Feb 2021",
      "locality":"Wahington, DC",
      "country":"USA",
      "currentTemp":4,
      "currentWeather":"thunder",
      "tempInDetail":{
         "precipitation":30,
         "wind":8,
         "humidity":30,
         "pressure":840
      },
      "nearByCity":[
         {
            "locality":"Los Angeles, CA",
            "currentTemp":11,
            "currentWeather":"tornado"
         },
         {
            "locality":"New York",
            "currentTemp":4,
            "currentWeather":"night"
         },
          {
            "locality":"Los Angeles, CA",
            "currentTemp":11,
            "currentWeather":"heavy_rain"
         },
         {
            "locality":"New York",
            "currentTemp":4,
            "currentWeather":"clear"
         }
      ],
      "tempByTime":[
         {
            "time":"01:00 am",
            "temp":"8",
            "weather":"cloudy"
         },
         {
            "time":"02:00 am",
            "temp":"9",
            "weather":"rainy"
         },
         {
            "time":"03:00 am",
            "temp":"11",
            "weather":"night"
         },
         {
            "time":"04:00 am",
            "temp":"12",
            "weather":"tornado"
         },
         {
            "time":"05:00 am",
            "temp":"11",
            "weather":"sleet"
         },
         {
            "time":"06:00 am",
            "temp":"10",
            "weather":"partial_cloudy"
         },
         {
            "time":"07:00 am",
            "temp":"13",
            "weather":"thunderstorm"
         },
         {
            "time":"08:00 am",
            "temp":"14",
            "weather":"fog"
         },
         {
            "time":"09:00 am",
            "temp":"13",
            "weather":"stormy"
         },
         {
            "time":"10:00 am",
            "temp":"15",
            "weather":"partly_cloudy"
         },
         {
            "time":"11:00 am",
            "temp":"16",
            "weather":"sunny"
         },
         {
            "time":"12:00 pm",
            "temp":"17",
            "weather":"heavy_rain"
         },
         {
            "time":"01:00 pm",
            "temp":"8",
            "weather":"little_sunny"
         },
         {
            "time":"02:00 pm",
            "temp":"9",
            "weather":"very_rain"
         },
         {
            "time":"03:00 pm",
            "temp":"11",
            "weather":"tornado"
         },
         {
            "time":"04:00 pm",
            "temp":"12",
            "weather":"lightning"
         },
         {
            "time":"05:00 pm",
            "temp":"11",
            "weather":"heavy_snow"
         },
         {
            "time":"06:00 pm",
            "temp":"10",
            "weather":"snow"
         },
         {
            "time":"07:00 pm",
            "temp":"13",
            "weather":"snowflake"
         },
         {
            "time":"08:00 pm",
            "temp":"14",
            "weather":"heavy_snow"
         },
         {
            "time":"09:00 pm",
            "temp":"13",
            "weather":"cloudy"
         },
         {
            "time":"10:00 pm",
            "temp":"15",
            "weather":"cloudy_night"
         },
         {
            "time":"11:00 pm",
            "temp":"16",
            "weather":"cloudy_night"
         },
         {
            "time":"12:00 am",
            "temp":"17",
            "weather":"stormy"
         }
      ]
   }
]
    """

    fun getWeatherDataObject(): MutableList<WeatherDataItem> {
        return Gson().fromJson(weatherJSONData, WeatherData::class.java).toMutableList()
    }
}