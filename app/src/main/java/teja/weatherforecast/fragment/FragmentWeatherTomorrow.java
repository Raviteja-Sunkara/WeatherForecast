package teja.weatherforecast.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import teja.weatherforecast.R;
import teja.weatherforecast.ServiceConstant.ServiceConstant;
import teja.weatherforecast.textview.WeatherPojo;
import teja.weatherforecast.utils.SessionManager;

/**
 * Created by Teja on 07/04/2017.
 */
public class FragmentWeatherTomorrow extends Fragment {

    private View myView;
    private ListView mlistview;
    private ArrayList<WeatherPojo> list = new ArrayList<WeatherPojo>();
    private SessionManager mySession;
    private FragmentAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_fragment_today, container, false);
        classAndWidgetIntialize(myView);
        return myView;
    }

    private void classAndWidgetIntialize(View aView) {
        mySession = new SessionManager(getActivity());
        mlistview = (ListView) aView.findViewById(R.id.listview_today);

        /*---get weather data---*/
        list = mySession.getlistTomorrow();
        adapter = new FragmentAdapter(getActivity(), list);
        mlistview.setAdapter(adapter);

    }

    public class FragmentAdapter extends BaseAdapter {

        private ArrayList<WeatherPojo> data;
        private LayoutInflater mInflater;
        private Activity context;

        public FragmentAdapter(Activity c, ArrayList<WeatherPojo> d) {
            context = c;
            mInflater = LayoutInflater.from(context);
            data = d;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            private TextView tv_Date, tv_weather_description, tv_Wind, tv_Pressure, tv_Humidity,tv_Temperature;
            private ImageView imgViewIcon;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = mInflater.inflate(R.layout.list_row, parent, false);
                holder = new ViewHolder();
                holder.tv_Date = (TextView) view.findViewById(R.id.itemDate);
                holder.tv_weather_description = (TextView) view.findViewById(R.id.itemDescription);
                holder.tv_Wind = (TextView) view.findViewById(R.id.itemWind);
                holder.tv_Pressure = (TextView) view.findViewById(R.id.itemPressure);
                holder.tv_Humidity = (TextView) view.findViewById(R.id.itemHumidity);
                holder.tv_Temperature = (TextView) view.findViewById(R.id.itemTemperature);
                holder.imgViewIcon=(ImageView) view.findViewById(R.id.itemIcon);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_Date.setText(data.get(position).getDate());
            holder.tv_weather_description.setText(data.get(position).getWeatherDesc());
            holder.tv_Wind.setText(getActivity().getResources().getString(R.string.wind_label)+" "+data.get(position).getWindspeed()+ " " +getActivity().getResources().getString(R.string.wind_unit));
            holder.tv_Pressure.setText(getActivity().getResources().getString(R.string.pressure_label)+" "+data.get(position).getPressure()+ " " +getActivity().getResources().getString(R.string.pressure_unit));
            holder.tv_Humidity.setText(getActivity().getResources().getString(R.string.humidity_label)+" "+data.get(position).getHumidity()+ " " +getActivity().getResources().getString(R.string.humidity_unit));
            holder.tv_Temperature.setText(data.get(position).getTemperature());
            setWeatherIcon(data.get(position).getWeatherIcon(),holder.imgViewIcon);
            return view;
        }
    }

    private void setWeatherIcon(String icon,ImageView imgview){
        try {
            String URL= ServiceConstant.WeatherIconAPI+icon+".png";

            Picasso.with(getActivity())
                    .load(URL)
                    .into(imgview);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

