<h1 align="center">My Divar</h1></br>

<p align="center">
  <a href="https://android-arsenal.com/api?level=17"><img alt="API" src="https://img.shields.io/badge/API-17%2B-brightgreen.svg?style=flat"/></a>
<!--   <a href="https://github.com/hamta-niknazar"><img alt="Profile" src="https://skydoves.github.io/badges/skydoves.svg"/></a>  -->
</p>

<p align="center">
🎺 An application inspired by Divar using a crawler to get data from the real divar website.
</p>
<p align="center">
<img src="https://github.com/taherfattahi/divar_application/blob/master/gifs/2.gif" width="25%"/>
<img src="https://github.com/taherfattahi/divar_application/blob/master/gifs/1.gif" width="25%"/>
<img src="https://github.com/taherfattahi/divar_application/blob/master/gifs/4.gif" width="25%"/>
</p>

Project dependencies:
```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

    // An image loading and caching library for Android focused on smooth scrolling
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

    // A Complete Fast Android Networking Library that also supports HTTP/2
    implementation 'com.amitshekhar.android:android-networking:1.0.2'

    // Android Auto Image Slider
    implementation 'com.github.smarteist:autoimageslider:1.4.0'

    // Mapbox Navigation SDK for Android
    implementation 'com.mapbox.mapboxsdk:mapbox-android-sdk:7.1.2'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-locationlayer:0.11.0'

    // A simple SearchView for Android based on Material Design
    implementation 'com.github.Ferfalk:SimpleSearchView:0.1.6'

    // A pull to refresh layout for android, the RecyclerRefreshLayout is based on the SwipeRefreshLayout.
    implementation 'com.dinuscxj:recyclerrefreshlayout:2.0.5'

    // Render After Effects animations natively on Android and iOS, Web, and React Native
    implementation 'com.airbnb.android:lottie:3.5.0'
}
```


<img src="https://github.com/taherfattahi/divar_application/blob/master/gifs/3.gif" align="right" width="20%"/>

<img src="https://github.com/taherfattahi/divar_application/blob/master/gifs/5.gif" align="right" width="20%"/>


### Features
`Splash Screen` is the first screen visible to the user when the application's launched. Splash screens are used to display some animations (typically of the application logo) and illustrations while some data for the next screens are fetched. Here, datas are fetched from divar api by a crawler which is : <a href="https://github.com/hamta-niknazar/MyDivarCrawler">My Divar Crawler</a>

`Linear Recyclerview` RecyclerView makes it easy to efficiently display large sets of data. You supply the data and define how each item looks, and the RecyclerView library dynamically creates the elements when they're needed.

As the name implies, RecyclerView recycles those individual elements. When an item scrolls off the screen, RecyclerView doesn't destroy its view. Instead, RecyclerView reuses the view for new items that have scrolled onscreen. This reuse vastly improves performance, improving your app's responsiveness and reducing power consumption.

`swipe Refresh Layout` A pull to refresh layout for android, the RecyclerRefreshLayout is based on the SwipeRefreshLayout. support all the views, highly customizable, code simplicity, etc. really a practical RefreshLayout!

`Search Bar` A simple SearchView for Android based on Material Design

`Auto Slider` This is an amazing image slider for the Android. You can easily load images with your custom layout, and there are many kinds of amazing animations you can choose.

`Map Box` The Mapbox Maps SDK for Android is a toolset for displaying maps inside of your Android application.


## Find this repository useful? ❤️
[follow me](https://github.com/hamta-niknazar) for my next creations! 🤩
