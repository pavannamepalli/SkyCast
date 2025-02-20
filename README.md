# Skycast Android App

Skycast is a feature-rich Android weather application that provides real-time weather conditions and forecasts for users based on their current location or a specified city. The app is designed using the MVVM (Model-View-ViewModel) architecture to ensure scalability and maintainability. It leverages the AccuWeather API for reliable and accurate weather data.

---

## Features

### ✔ Real-time Weather Updates
- Fetch and display current weather conditions, including temperature, humidity, wind speed, and precipitation levels.
- Provides an intuitive user interface with visually appealing weather representations.

### ✔ Daily & Weekly Forecasts
- Offers detailed weather forecasts for the next few days, including minimum and maximum temperatures, weather conditions, and precipitation chances.
- Graphical representation of weather trends to enhance readability.

### ✔ Location-based Weather Data
- Uses GPS and network location services to determine the user’s current location and fetch corresponding weather information.
- Option to manually search for weather data of any city worldwide.

### ✔ AccuWeather API Integration
- Seamless integration with the AccuWeather API to provide accurate and up-to-date weather data.
- Ensures data reliability with fast and efficient API calls.

### ✔ User-friendly UI/UX
- Clean and modern UI following Material Design principles.
- Dark mode support for improved user experience in low-light conditions.

### ✔ MVVM Architecture
- Maintains a clean architecture using the MVVM design pattern for better code management.
- Separation of concerns makes the codebase easy to maintain and extend.

---

## Getting Started

Follow these instructions to set up and run the Skycast project on your local machine.

### Prerequisites
Ensure you have the following tools installed:
- **Android Studio** (latest stable version)
- **JDK 11+**
- **An active AccuWeather API key**

### Setup Instructions
1. **Clone the Repository**
   ```sh
   git clone https://github.com/your-repo/skycast.git
   cd skycast
   ```
2. **Open the Project in Android Studio**
   - Launch Android Studio and open the cloned project.
   - Allow Gradle to sync dependencies.

3. **Obtain an AccuWeather API Key**
   - Visit [AccuWeather API](https://developer.accuweather.com/) and sign up for an API key.
   - Replace `YOUR_ACCUWEATHER_API_KEY` in the code with your actual API key.

4. **Run the App**
   - Connect a physical device or use an emulator.
   - Click the **Run** button in Android Studio to build and launch the app.

---

## Project Structure
Skycast follows the MVVM architecture pattern for better separation of concerns. Below is the directory structure:
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/skycast/
│   │   │   ├── data/            # Repository and API calls
│   │   │   ├── model/           # Data models
│   │   │   ├── ui/              # UI components (Activities/Fragments)
│   │   │   ├── viewmodel/       # ViewModels for UI interaction
│   │   │   ├── utils/           # Utility classes
│   │   ├── res/                 # Layout and drawable resources
```
---

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

---

## Acknowledgments
Special thanks to [AccuWeather](https://developer.accuweather.com/) for providing the weather API service.

---

## Author
[Pavan Namepalli](https://www.linkedin.com/in/pavan-namepalli/)

