package rmont.zero.yahooweatherexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rmont.zero.yahooweatherexample.models.Forecast;
import rmont.zero.yahooweatherexample.utils.WeatherUtils;
import rmont.zero.yahooweatherexample.R;

/**
 * Created by rmontgomery on 4/10/17.
 */

public class WeatherItemArrayAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Forecast> values;

    public WeatherItemArrayAdapter(Context context, ArrayList<Forecast> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        //not used
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.weather_item, parent, false);
        TextView firstLine = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Forecast forecast = values.get(position);
        firstLine.setText("  " +forecast.getDay() + "  -   High: " + forecast.getHigh() + "  Low:" + forecast.getLow());
        secondLine.setText(values.get(position).getText());
        imageView.setImageResource(WeatherUtils.getImageIdForType(forecast.getText()));

        return rowView;
    }

    public ArrayList<Forecast> getData() {
        return values;
    }
}
