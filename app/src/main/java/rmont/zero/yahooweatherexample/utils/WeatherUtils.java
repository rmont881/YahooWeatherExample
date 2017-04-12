package rmont.zero.yahooweatherexample.utils;

import rmont.zero.yahooweatherexample.R;

/**
 * Created by rmontgomery on 4/11/17.
 */

public class WeatherUtils {

    public static int getImageIdForType(String s) {
        if (s.equals("Sunny")) {
            return R.mipmap.sunny;
        } else if(s.equals("Cloudy")) {
            return R.mipmap.cloudy;
        } else if(s.equals("Mostly Sunny")) {
            return R.mipmap.partly_cloudy;
        } else if(s.equals("Partly Cloudy")) {
            return R.mipmap.cloudy;
        } else if(s.equals("Mostly Cloudy")) {
            return R.mipmap.overcast;
        } else if(s.equals("Breezy")) {
            return R.mipmap.windy;
        } else {
            //Something not accounted for
            return R.mipmap.sunny;
        }
    }
}
