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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product {
    int productId;
    String name;
    double price;
    int discountPercent;
    double discountPrice;
    String explanation;
    String courseHourDuration;
    String courseMinuteDuration;
    int productCategoryId;
    String categoryName;
    int courseLevelId;
    String courseLevel;
    byte[] image;
    int instructorId;
    String instructorName;
    String instructorSurname;
    String instructorMail;
    String companyWebsite;
    String companyName;
    boolean isAvailable;
    int productQuantity;

    public static void getCategoryProducts(int categoryId, List<Product> products, Context context, final VolleyCallBack callBack)
    {
        String _url = Api.products + "/categories/" + categoryId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Product product = new Product();

                                product.companyName = obj.getString("companyName");
                                product.companyWebsite = obj.getString("companyWebsite");
                                product.categoryName = obj.getString("categoryName");
                                product.courseHourDuration = obj.getString("courseHourDuration");
                                product.courseLevel = obj.getString("courseLevel");
                                product.productId = obj.getInt("productId");
                                product.courseLevelId = obj.getInt("courseLevelId");
                                product.courseMinuteDuration = obj.getString("courseMinuteDuration");
                                product.discountPercent = obj.getInt("discountPercent");
                                product.discountPrice = obj.getDouble("discountPrice");
                                product.explanation = obj.getString("explanation");
                                product.productCategoryId = obj.getInt("productCategoryId");
                                product.name = obj.getString("name");
                                product.price = obj.getDouble("price");
                                product.image = android.util.Base64.decode(obj.getString("base64Image"),android.util.Base64.DEFAULT);
                                product.instructorId = obj.getInt("userId");
                                product.isAvailable = obj.getBoolean("isAvailable");
                                JSONObject instructor = obj.getJSONObject("user");

                                product.instructorName = instructor.getString("name");
                                product.instructorSurname = instructor.getString("surname");
                                product.instructorMail = instructor.getString("email");

                                products.add(product);

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
                headerMap.put("Authorization", "Bearer " + Api.user.token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }


    public static void getFavouritesProducts(List<Product> _products, Context context, final VolleyCallBack callBack)
    {
        String _url = Api.favourites + "/" + Api.user.id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray products = response.getJSONArray("favouritesProducts");
                                for(int i = 0; i < products.length(); i++)
                                {
                                    JSONObject obj = products.getJSONObject(i).getJSONObject("product");
                                    Product product = new Product();

                                    product.companyName = obj.getString("companyName");
                                    product.companyWebsite = obj.getString("companyWebsite");
                                    product.categoryName = obj.getString("categoryName");
                                    product.courseHourDuration = obj.getString("courseHourDuration");
                                    product.courseLevel = obj.getString("courseLevel");
                                    product.productId = obj.getInt("productId");
                                    product.courseLevelId = obj.getInt("courseLevelId");
                                    product.courseMinuteDuration = obj.getString("courseMinuteDuration");
                                    product.discountPercent = obj.getInt("discountPercent");
                                    product.discountPrice = obj.getDouble("discountPrice");
                                    product.explanation = obj.getString("explanation");
                                    product.productCategoryId = obj.getInt("productCategoryId");
                                    product.name = obj.getString("name");
                                    product.price = obj.getDouble("price");
                                    product.image = android.util.Base64.decode(obj.getString("base64Image"),android.util.Base64.DEFAULT);
                                    product.instructorId = obj.getInt("userId");
                                    product.isAvailable = obj.getBoolean("isAvailable");

                                    JSONObject instructor = obj.getJSONObject("user");

                                    product.instructorName = instructor.getString("name");
                                    product.instructorSurname = instructor.getString("surname");
                                    product.instructorMail = instructor.getString("email");

                                    _products.add(product);
                                }

                            } catch (JSONException e) {
                                Toast.makeText(context, "Reading Data Error", Toast.LENGTH_LONG).show();
                            }
                        callBack.onSuccess();
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
                headerMap.put("Authorization", "Bearer " + Api.user.token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
