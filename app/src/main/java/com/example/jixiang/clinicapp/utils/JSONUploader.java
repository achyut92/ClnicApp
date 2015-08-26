package com.example.jixiang.clinicapp.utils;

import com.example.jixiang.clinicapp.Models.RouteItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jixiang on 20/8/15.
 */
public class JSONUploader {


    public String sendJsonToServer(String url,JSONObject json) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String result = "";
        try {

            HttpPost httpPost = new HttpPost(url);
            HttpParams httpParams = new BasicHttpParams();
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            String s = json.toString();
            HttpEntity entity = new StringEntity(s);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);

            httpPost.setParams(httpParams);


            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null && statusLine.getStatusCode() == 200) {
                HttpEntity entity2 = response.getEntity();
                if (entity2 != null) {
                    result =  EntityUtils.toString(response.getEntity());

                } else {
                   result = "no relative data";
                }
            } else {
                result = "send data failed";
            }

            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /*
     * 填充数据源
     */
    public JSONObject initJson(RouteItem item) {
        JSONObject obj = new JSONObject();

        try {
            obj.put("address1",item.getAddress1());
            obj.put("aviva_code",item.getAviva_code());
            obj.put("estate",item.getEstate());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }
}
