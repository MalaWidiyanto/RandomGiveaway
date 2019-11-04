package com.malawidiyanto.randomgiveaway

import android.app.Application
import io.paperdb.Paper
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)

        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}