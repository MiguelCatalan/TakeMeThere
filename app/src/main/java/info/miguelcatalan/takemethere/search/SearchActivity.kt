package info.miguelcatalan.takemethere.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.constants.MyLocationTracking
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.services.android.telemetry.location.AndroidLocationEngine
import com.mapbox.services.android.telemetry.location.LocationEnginePriority
import com.mapbox.services.android.telemetry.permissions.PermissionsManager
import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.base.BaseActivity
import info.miguelcatalan.takemethere.navigation.NavigationActivity
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView, OnMapReadyCallback {

    var mapboxMap: MapboxMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_search
    }

    override fun createPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override fun view(): SearchView {
        return this
    }

    override fun setupViews() {
        super.setupViews()
        navigateButton.setOnClickListener {
            getPresenter().onNavigatePressed()
        }
    }

    public override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setOnMapClickListener {
            getPresenter().onMapPressed(it)
        }

        val locationEngine = AndroidLocationEngine(this)
        locationEngine.interval = 0
        locationEngine.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine.fastestInterval = 1000
        locationEngine.activate()

        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapboxMap.isMyLocationEnabled = true
            mapboxMap.trackingSettings.myLocationTrackingMode = MyLocationTracking.TRACKING_FOLLOW
            mapboxMap.trackingSettings.setDismissAllTrackingOnGesture(false)
        }
        mapboxMap.moveCamera(CameraUpdateFactory.zoomBy(12.0))
        mapboxMap.setLocationSource(locationEngine)

        this.mapboxMap = mapboxMap
        getPresenter().onMapReady()
    }

    override fun centerMapAt(latitude: Double, longitude: Double, zoom: Double) {
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(zoom)
                .build()

        mapboxMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun clearMap() {
        mapboxMap?.clear()
    }

    override fun drawDropOff(position: LatLng) {
        mapboxMap?.addMarker(MarkerOptions()
                .position(position)
                .title("DropOff"))
    }

    override fun showNavigateButton() {
        navigateContainer.visibility = View.VISIBLE
    }

    override fun navigateToNavigation(dropOff: LatLng) {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.putExtra(NavigationActivity.DROPOFF_POSITION, dropOff)
        startActivity(intent)
    }

}
