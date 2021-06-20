package com.popcorn.cakey

import android.app.Application
import com.parse.Parse


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("YOUR_APP_ID")
                .clientKey("YOUR_CLIENT_KEY")
                .server("http://localhost:1337/parse/")
                .build()
        )
    }
}