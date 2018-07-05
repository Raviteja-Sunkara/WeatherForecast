package teja.weatherforecast.textview;

import java.util.ArrayList;

/**
 * Created by Teja on 7/4/2018.
 */

public class WeatherPojo {
    private ArrayList<WeatherPojo> listToday=null;
    private ArrayList<WeatherPojo> listTomorrow=null;
    private ArrayList<WeatherPojo> listLater=null;
    private String temperature,min_temperature,max_temperature,windspeed,winddegree, pressure, humidity,weatherDesc,weatherIcon,date,cloud;

    public void setListToday(ArrayList<WeatherPojo> addonPojoinfo) {
        listToday = addonPojoinfo;
    }

    public ArrayList<WeatherPojo> getListToday() {
        return listToday;
    }

    public ArrayList<WeatherPojo> getListTomorrow() {
        return listTomorrow;
    }

    public void setListTomorrow(ArrayList<WeatherPojo> listTomorrow) {
        this.listTomorrow = listTomorrow;
    }

    public ArrayList<WeatherPojo> getListLater() {
        return listLater;
    }

    public void setListLater(ArrayList<WeatherPojo> listLater) {
        this.listLater = listLater;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMin_temperature() {
        return min_temperature;
    }

    public void setMin_temperature(String min_temperature) {
        this.min_temperature = min_temperature;
    }

    public String getMax_temperature() {
        return max_temperature;
    }

    public void setMax_temperature(String max_temperature) {
        this.max_temperature = max_temperature;
    }


    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddegree() {
        return winddegree;
    }

    public void setWinddegree(String winddegree) {
        this.winddegree = winddegree;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
