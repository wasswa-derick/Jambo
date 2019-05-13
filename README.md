[![CircleCI](https://circleci.com/gh/wasswa-derick/Jambo/tree/develop.svg?style=svg)](https://circleci.com/gh/wasswa-derick/Jambo/tree/develop)
[![Maintainability](https://api.codeclimate.com/v1/badges/711c48ecd54746ec86d9/maintainability)](https://codeclimate.com/github/wasswa-derick/Jambo/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/711c48ecd54746ec86d9/test_coverage)](https://codeclimate.com/github/wasswa-derick/Jambo/test_coverage)

Jambo
=====

A news application that shows articles based a user's current location as well as other categories for locations such as Kampala (Uganda), Kigali (Rwanda), Nairobi (Kenya), Lagos (Nigeria), and New York (USA).


### [Jambo on Google Play Store](https://play.google.com/store/apps/details?id=com.rosen.jambo)
### [Jambo on Amazon App Store](https://www.amazon.com/Andela-Jambo-News/dp/B07RLQ8BMP/ref=sr_1_1?keywords=Jambo+News&qid=1557739030&s=gateway&sr=8-1-spell)


## Screenshots

<img src="https://user-images.githubusercontent.com/39955231/57185490-4f5b8c80-6ed5-11e9-84dc-e5a793204646.png" alt="Screenshot_2019-05-05-01-07-24" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185491-4ff42300-6ed5-11e9-824d-23aa018a6483.png" alt="Screenshot_2019-05-05-01-09-18" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185492-4ff42300-6ed5-11e9-93f3-f5282bd88667.png" alt="Screenshot_2019-05-05-01-10-12" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185493-508cb980-6ed5-11e9-8db0-5cebdea5aaa7.png" alt="Screenshot_2019-05-05-01-10-38" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185494-508cb980-6ed5-11e9-94a9-9db9179be0ab.png" alt="Screenshot_2019-05-05-01-11-18" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185495-51255000-6ed5-11e9-8220-306ee47b7e62.png" alt="Screenshot_2019-05-05-01-11-35" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185496-51255000-6ed5-11e9-9eea-f8e6a7e3a980.png" alt="Screenshot_2019-05-05-01-11-45" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185497-51bde680-6ed5-11e9-9d9f-e75525c4e80a.png" alt="Screenshot_2019-05-05-01-12-00" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185499-52567d00-6ed5-11e9-96ed-e761b043a666.png" alt="Screenshot_2019-05-05-01-12-11" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185501-52ef1380-6ed5-11e9-9c27-edbc2b8afbad.png" alt="Screenshot_2019-05-05-01-13-05" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185502-52ef1380-6ed5-11e9-8083-f9003a7e25d3.png" alt="Screenshot_2019-05-05-01-13-16" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57185503-54204080-6ed5-11e9-96fa-1d226cc6ecaf.png" alt="Screenshot_2019-05-05-01-13-31" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185504-54b8d700-6ed5-11e9-88e5-6c5cfbe7930e.png" alt="Screenshot_2019-05-05-01-13-37" width="200" /> <img src="https://user-images.githubusercontent.com/39955231/57198569-137c0200-6f7d-11e9-948c-1b9bfa9cdab5.png" alt="Screenshot_2019-05-05-01-14-45" width="200" />
<img src="https://user-images.githubusercontent.com/39955231/57185505-54b8d700-6ed5-11e9-9ca1-e68607f70652.png" alt="Screenshot_2019-05-05-01-13-59" width="200" />

## Getting Started and Installation

1. Open up the terminal and clone the repository under any directory using `git clone https://github.com/wasswa-derick/Jambo.git`.

2. In Android Studio, under the file menu select open, then select an existing project and navigate to the project you just cloned.

3. Build the project using `./gradlew build`.

4. Run the application on a connected device or emulator.


### Prerequisites

1. [Set up Android Studio](https://developer.android.com/studio/install)

2. [Enable Kotlin in Android Studio](https://medium.com/@elye.project/setup-kotlin-for-android-studio-1bffdf1362e8)

3. [Run application on emulator](https://developer.android.com/studio/run/emulator)

4. [Run application on android device](https://developer.android.com/studio/run/device)


## Running the tests (Unit and Instrumentation tests)

1. Run tests using `./gradlew test` or `./gradlew jacocoTestReport`.

Unit tests can be run using one of the following:
~~~~
./gradlew test
~~~~

Jacoco Tests report (Run both instrumented and unit tests at once).
~~~~
./gradlew jacocoTestReport
~~~~

2. To run instrumentation tests, endeavor to have an emulator or an Android Device connected either via USB or WIFI.


## Architecture
* Model View ViewModel (MVVM)


## Consumed API Endpoints

```
    https://newsapi.org/v2/everything?q={query}&apiKey={key}
```


## Dependencies

* [Android](https://www.android.com/) - Operating System
* [Retrofit](https://square.github.io/retrofit/) - HTTP Requests
* [Kotlin](https://kotlinlang.org/) - Programing language
* [Room](https://developer.android.com/topic/libraries/architecture/room) - Local database
* [Dagger 2](https://github.com/google/dagger) - Dependency Injection
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Asynchronous and event-based functionality
* [RxJava](https://github.com/ReactiveX/RxJava) - Asynchronous and event-based functionality
* [Picasso](http://square.github.io/picasso/) - Image processing
* [Google Maps API](https://developers.google.com/maps/documentation/) - Maps
* [Navigation Drawer](https://github.com/PSD-Company/duo-navigation-drawer) - Navigation
* [GPS Monitor](https://github.com/ankitdubey021/GPSTracker) - Device Location


## Authors
[Derick Wasswa](https://github.com/wasswa-derick)


## Credits
All props to [NewsAPI.org](https://newsapi.org) for availing the service.
