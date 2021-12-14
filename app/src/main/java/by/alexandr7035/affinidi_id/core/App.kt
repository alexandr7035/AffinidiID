package by.alexandr7035.affinidi_id.core

import android.app.Application
import by.alexandr7035.affinidi_id.BuildConfig
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        // Setup timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}