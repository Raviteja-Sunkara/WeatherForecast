package teja.weatherforecast.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import teja.weatherforecast.R;
import teja.weatherforecast.ServiceConstant.ServiceConstant;
import teja.weatherforecast.fragment.FragmentWeatherLater;
import teja.weatherforecast.fragment.FragmentWeatherToday;
import teja.weatherforecast.fragment.FragmentWeatherTomorrow;
import teja.weatherforecast.textview.WeatherPojo;
import teja.weatherforecast.utils.ConnectionDetector;
import teja.weatherforecast.utils.ProgressLoading;
import teja.weatherforecast.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private String strCityName="",strCountryName="",strCityTemperature="",strTime="",str_dt_txt="",str_dt_txtTemp="",strDate="",strDate2="",strDay="";
    private TextView Tv_city_name,Tv_date,Tv_city_temp,Tv_city_weather_desc;
    private DateFormat dateFormat, dayFormat;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private ArrayList<WeatherPojo> arrayListToday;
    private ArrayList<WeatherPojo> arrayListTomorrow;
    private ArrayList<WeatherPojo> arrayListLater;
    private SessionManager session;
    private LinearLayout top_layout;
    private ProgressLoading myDialog;
    private ImageView weather_imgView;
    private Boolean isInternetPresent = false;
    private ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        if (isInternetPresent)
        {
            Weather_API();
        }else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.internet_toast),Toast.LENGTH_LONG).show();
        }

        top_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tv_wind,tv_pressure,tv_humidity;
                RelativeLayout close;
                final MaterialDialog dialog=new MaterialDialog(MainActivity.this);
                View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_dialog,null);
                dialog.setCanceledOnTouchOutside(true);
                close=(RelativeLayout) view.findViewById(R.id.dialog_close_lay);
                tv_wind = (TextView) view.findViewById(R.id.wind_value);
                tv_pressure = (TextView) view.findViewById(R.id.pressure_value);
                tv_humidity = (TextView) view.findViewById(R.id.humidity_value);
                //---setting current date Values
                tv_wind.setText(arrayListToday.get(0).getWindspeed()+" "+getResources().getString(R.string.wind_unit));
                tv_pressure.setText(arrayListToday.get(0).getPressure()+" "+getResources().getString(R.string.pressure_unit));
                tv_humidity.setText(arrayListToday.get(0).getHumidity()+" "+getResources().getString(R.string.humidity_unit));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setView(view).show();
            }
        });

    }

    private void initialize() {
        session=new SessionManager(MainActivity.this);
        myDialog = new ProgressLoading(MainActivity.this);
        cd = new ConnectionDetector(MainActivity.this);
        isInternetPresent = cd.isConnectingToInternet();
        Tv_city_name=(TextView) findViewById(R.id.Tv_city_name);
        Tv_date=(TextView)findViewById(R.id.Tv_date);
        Tv_city_temp=(TextView)findViewById(R.id.Tv_city_temp);
        Tv_city_weather_desc=(TextView)findViewById(R.id.Tv_weather_desc);
        top_layout= (LinearLayout) findViewById(R.id.top_layout);
        myViewPager = (ViewPager) findViewById(R.id.viewpager);
        myTabLayout = (TabLayout) findViewById(R.id.tabs);
        weather_imgView=(ImageView) findViewById(R.id.weather_imgView);
        arrayListToday=new ArrayList<WeatherPojo>();
        arrayListTomorrow=new ArrayList<WeatherPojo>();
        arrayListLater=new ArrayList<WeatherPojo>();
        dateFormat = DateFormat.getDateTimeInstance();
        dayFormat=new SimpleDateFormat("EEEE");

        /*---show progress Dialog---*/
        if (!myDialog.isShowing()) {
            myDialog.show();
        }
    }

    private void changeTabsFont() {
        Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Lato-Regular.ttf");
        ViewGroup vg = (ViewGroup) myTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }
    }

    private void setupViewPager(ViewPager aViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentWeatherToday(), getResources().getString(R.string.viewpager_today_label));
        adapter.addFragment(new FragmentWeatherTomorrow(), getResources().getString(R.string.viewpager_tomorrow_label));
        adapter.addFragment(new FragmentWeatherLater(), getResources().getString(R.string.viewpager_later_label));
        aViewPager.setAdapter(adapter);
        aViewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
        myTabLayout.setupWithViewPager(myViewPager);

        changeTabsFont();
    }

    private void tabListener() {
        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());
                myTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                myTabLayout.setBackgroundColor(getResources().getColor(R.color.tab_background));
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                myTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        /*---close progress Dialog---*/
        if (myDialog.isShowing()) {
            myDialog.dismiss();
        }

        myTabLayout.setVisibility(View.VISIBLE);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> myFragmentList = new ArrayList<>();
        private final List<String> myFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return myFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return myFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            myFragmentList.add(fragment);
            myFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myFragmentTitleList.get(position);
        }
    }

    private void Weather_API() {

        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET, ServiceConstant.WeatherAPI, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    /*---Fetch city details---*/
                    if (response.has("city")) {
                        JSONObject city_object = response.getJSONObject("city");
                        if (city_object.length() > 0) {

                            strCityName=city_object.getString("name");
                            strCountryName=city_object.getString("country");
                            Tv_city_name.setText(strCityName + ", " + strCountryName);
                        }
                    }

                    /*-----fetch temp days list--*/
                    Object obj=response.get("list");
                    if (obj instanceof JSONArray) {
                        JSONArray listArray = response.getJSONArray("list");
                        if (listArray.length() > 0) {

                            arrayListToday.clear();
                            arrayListTomorrow.clear();
                            arrayListLater.clear();

                            for (int i=0;i<listArray.length();i++){

                                JSONObject listObject=listArray.getJSONObject(i);
                                WeatherPojo pojo=new WeatherPojo();

                                 //---for today's data
                                if (i==0){
                                    strTime = dateFormat.format(new Date(listObject.getLong("dt")*1000));
                                    strDay=dayFormat.format(new Date(listObject.getLong("dt")*1000));

                                    Tv_date.setText(strTime);
                                    Tv_city_weather_desc.setText(listObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                                    strDate= String.valueOf(strTime.charAt(0));

                                    JSONObject main_object2=listObject.getJSONObject("main");
                                    if (main_object2.length()>0){
                                        //---temperature roundoff
                                        float temperature = (Float.parseFloat(main_object2.getString("temp")));
                                        String temp= String.format("%.0f", temperature);
                                        Tv_city_temp.setText(temp+"°c");
                                    }

                                    setWeatherIcon(listObject.getJSONArray("weather").getJSONObject(0).getString("icon"));
                                }
                                /*----Weather Description--*/
                                pojo.setWeatherDesc((listObject.getJSONArray("weather").getJSONObject(0).getString("description")));

                                strTime = dateFormat.format(new Date(listObject.getLong("dt")*1000));
                                pojo.setDate(strTime);

                                JSONObject main_object=listObject.getJSONObject("main");
                                if (main_object.length()>0){

                                   /*---temperature roundoff---*/
                                    float temperature = (Float.parseFloat(main_object.getString("temp")));
                                    String temp= String.format("%.0f", temperature);

                                    pojo.setTemperature(temp+"°c");
                                    pojo.setMin_temperature(main_object.getString("temp_min")+"°c");
                                    pojo.setMax_temperature(main_object.getString("temp_max")+"°c");
                                    pojo.setPressure(main_object.getString("pressure"));
                                    pojo.setHumidity(main_object.getString("humidity"));
                                }

                                JSONObject wind_object=listObject.getJSONObject("wind");
                                if (wind_object.length()>0){
                                    pojo.setWindspeed(wind_object.getString("speed"));
                                    pojo.setWinddegree(wind_object.getString("deg"));
                                }

                                JSONObject cloud_object=listObject.getJSONObject("clouds");
                                if (cloud_object.length()>0){
                                    pojo.setCloud(cloud_object.getString("all"));
                                }

                                pojo.setWeatherIcon(listObject.getJSONArray("weather").getJSONObject(0).getString("icon"));

                                //------------get date wise date--------------
                                Calendar today = Calendar.getInstance();
                                int dat=today.get(Calendar.DAY_OF_YEAR);
                                final String dateMsString = listObject.getString("dt") + "000";
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(Long.parseLong(dateMsString));
                                int getdate=cal.get(Calendar.DAY_OF_YEAR);

                                if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                                    arrayListToday.add(pojo);
                                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
                                    arrayListTomorrow.add(pojo);
                                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 2) {
                                    arrayListLater.add(pojo);
                                } else {
                                    /*---all other next days will be here----*/
                                }
                            }

                            /*---save weather date based on date----*/
                            session.putlistToday(arrayListToday);
                            session.putlistTomorrow(arrayListTomorrow);
                            session.putListLater(arrayListLater);
                        }

                        /*--load viewpager after loading data---*/
                        setupViewPager(myViewPager);
                        tabListener();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }
            }
        });

        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(jor);
    }

    private void setWeatherIcon(String icon){
        String URL=ServiceConstant.WeatherIconAPI+icon+".png";
        Picasso.with(MainActivity.this)
                .load(URL)
                .into(weather_imgView);
    }

}
