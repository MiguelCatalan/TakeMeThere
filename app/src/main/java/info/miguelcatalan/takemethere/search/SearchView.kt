package info.miguelcatalan.takemethere.search

import com.mapbox.mapboxsdk.geometry.LatLng
import info.miguelcatalan.takemethere.base.BaseView

interface SearchView : BaseView {
    fun centerMapAt(latitude: Double, longitude: Double, zoom: Double)
    fun clearMap()
    fun drawDropOff(position: LatLng)
    fun showNavigateButton()
    fun navigateToNavigation(dropOff: LatLng)
}