package com.example.ocean1;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class User {
    String token;
    int id;
    String role;

    void login(String email, String password,Context context,final VolleyCallBack callBack) throws JSONException {
        String url = Api.login;
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", password);

        StringRequest request = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                token = response;
                String[] split_string = response.split("\\.");
                String base64EncodedBody = split_string[1];

                Base64.Decoder decoder = Base64.getUrlDecoder();
                String body = new String(decoder.decode(base64EncodedBody));
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    id = Integer.parseInt(jsonObject.getString("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier"));
                    role = jsonObject.getString(("http://schemas.microsoft.com/ws/2008/06/identity/claims/role"));
                    callBack.onSuccess();

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
}
