package info.miguelcatalan.takemethere.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;

public class DrawableUtils {

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawable) {
        Drawable vectorDrawable;
        try {
            vectorDrawable = AppCompatResources.getDrawable(context, drawable);
        } catch (Resources.NotFoundException ex) {
            vectorDrawable = ContextCompat.getDrawable(context, drawable);
        }
        return vectorDrawable != null ? vectorDrawable : null;
    }
}
