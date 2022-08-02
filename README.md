<div align="center">
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/light-dark%20cover.png?raw=true" width=75%>
</div>

# F1-Calendar
***F1-Calendar*** is a lightweight Android application developed in [Kotlin](https://kotlinlang.org/) designed for tracking race weekends in a given F1 season.

- Supporting seasons *(1950 - 2022)*
- Circuit [location](https://www.google.com/maps) and more [information](https://www.wikipedia.org/)
- Race season data caching *(local DB)* for availability without Internet connection
- No Internet error handling
- light and dark mode

Application uses [Ergast F1 Api](https://ergast.com/mrd/) as data source

This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - programming language for Android development
- [RxJava/RxAndroid](https://github.com/ReactiveX/RxAndroid) - For asynchronous operations
- [Dagger-Hilt](https://dagger.dev/hilt/) - Dependency Injection
- [Retrofit](https://square.github.io/retrofit/) - HTTP client for Android
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
- [Connectivity Manager](https://developer.android.com/training/monitoring-device-state/connectivity-status-type) - Monitoring connection to the Internet
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
- [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Google Maps API](https://developers.google.com/maps) - Map View
- [Chrome Custom Tabs](https://developer.chrome.com/docs/android/custom-tabs/) - Displaying WEB content
- [Jetpack Navigation](https://developer.android.com/guide/navigation) - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app
- [Material Components for Android](https://material.io/develop/android) - Modular and customizable Material Design UI components for Android.
- [Multiple View Types in Recycler View](https://www.geeksforgeeks.org/how-to-create-recyclerview-with-multiple-viewtype-in-android/) - Collapsable Header type in the list (Grand Prix)
- [Custom Decoration Drawing](https://developer.android.com/training/custom-views/custom-drawing) - For drawing 1px borders on list items

# Preview
## Splash & Home Screen (dark & light)
<div align="center">
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/light-dark%20splash.png?raw=true" width=50%><br>
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_dark_home.png?raw=true" width=27%>
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_light_home.png?raw=true" width=27%>
</div>
<br/><br/>

## Season Select
<br/><br/>
<div align="center">
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_light_season_select.png?raw=true" width=27%>&nbsp;&nbsp;&nbsp;&nbsp;
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_dark_season_select.png?raw=true" width=27%>
</div>
<br/><br/>

## Map View & Web Content
<br/><br/>
<div align="center">
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_map.png?raw=true" width=27%>&nbsp;&nbsp;&nbsp;&nbsp;
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_wiki.png?raw=true" width=27%>
</div>
<br/><br/>

## Connection Error handling
<br/><br/>
<div align="center">
    <img src="https://github.com/mkostic21/F1-calendar/blob/master/screenshots/Screenshot_light_internet_error.png?raw=true" width=27%>
</div>