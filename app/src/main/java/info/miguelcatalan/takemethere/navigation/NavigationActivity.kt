package info.miguelcatalan.takemethere.navigation

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mapbox.mapboxsdk.annotations.Polyline
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.services.Constants.PRECISION_6
import com.mapbox.services.api.directions.v5.models.DirectionsRoute
import com.mapbox.services.api.directions.v5.models.LegStep
import com.mapbox.services.commons.geojson.LineString
import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseActivity<NavigationView, NavigationPresenter>(), NavigationView, OnMapReadyCallback {
    companion object {

        const val PICKUP_POSITION = "pickup_position"

        const val DROPOFF_POSITION = "dropoff_position"
        const val TRACK_WIDTH = 9f
    }

    private var mapboxMap: MapboxMap? = null
    private var routeLine: Polyline? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_navigation
    }

    override fun createPresenter(): NavigationPresenter {
        return NavigationPresenter()
    }

    override fun view(): NavigationView {
        return this
    }

    override fun onMapReady(mapboxMap: MapboxMap?) {
        mapboxMap?.isMyLocationEnabled = true
        mapboxMap?.uiSettings?.isCompassEnabled = false
        this.mapboxMap = mapboxMap
        getPresenter().onMapReady()
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        getPresenter().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun getPickUpLocation(): LatLng {
        return intent.getParcelableExtra(PICKUP_POSITION)
    }

    override fun getDropOffLocation(): LatLng {
        return intent.getParcelableExtra(DROPOFF_POSITION)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showCalculatingRoute() {
        Toast.makeText(this, "Calculating route", Toast.LENGTH_SHORT).show()
    }

    override fun hideCalculatingRoute() {
        Toast.makeText(this, "Route calculated", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorCalculatingRoute() {
        Toast.makeText(this, "Error route calculated", Toast.LENGTH_SHORT).show()
    }

    override fun drawRoute(route: DirectionsRoute?) {
        val positions = LineString.fromPolyline(route?.geometry, PRECISION_6).coordinates
        val latLngs = ArrayList<LatLng>()
        positions.mapTo(latLngs) { LatLng(it.latitude, it.longitude) }

        routeLine?.let {
            mapboxMap?.removePolyline(it)
        }

        routeLine = mapboxMap?.addPolyline(PolylineOptions()
                .addAll(latLngs)
                .color(Color.parseColor("#56b881"))
                .width(TRACK_WIDTH))
    }

    override fun centerMapAt(latitude: Double, longitude: Double, zoom: Double) {

        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(zoom)
                .build()

        mapboxMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun easeCamera(tilt: Double, zoom: Double, target: LatLng, bearing: Double) {

        val cameraPosition = CameraPosition.Builder()
                .tilt(tilt)
                .zoom(zoom)
                .target(target)
                .bearing(bearing)
                .build()

        mapboxMap?.easeCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, false)
    }

    override fun startSteps(currentStep: LegStep) {
        indicatorView.visibility = View.VISIBLE
        indicatorView.setManeuverDistance(currentStep.distance.toString(), "meters")
        indicatorView.setManeuverTurn(currentStep.maneuver.instruction)
    }

    override fun updateStep(currentStep: LegStep) {
        indicatorView.visibility = View.VISIBLE
        indicatorView.setManeuverDistance(currentStep.distance.toString(), "meters")
        indicatorView.setManeuverTurn(currentStep.maneuver.instruction)
    }

    override fun updateDistance(distance: Double) {
        indicatorView.setManeuverDistance(distance.toString(), "meters")
    }

}
