package com.example.jixiang.clinicapp.Models;

import android.util.Log;

import com.example.jixiang.clinicapp.R;
import com.example.jixiang.clinicapp.utils.JSONParser;
import com.example.jixiang.clinicapp.utils.JSONUploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jixiang on 13/8/15.
 */
public class RouteItem extends HashMap<String, String> {
    /**
     * Constructs a new empty {@code HashMap} instance.
     */

    private String id;
    private String address1;
    private String aviva_code;
    private String estate;



    public RouteItem(String id,String address_1, String address_2, String clinic){


        this.id = id;
        this.address1 = address_1;
        this.aviva_code = address_2;
        this.estate = clinic;


        put("id",id);
        put("address1",address1);
        put("aviva_code",aviva_code);
        put("estate",estate);



    }


    public RouteItem(String address1, String aviva_code, String estate){

        put("address1",address1);
        put("aviva_code",aviva_code);
        put("estate",estate);

        this.address1 = address1;
        this.aviva_code = aviva_code;
        this.estate = estate;

    }

    public RouteItem(String address1, String address2, String aviva_code,
                     String estate, String fax, String id, String name,
                     String postal, String public_holiday, String remarks,
                     String saturday, String sunday, String telephone,
                     String weekday, String zone) {

        put("address1",address1);
        put("address2",address2);
        put("aviva_code",aviva_code);
        put("estate",estate);
        put("fax",fax);
        put("id",id);
        put("name",name);
        put("postal",postal);
        put("public_holiday",public_holiday);
        put("remarks",remarks);
        put("saturday",saturday);
        put("sunday",sunday);
        put("telephone",telephone);
        put("weekday",weekday);
        put("zone",zone);
    }

    public static List<RouteItem> jobjread(String url) {
        List<RouteItem> list = new ArrayList<RouteItem>();
        JSONObject a = JSONParser.getJSONFromUrl(url);
            try {
                list.add(new RouteItem(a.getString("address1"),a.getString("address2"),a.getString("name")));
                Log.i("fetching",a.getString("address1")+a.getString("address2")+a.getString("name"));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        return(list);
    }

    public static String jobjInsert(RouteItem item){

        List<RouteItem> list = new ArrayList<RouteItem>();
        //JSONObject a = JSONParser.getJSONFromUrl("http://10.0.2.2:5000/queryById/1");
        String url = "";
        JSONUploader b = new JSONUploader();

        JSONObject a = b.initJson(item);

        String sendBack = b.sendJsonToServer(url,a);

        try {
            list.add(new RouteItem(a.getString("address1"),a.getString("aviva_code"),a.getString("estate")));
            Log.i("fetching",a.getString("address1")+a.getString("aviva_code")+a.getString("estate"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return sendBack;
    }

    public static List<RouteItem> jread(String url) {
        List<RouteItem> list = new ArrayList<RouteItem>();
        JSONObject a = JSONParser.getJSONFromUrl(url);
        if (a == null){
            return list;
        }
        try {
            JSONArray jsonArray = a.getJSONArray("clinic");
            int jsonLength = jsonArray.length();
            //List<String> list= new ArrayList<String>();
            for(int i=0;i<jsonLength;i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("address_1") && jsonObject.has("address_2")&&
                        jsonObject.has("clinic")&&jsonObject.has("id")){
                    list.add(new RouteItem(jsonObject.getString("id"),jsonObject.getString("address_1"),
                            jsonObject.getString("address_2"), jsonObject.getString("clinic")));
                    //clinic_id = jsonObject.getString("id");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return(list);
    }




    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getAviva_code() {
        return aviva_code;
    }

    public void setAviva_code(String aviva_code) {
        this.aviva_code = aviva_code;
    }


    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }
}
