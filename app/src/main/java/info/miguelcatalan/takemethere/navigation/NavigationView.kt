package info.miguelcatalan.takemethere.navigation

import com.mapbox.mapboxsdk.geometry.LatLng
import info.miguelcatalan.takemethere.base.BaseView

interface NavigationView : BaseView {
    fun getPickUpLocation(): LatLng
    fun getDropOffLocation(): LatLng
}