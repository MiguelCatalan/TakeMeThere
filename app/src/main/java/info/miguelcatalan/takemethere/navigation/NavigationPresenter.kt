package info.miguelcatalan.takemethere.navigation

import com.mapbox.mapboxsdk.geometry.LatLng
import info.miguelcatalan.takemethere.base.BasePresenter

class NavigationPresenter : BasePresenter<NavigationView>() {

    var pickUp: LatLng? = null
    var dropOff: LatLng? = null

    override fun initialize() {
        pickUp = getView().getPickUpLocation()
        dropOff = getView().getDropOffLocation()
    }

}