package com.example.ocean1;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class User {
    String token;
    int id;
    String role;
    String email;
    String name;
    ArrayList<Integer> whishlistProducts;
    ArrayList<Integer> basketProducts;

    void login(String _email, String password,Context context,final VolleyCallBack callBack) throws JSONException {
        String url = Api.login;
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", _email);
        jsonBody.put("password", password);

        StringRequest request = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                token = response;
                String[] split_string = response.split("\\.");
                String base64EncodedBody = split_string[1];

                String body = new String(android.util.Base64.decode(base64EncodedBody,android.util.Base64.DEFAULT));
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    id = Integer.parseInt(jsonObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier"));
                    role = jsonObject.getString("http://schemas.microsoft.com/ws/2008/06/identity/claims/role");
                    email = jsonObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");
                    name = jsonObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
                    whishlistProducts = new ArrayList<Integer>();
                    basketProducts = new ArrayList<Integer>();
                    getWhishlist(context,callBack);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public byte[] getBody() {
                return jsonBody.toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    void register(String name, String surname, String email, String phone, String password, Context context,final VolleyCallBack callBack) throws JSONException {
        String url = Api.users;
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name",name);
        jsonBody.put("surname",surname);
        jsonBody.put("mobilePhone",phone);
        jsonBody.put("email",email);
        jsonBody.put("password",password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    login(email,password,context,callBack);
                } catch (JSONException e) {
                    Toast.makeText(context, "Reading Data Error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(context, "Error Code : " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    void getWhishlist(Context context, final VolleyCallBack callBack)
    {
        String _url = Api.favourites + "/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray productobjarr = response.getJSONArray("favouritesProducts");
                            for(int i = 0; i < productobjarr.length(); i++)
                            {
                                JSONObject productobj = productobjarr.getJSONObject(i);

                                whishlistProducts.add(productobj.getInt("productId"));
                            }
                            getBasket(context,callBack);
                        } catch (JSONException e) {
                            Toast.makeText(context, "Reading Data Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(context, "Error Code : " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    void getBasket(Context context, final VolleyCallBack callBack)
    {
        String _url = Api.baskets + "/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray productobjarr = response.getJSONArray("basketProducts");
                            for(int i = 0; i < productobjarr.length(); i++)
                            {
                                JSONObject productobj = productobjarr.getJSONObject(i);

                                basketProducts.add(productobj.getInt("productId"));
                            }
                            callBack.onSuccess();
                        } catch (JSONException e) {
                            Toast.makeText(context, "Reading Data Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(context, "Error Code : " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

}
