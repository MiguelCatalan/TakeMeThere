package info.miguelcatalan.takemethere.navigation

import android.content.Context
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.api.directions.v5.models.DirectionsRoute
import info.miguelcatalan.takemethere.base.BaseView

interface NavigationView : BaseView {
    fun getContext(): Context
    fun getPickUpLocation(): LatLng
    fun getDropOffLocation(): LatLng
    fun showCalculatingRoute()
    fun hideCalculatingRoute()
    fun showErrorCalculatingRoute()
    fun drawRoute(directionsRoute: DirectionsRoute?)
    fun centerMapAt(latitude: Double, longitude: Double, zoom: Double)
    fun easeCamera(tilt: Double, zoom: Double, target: LatLng, bearing: Double)
}