package info.miguelcatalan.takemethere.search

import com.mapbox.mapboxsdk.geometry.LatLng
import info.miguelcatalan.takemethere.base.BasePresenter

class SearchPresenter : BasePresenter<SearchView>() {

    private var pickup: LatLng? = null
    private var dropOff: LatLng? = null

    override fun initialize() {}

    fun onMapReady() {
        getView().centerMapAt(38.0847779, -0.9464973, 12.0)
    }

    fun onMapPressed(position: LatLng) {
        if (pickup == null) {
            setUpPickup(position)
        } else if (dropOff == null) {
            setUpDropOff(position)
            getView().showNavigateButton()
        } else {
            resetLocations()
            setUpPickup(position)
        }
    }

    private fun resetLocations() {
        pickup = null
        dropOff = null
        getView().clearMap()
    }

    private fun setUpPickup(position: LatLng) {
        pickup = position
        getView().drawPickUp(position)
    }

    private fun setUpDropOff(position: LatLng) {
        dropOff = position
        getView().drawDropOff(position)
    }

    fun onNavigatePressed() {
        if (dropOff != null && pickup != null) {
            getView().navigateToNavigation(pickup!!, dropOff!!)
        }
    }
}