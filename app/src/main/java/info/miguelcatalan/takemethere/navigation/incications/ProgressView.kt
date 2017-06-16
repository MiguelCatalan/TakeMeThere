package info.miguelcatalan.takemethere.navigation.incications

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import info.miguelcatalan.takemethere.R
import kotlinx.android.synthetic.main.view_progress.view.*

class ProgressView : FrameLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int) : super(context, attrs,
            defStyleAttr) {
        initViews()
    }

    private fun initViews() {
        LayoutInflater.from(context).inflate(
                R.layout.view_progress,
                this,
                true)
    }

    fun updateProgress(progress: Int) {
        progressIndicator.progress = progress
    }

    fun updateTimeRemaining(time: String) {
        timeRemaining.text = time
    }

    fun updateDistanceRemaining(distance: String) {
        distanceRemaining.text = distance
    }

    fun setEta(estimatedTime: String) {
        eta.text = estimatedTime
    }
}