package info.miguelcatalan.takemethere.utils;

import android.support.annotation.DrawableRes;

import info.miguelcatalan.takemethere.R;

public class ManeuverIndicator {

    @DrawableRes
    public static int getManeuverIndicator(String type, String modifier) {

        int maneuver = R.drawable.direction_invalid;

        type = type.replace(" ", "_");
        modifier = modifier.replace(" ", "_");

        try {
            maneuver = R.drawable.class.getField("direction_" + type + "_" + modifier).getInt(null);

        } catch (Exception e) {
            maneuver = R.drawable.direction_invalid;
        }

        return maneuver;
    }

}
