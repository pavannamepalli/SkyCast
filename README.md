# SkyCast Weather App

A modern Android weather application built with Kotlin that provides real-time weather conditions and forecasts using the AccuWeather API.

## Features

- **Real-time Weather Data**: Current temperature, humidity, wind speed, and atmospheric pressure
- **5-Day Forecast**: Detailed weather predictions with temperature ranges and conditions
- **Location Services**: GPS-based weather data for current location
- **City Search**: Search and get weather for any city worldwide
- **Offline Support**: Cached data for offline viewing
- **Material Design**: Clean, modern UI following Google's design guidelines

## Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: Material Design with ViewBinding
- **Networking**: Retrofit2 with Gson
- **Image Loading**: Picasso
- **Location**: Google Play Services Location
- **Async Operations**: Kotlin Coroutines
- **Data Persistence**: SharedPreferences

## Project Structure

```
app/
├── src/main/java/com/example/skycast/
│   ├── data/
│   │   ├── apiinterface/
│   │   │   └── ApiService.kt
│   │   ├── model/
│   │   │   ├── currentcondition/
│   │   │   ├── dailyforcast/
│   │   │   ├── geoposition/
│   │   │   └── searchcities/
│   │   ├── repository/
│   │   │   ├── HomeRepository.kt
│   │   │   └── SearchRepository.kt
│   │   └── retrofit/
│   │       └── RetrofitClient.kt
│   ├── ui/
│   │   ├── adapter/
│   │   │   ├── ForeCastAdapter.kt
│   │   │   ├── LocalistListAdapter.kt
│   │   │   └── onItemClickListener.kt
│   │   ├── fragment/
│   │   │   ├── HomeFragment.kt
│   │   │   ├── SearchFragment.kt
│   │   │   └── SettingsFragment.kt
│   │   └── viewmodel/
│   │       ├── HomeViewModel.kt
│   │       └── SearchViewModel.kt
│   ├── utils/
│   │   ├── Constants.kt
│   │   ├── NetworkUtils.kt
│   │   ├── NoInternetDialogFragment.kt
│   │   ├── Resource.kt
│   │   └── Utils.kt
│   ├── viewmodelfactory/
│   │   ├── HomeViewModelFactory.kt
│   │   └── SearchViewModelFactory.kt
│   └── MainActivity.kt
└── src/main/res/
    ├── drawable/
    ├── layout/
    ├── values/
    └── menu/
```

## Setup Instructions

### Prerequisites
- Android Studio (latest stable version)
- JDK 11+
- AccuWeather API key

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-repo/skycast.git
   cd skycast
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Open the cloned project
   - Wait for Gradle sync to complete

3. **Configure API Key**
   - Get your AccuWeather API key from [AccuWeather Developer Portal](https://developer.accuweather.com/)
   - Update the API key in `app/build.gradle.kts`:
   ```kotlin
   buildConfigField("String", "ACCUWEATHER_API_KEY", "\"YOUR_API_KEY_HERE\"")
   ```

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the Run button in Android Studio

## API Configuration

The app uses the AccuWeather API with the following endpoints:
- **Geoposition Search**: Get location details from coordinates
- **Current Conditions**: Real-time weather data
- **5-Day Forecast**: Weather predictions
- **City Autocomplete**: Search functionality

## Architecture Details

### MVVM Pattern
- **Model**: Data classes representing weather information
- **View**: Fragments with ViewBinding for UI
- **ViewModel**: Business logic and data management

### Repository Pattern
- Abstracts data sources (API and local storage)
- Handles caching and offline functionality
- Provides single source of truth for data

### Resource Wrapper
- Sealed class for handling API states (Success, Error, Loading)
- Consistent error handling across the app

## Key Components

### HomeFragment
- Displays current weather conditions
- Shows 5-day forecast
- Handles location permissions and GPS services
- Real-time weather updates

### SearchFragment
- City search with autocomplete
- Location selection and navigation
- Real-time search results

### Data Models
- **CurrentTemp**: Current weather conditions
- **DailyForcast**: 5-day weather predictions
- **GeoPositionData**: Location information
- **LocationList**: Search results

## Permissions

The app requires the following permissions:
- `ACCESS_FINE_LOCATION`: For GPS-based weather
- `ACCESS_COARSE_LOCATION`: For approximate location
- `INTERNET`: For API calls
- `ACCESS_NETWORK_STATE`: For network status checking

## Build Configuration

- **Target SDK**: 33 (Android 13)
- **Min SDK**: 24 (Android 7.0)
- **Kotlin**: 1.8.0
- **Gradle**: 8.1.0


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

[Pavan Namepalli](https://www.linkedin.com/in/pavan-namepalli/)
