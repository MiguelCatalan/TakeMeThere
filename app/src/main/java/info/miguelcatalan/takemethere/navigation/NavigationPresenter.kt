package info.miguelcatalan.takemethere.navigation

import android.location.Location
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.services.android.navigation.v5.MapboxNavigation
import com.mapbox.services.android.navigation.v5.NavigationConstants.DEPART_ALERT_LEVEL
import com.mapbox.services.android.navigation.v5.NavigationConstants.HIGH_ALERT_LEVEL
import com.mapbox.services.android.navigation.v5.NavigationConstants.MEDIUM_ALERT_LEVEL
import com.mapbox.services.android.navigation.v5.RouteProgress
import com.mapbox.services.android.telemetry.location.AndroidLocationEngine
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.api.directions.v5.models.DirectionsResponse
import com.mapbox.services.api.directions.v5.models.DirectionsRoute
import com.mapbox.services.api.utils.turf.TurfConstants
import com.mapbox.services.api.utils.turf.TurfMeasurement
import com.mapbox.services.commons.models.Position
import info.miguelcatalan.takemethere.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NavigationPresenter : BasePresenter<NavigationView>() {

    private var pickUp: LatLng? = null
    private var dropOff: LatLng? = null
    private var navigation: MapboxNavigation? = null
    private var directionsRoute: DirectionsRoute? = null

    override fun initialize() {

        val locationEngine = AndroidLocationEngine(getView().getContext())
        locationEngine.interval = 0
        locationEngine.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine.fastestInterval = 1000

        navigation = MapboxNavigation(getView().getContext(), Mapbox.getAccessToken())
        navigation?.setLocationEngine(locationEngine)
        navigation?.addProgressChangeListener {
            location, routeProgress ->
            onProgressChanged(location, routeProgress)
        }
        navigation?.addAlertLevelChangeListener {
            alertLevel, routeProgress ->
            onAlertLevelChanged(alertLevel, routeProgress)
        }
        navigation?.addOffRouteListener {
            onOffRoute(it)
        }

        directionsRoute?.let {
            navigation?.startNavigation(directionsRoute)
        }

        locationEngine.activate()

        pickUp = getView().getPickUpLocation()
        dropOff = getView().getDropOffLocation()
    }

    private fun onOffRoute(newUserLocation: Location?) {
        newUserLocation?.let {
            val pickup = it
            dropOff?.let {
                getRoute(LatLng(pickup.latitude, pickup.longitude), it)
            }
        }
    }

    private fun onProgressChanged(location: Location, routeProgress: RouteProgress) {
        val targetPosition: Position = TurfMeasurement.destination(
                Position.fromCoordinates(location.longitude, location.latitude),
                60.0,
                location.bearing.toDouble(),
                TurfConstants.UNIT_METERS)

        getView().easeCamera(60.0, 17.0, LatLng(targetPosition.latitude, targetPosition.longitude), location.bearing.toDouble())

        getView().updateProgress(routeProgress.fractionTraveled * 100, routeProgress.durationRemaining, routeProgress.distanceRemaining)
    }

    private fun onAlertLevelChanged(alertLevel: Int, routeProgress: RouteProgress?) {
        if (alertLevel == DEPART_ALERT_LEVEL) {
            routeProgress?.currentLegProgress?.upComingStep?.let {
                getView().startSteps(it)
            }
        } else if (alertLevel == HIGH_ALERT_LEVEL || alertLevel == MEDIUM_ALERT_LEVEL) {
            routeProgress?.currentLegProgress?.upComingStep?.let {
                getView().updateStep(it)
            }
        }

        routeProgress?.currentLegProgress?.upComingStep?.distance?.let {
            getView().updateDistance(it)
        }
    }

    fun onMapReady() {
        if (pickUp != null && dropOff != null) {
            getView().centerMapAt(pickUp!!.latitude, pickUp!!.longitude, 15.0)
            getRoute(pickUp!!, dropOff!!)
        }
    }

    fun onStart() {
        navigation?.onStart()
    }

    fun onStop() {
        navigation?.onStop()
    }

    private fun getRoute(pickUp: LatLng, dropOff: LatLng) {
        getView().showCalculatingRoute()

        val origin = Position.fromCoordinates(pickUp.longitude, pickUp.latitude)
        val destination = Position.fromCoordinates(dropOff.longitude, dropOff.latitude)

        navigation?.getRoute(origin, destination, RouteDirectionCallback())
    }

    private fun onRouteCalculated(directionsRoute: DirectionsRoute?) {
        this.directionsRoute = directionsRoute
        navigation?.startNavigation(directionsRoute)
        getView().hideCalculatingRoute()
        getView().drawRoute(directionsRoute)
    }

    private fun onRouteCalculatedError() {
        getView().hideCalculatingRoute()
        getView().showErrorCalculatingRoute()
    }

    inner class RouteDirectionCallback : Callback<DirectionsResponse> {

        override fun onResponse(call: Call<DirectionsResponse>?, response: Response<DirectionsResponse>?) {
            onRouteCalculated(response?.body()?.routes?.get(0))
        }

        override fun onFailure(call: Call<DirectionsResponse>?, throwable: Throwable?) {
            onRouteCalculatedError()
        }

    }

}