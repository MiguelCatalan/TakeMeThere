package info.miguelcatalan.takemethere.search

import android.os.Bundle
import android.view.View
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.base.BaseActivity
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

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setOnMapClickListener {
            getPresenter().onMapPressed(it)
        }

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

    override fun drawPickUp(position: LatLng) {
        mapboxMap?.addMarker(MarkerOptions()
                .position(position)
                .title("PickUp"))
    }

    override fun drawDropOff(position: LatLng) {
        mapboxMap?.addMarker(MarkerOptions()
                .position(position)
                .title("DropOff"))
    }

    override fun showNavigateButton() {
        navigateContainer.visibility = View.VISIBLE
    }

}
