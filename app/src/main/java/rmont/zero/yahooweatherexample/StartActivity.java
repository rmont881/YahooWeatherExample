package rmont.zero.yahooweatherexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rmont.zero.yahooweatherexample.adapters.WeatherItemArrayAdapter;
import rmont.zero.yahooweatherexample.api.YahooWeatherInterface;
import rmont.zero.yahooweatherexample.models.Condition;
import rmont.zero.yahooweatherexample.models.Forecast;
import rmont.zero.yahooweatherexample.models.Weather;

public class StartActivity extends Activity {

    TextView titleText = null;
    ListView listView = null;

    ImageView weatherImage = null;
    TextView weatherHeading = null;
    TextView weatherDetail = null;

    private ArrayList<Forecast> forecastData = new ArrayList<>();
    private WeatherItemArrayAdapter forecastDataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_forecast);

        listView = (ListView)findViewById(R.id.forecastList);

        weatherImage = (ImageView)findViewById(R.id.weatherImage);
        weatherHeading = (TextView)findViewById(R.id.weatherHeading);
        weatherDetail = (TextView)findViewById(R.id.weatherDetail);

        titleText = (TextView)findViewById(R.id.weatherTitle);
        titleText.setText("Weather Forecast");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://query.yahooapis.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        YahooWeatherInterface apiService = retrofit.create(YahooWeatherInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("q", "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"nome, ak\")");
        params.put("format", "json");
        params.put("env", "store://datatables.org/alltableswithkeys");

        Call<JsonElement> call = apiService.getWeatherForecast(params);

        System.out.println(call.request().url());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                int statusCode = response.code();
                JsonElement user = response.body();
                Gson gson = new Gson();
                Weather weather = gson.fromJson(user, Weather.class);
                Condition condition = weather.getQuery().getResults().getChannel().getItem().getCondition();
                weatherHeading.setText("Its "+condition.getTemp()+" today and is " + condition.getText());

                //Come back and verify dates to guarantee todays forecast.
                Forecast todayForecast = weather.getQuery().getResults().getChannel().getItem().getForecast().get(0);
                weatherDetail.setText("High: " + todayForecast.getHigh() + "  -  Low: " + todayForecast.getLow());

                forecastData = (ArrayList<Forecast>)weather.getQuery().getResults().getChannel().getItem().getForecast();

                forecastDataAdapter.getData().clear();
                forecastDataAdapter.getData().addAll(forecastData);
                forecastDataAdapter.notifyDataSetChanged();

                weather.getQuery().getCount();

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                // Log error here since request failed
                System.out.println("Something went wrong");
            }
        });

        setListClickListener();

        forecastDataAdapter = new WeatherItemArrayAdapter(this, forecastData);
        listView.setAdapter(forecastDataAdapter);
    }


    private void setListClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parentView, View childView,
                                       int position, long id) {
                Intent intent = new Intent(getBaseContext(), ForecastDetailActivity.class);
                //Quick n dirty. This should be architected out completely different.
                intent.putExtra("FORECAST_DAY", forecastData.get(position).getDay());
                intent.putExtra("FORECAST_HIGH", forecastData.get(position).getHigh());
                intent.putExtra("FORECAST_LOW", forecastData.get(position).getLow());
                intent.putExtra("FORECAST_TEXT", forecastData.get(position).getText());
                startActivity(intent);
            }

            public void onNothingSelected(AdapterView parentView) {

            }
        });
    }
}
