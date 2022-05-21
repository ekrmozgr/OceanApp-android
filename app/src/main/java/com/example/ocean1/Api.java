package com.example.ocean1;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Api {
    public static String url = "http://10.0.2.2:7157";
    public static String baskets = url + "/api/baskets";
    public static String comments = url + "/api/comments";
    public static String coupons = url + "/api/coupons";
    public static String favourites = url + "/api/favourites";
    public static String infos = url + "/api/infos";
    public static String login = url + "/api/login";
    public static String options = url + "/api/options";
    public static String orders = url + "/api/orders";
    public static String products = url + "/api/products";
    public static String users = url + "/api/users";
    public static User user;

    public static void getCategories(HashMap<String,Integer> hashMap, Context context, final VolleyCallBack callBack)
    {
        String _url = options + "/categories";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for(int i = 0; i < response.length(); i++)
                            {
                                try {
                                JSONObject obj = response.getJSONObject(i);
                                hashMap.put(obj.getString("optionName").trim().toLowerCase(),obj.getInt("optionId"));
                                } catch (JSONException e) {
                                    Toast.makeText(context, "Reading Data Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(context, "Error Codee : " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
