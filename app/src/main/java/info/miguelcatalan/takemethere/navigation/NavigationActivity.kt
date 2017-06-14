package info.miguelcatalan.takemethere.navigation

import android.os.Bundle
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : BaseActivity<NavigationView, NavigationPresenter>(), NavigationView, OnMapReadyCallback {

    companion object {
        const val PICKUP_POSITION = "pickup_position"
        const val DROPOFF_POSITION = "dropoff_position"
    }

    private var mapboxMap: MapboxMap? = null

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
        this.mapboxMap = mapboxMap
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

    override fun getPickUpLocation(): LatLng {
        return intent.getParcelableExtra(PICKUP_POSITION)
    }

    override fun getDropOffLocation(): LatLng {
        return intent.getParcelableExtra(DROPOFF_POSITION)
    }

}
