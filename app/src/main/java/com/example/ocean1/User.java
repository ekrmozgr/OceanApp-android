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

    void initializeUser(String email, String password,Context context,final VolleyCallBack callBack) throws JSONException {
        String url = Api.url + "/api/login";


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
                    id = Integer.parseInt(jsonObject.getString("id"));
                    role = jsonObject.getString(("role"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess();
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
}
