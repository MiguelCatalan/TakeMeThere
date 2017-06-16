package info.miguelcatalan.takemethere.navigation.incications

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import info.miguelcatalan.takemethere.R
import info.miguelcatalan.takemethere.utils.DrawableUtils
import kotlinx.android.synthetic.main.view_indications.view.*


class IndicationsView : FrameLayout {

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
                R.layout.view_indications,
                this,
                true)
    }

    fun setManeuverTurn(maneuver: String) {
        maneuverRoad.text = maneuver
    }

    fun setManeuverDistance(displayText: String) {
        val span = SpannableString(displayText)
        var unitStart = displayText.indexOf(" ")
        if (unitStart < 1) {
            unitStart = displayText.length
        }
        val unitEnd = displayText.length

        //span.setSpan(StyleSpan(Typeface.BOLD), 0, unitStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(RelativeSizeSpan(0.8f), unitStart, unitEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        maneuverDistance.text = span
    }

    fun setManeuverIndicator(maneuver: Int) {
        maneuverIndicator.setImageDrawable(DrawableUtils.getDrawable(context, maneuver))
    }
}