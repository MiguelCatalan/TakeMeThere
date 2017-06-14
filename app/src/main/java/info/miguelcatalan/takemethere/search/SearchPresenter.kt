package info.miguelcatalan.takemethere.search

import com.mapbox.mapboxsdk.geometry.LatLng
import info.miguelcatalan.takemethere.base.BasePresenter

class SearchPresenter : BasePresenter<SearchView>() {

    private var dropOff: LatLng? = null

    override fun initialize() {}

    fun onMapReady() {
    }

    fun onMapPressed(position: LatLng) {
        resetLocations()
        setUpDropOff(position)
        getView().showNavigateButton()
    }

    private fun resetLocations() {
        dropOff = null
        getView().clearMap()
    }

    private fun setUpDropOff(position: LatLng) {
        dropOff = position
        getView().drawDropOff(position)
    }

    fun onNavigatePressed() {
        dropOff?.let {
            getView().navigateToNavigation(it)
        }

    }
}