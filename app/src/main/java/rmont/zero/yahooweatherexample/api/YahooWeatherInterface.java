package rmont.zero.yahooweatherexample.api;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by rmontgomery on 4/10/17.
 */

public interface YahooWeatherInterface {

    @GET("v1/public/yql")
    Call<JsonElement> getWeatherForecast(@QueryMap Map<String, String> params);

}
