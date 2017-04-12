package rmont.zero.yahooweatherexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import rmont.zero.yahooweatherexample.utils.WeatherUtils;

public class ForecastDetailActivity extends AppCompatActivity {

    TextView firstLine = null;
    TextView secondLine = null;
    ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_detail);

        firstLine = (TextView) findViewById(R.id.firstLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        imageView = (ImageView) findViewById(R.id.icon);

        String day = getIntent().getStringExtra("FORECAST_DAY");
        String high = getIntent().getStringExtra("FORECAST_HIGH");
        String low = getIntent().getStringExtra("FORECAST_LOW");
        String text = getIntent().getStringExtra("FORECAST_TEXT");

        firstLine.setText("  " + day + "  -   High: " + high + "  Low:" + low);
        secondLine.setText(text);
        imageView.setImageResource(WeatherUtils.getImageIdForType(text));

    }
}
