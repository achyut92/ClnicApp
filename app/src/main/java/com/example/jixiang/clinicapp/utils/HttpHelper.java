package com.example.jixiang.clinicapp.utils;

import android.widget.Toast;

import com.example.jixiang.clinicapp.Models.RouteItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jixiang on 26/8/15.
 */
public class HttpHelper {

    public static String addData(RouteItem item, String url) {

        String result = "";
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);//"http://10.10.2.211:5000/createClinic"

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("id", "201"));
            nameValuePairs.add(new BasicNameValuePair("clinic", item.getEstate()));
            nameValuePairs.add(new BasicNameValuePair("address_1", item.getAddress1()));
            nameValuePairs.add(new BasicNameValuePair("address_2", item.getAviva_code()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            // Log.d("Create Response", response.toString());

            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null && statusLine.getStatusCode() == 200) {
                HttpEntity entity2 = response.getEntity();
                if (entity2 != null) {
                    result = EntityUtils.toString(response.getEntity());
                    if (result.contains("success")) {
                        result = "success";
                    } else {
                        result = "falied";
                    }
                } else {
                    result = "no relative data";
                }
            } else {
                result = "send data failed";
            }

            System.out.println(result);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return result;
    }


    public static String deleteData(String id, String url) {

        String result = "";
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);//"http://10.10.2.211:5000/createClinic"

        String clinic_id = id;

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("id", id));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            // Log.d("Create Response", response.toString());

            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null && statusLine.getStatusCode() == 200) {
                HttpEntity entity2 = response.getEntity();
                if (entity2 != null) {
                    result = EntityUtils.toString(response.getEntity());
                    if (result.contains("success")) {
                        result = "success";
                    } else {
                        result = "falied";
                    }
                } else {
                    result = "no relative data";
                }
            } else {
                result = "send data failed";
            }

            System.out.println(result);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return result;

    }

}
