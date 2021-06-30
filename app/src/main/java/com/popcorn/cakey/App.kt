package com.popcorn.cakey

import android.app.Application
import com.parse.Parse

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("cakeySE")
                .clientKey("YOUR_CLIENT_KEY")
                .server("https://cakey-se.herokuapp.com/parse/")
                .build()
        )
    }
}