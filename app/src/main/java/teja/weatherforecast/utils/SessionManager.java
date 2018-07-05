package teja.weatherforecast.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import teja.weatherforecast.textview.WeatherPojo;

/**
 * Created by Teja on 07/4/2018.
 */

public class SessionManager {

    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "srt";
    private static final String KEY_DYNAMIC_CURRENCY = "currency";

    private static final String KEY_LIST_TODAY = "listoday";
    private static final String KEY_LIST_TOMORROW = "listomorrow";
    private static final String KEY_LIST_LATER = "listlater";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void putlistToday(ArrayList<WeatherPojo> list) {
        String aScheduleTImeJson = null;
        Gson aGson = new Gson();
        aScheduleTImeJson = aGson.toJson(list);
        editor.putString(KEY_LIST_TODAY, aScheduleTImeJson);
        editor.commit();
    }

    public ArrayList<WeatherPojo> getListToday() {
        ArrayList<WeatherPojo> mySelectedInfoList = null;
        String aScheduleSelectedInfoJSON = pref.getString(KEY_LIST_TODAY, null);
        if (aScheduleSelectedInfoJSON != null) {
            mySelectedInfoList = new Gson().fromJson(aScheduleSelectedInfoJSON, new TypeToken<ArrayList<WeatherPojo>>() {
            }.getType());
        }
        return mySelectedInfoList;
    }

    public void putlistTomorrow(ArrayList<WeatherPojo> list) {
        String aScheduleTImeJson = null;
        Gson aGson = new Gson();
        aScheduleTImeJson = aGson.toJson(list);
        editor.putString(KEY_LIST_TOMORROW, aScheduleTImeJson);
        editor.commit();
    }

    public ArrayList<WeatherPojo> getlistTomorrow() {
        ArrayList<WeatherPojo> mySelectedInfoList = null;
        String aScheduleSelectedInfoJSON = pref.getString(KEY_LIST_TOMORROW, null);
        if (aScheduleSelectedInfoJSON != null) {
            mySelectedInfoList = new Gson().fromJson(aScheduleSelectedInfoJSON, new TypeToken<ArrayList<WeatherPojo>>() {
            }.getType());
        }
        return mySelectedInfoList;
    }

    public void putListLater(ArrayList<WeatherPojo> list) {
        String aScheduleTImeJson = null;
        Gson aGson = new Gson();
        aScheduleTImeJson = aGson.toJson(list);
        editor.putString(KEY_LIST_LATER, aScheduleTImeJson);
        editor.commit();
    }

    public ArrayList<WeatherPojo> getlistLater() {
        ArrayList<WeatherPojo> mySelectedInfoList = null;
        String aScheduleSelectedInfoJSON = pref.getString(KEY_LIST_LATER, null);
        if (aScheduleSelectedInfoJSON != null) {
            mySelectedInfoList = new Gson().fromJson(aScheduleSelectedInfoJSON, new TypeToken<ArrayList<WeatherPojo>>() {
            }.getType());
        }
        return mySelectedInfoList;
    }

    //----currency-----
    public void setKeyDynamicCurrency(String status) {
        editor.putString(KEY_DYNAMIC_CURRENCY, status);
        editor.commit();
    }

    public HashMap<String, String> getKeyDynamicCurrency() {
        HashMap<String, String> service = new HashMap<String, String>();
        service.put(KEY_DYNAMIC_CURRENCY, pref.getString(KEY_DYNAMIC_CURRENCY, ""));
        return service;
    }

}
