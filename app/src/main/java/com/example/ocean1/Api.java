package com.example.ocean1;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public static void getCourseLevels(HashMap<String,Integer> hashMap, Context context, final VolleyCallBack callBack)
    {
        String _url = options + "/courselevels";
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

    public static void getCompany(HashMap<String,String> hashMap,Context context, final VolleyCallBack callBack)
    {
        String _url = infos + "/company";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, _url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    hashMap.put("companyName",response.getString("companyName"));
                    hashMap.put("email", response.getString("email"));
                    hashMap.put("address",response.getString("address"));
                    hashMap.put("phoneNo",response.getString("phoneNo"));
                    callBack.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void forgottenPw(String email, Context context, final VolleyCallBack callBack)
    {
        String _url = login + "/forgottenpw";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, _url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void postComment(ArrayList<Comments> commentList, String comment, int productId, Context context, final VolleyCallBack callBack)
    {
        String _url = comments;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("comment", comment);
            jsonBody.put("userId",Api.user.id);
            jsonBody.put("productId",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, _url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comments currentComment = new Comments();
                try {
                    currentComment.id = response.getInt("id");
                    currentComment.dateOfComment = response.getString("dateOfComment");
                    currentComment.comment = response.getString("comment");
                    JSONObject commentuserobj = response.getJSONObject("user");
                    currentComment.userName = commentuserobj.getString("name");
                    currentComment.userSurname = commentuserobj.getString("surname");
                    commentList.add(currentComment);
                    callBack.onSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + Api.user.token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public static void addProduct(JSONObject jsonBody, Context context, VolleyCallBack callBack)
    {
        String _url = products;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, _url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Authorization", "Bearer " + Api.user.token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


}
