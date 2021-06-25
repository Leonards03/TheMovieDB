# The Movie DB - Dummy

A Dummy app that consuming TMDB API for Dicoding's Creating Android App Expert Course Submission

[![Leonards03](https://circleci.com/gh/Leonards03/TheMovieDB-Dummy.svg?style=svg)](https://circleci.com/gh/Leonards03/TheMovieDB-Dummy)


## Installation

Clone this repository:
```
git clone https://github.com/Leonards03/TheMovieDB-Dummy.git
```
or Download the project directly from Github.

Replace <TMDB_API_KEY> with your own The Movie DB API Key, for the key generation guide you can find the details [here](https://developers.themoviedb.org/3/getting-started/introduction)
```
    defaultConfig {
        ...

        buildConfigField("String", "TMDB_API_KEY", '"<TMDB_API_KEY>"')

        ...
    }
```

## Libraries used
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Material Design](https://material.io/develop/android/docs/getting-started)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
    - [Room](https://developer.android.com/topic/libraries/architecture/room)
    - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
    - [Hilt Android](https://developer.android.com/training/dependency-injection/hilt-android)
- [Shimmer](https://github.com/facebook/shimmer-android)
- [Glide](https://github.com/bumptech/glide)
- [Lottie-Android](https://github.com/airbnb/lottie-android)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](http://square.github.io/okhttp/)
- [Gson](https://github.com/google/gson)
- [Timber](http://jakewharton.github.io/timber/)

### Test
- [MockK](https://mockk.io/)
- [Turbine](https://cashapp.github.io/turbine/docs/0.x/)
- [Kotest Assertions](https://kotest.io/docs/assertions/assertions.html)