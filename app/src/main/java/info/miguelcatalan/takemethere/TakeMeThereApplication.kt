package info.miguelcatalan.takemethere

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox
import info.miguelcatalan.takemethere.utils.MapboxUtils

class TakeMeThereApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val mapboxAccessToken = MapboxUtils.getMapboxAccessToken(applicationContext)
        Mapbox.getInstance(applicationContext, mapboxAccessToken)
    }
}