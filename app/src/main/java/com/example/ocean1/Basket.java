package com.example.ocean1;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    double price;
    int productCount;
    ArrayList<Product> products;

    void getBasketProducts(Basket basket,String token, int id, Context context, final VolleyCallBack callBack)
    {
        String _url = Api.baskets + "/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        products = new ArrayList<>();
                        try {
                            basket.price = response.getDouble("price");
                            basket.productCount = response.getInt("productCount");
                            JSONArray productobjarr = response.getJSONArray("basketProducts");
                            for(int i = 0; i < productobjarr.length(); i++)
                            {
                                JSONObject basketproductobj = productobjarr.getJSONObject(i);
                                Product product = new Product();
                                product.productQuantity = basketproductobj.getInt("productQuantity");
                                JSONObject productobj = basketproductobj.getJSONObject("product");

                                product.companyName = productobj.getString("companyName");
                                product.companyWebsite = productobj.getString("companyWebsite");
                                product.categoryName = productobj.getString("categoryName");
                                product.courseHourDuration = productobj.getString("courseHourDuration");
                                product.courseLevel = productobj.getString("courseLevel");
                                product.productId = productobj.getInt("productId");
                                product.courseLevelId = productobj.getInt("courseLevelId");
                                product.courseMinuteDuration = productobj.getString("courseMinuteDuration");
                                product.discountPercent = productobj.getInt("discountPercent");
                                product.discountPrice = productobj.getDouble("discountPrice");
                                product.explanation = productobj.getString("explanation");
                                product.productCategoryId = productobj.getInt("productCategoryId");
                                product.name = productobj.getString("name");
                                product.price = productobj.getDouble("price");
                                product.image = android.util.Base64.decode(productobj.getString("base64Image"),android.util.Base64.DEFAULT);
                                product.instructorId = productobj.getInt("userId");
                                product.isAvailable = productobj.getBoolean("isAvailable");

                                JSONObject instructor = productobj.getJSONObject("user");

                                product.instructorName = instructor.getString("name");
                                product.instructorSurname = instructor.getString("surname");
                                product.instructorMail = instructor.getString("email");

                                basket.products.add(product);
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

    public static void purchaseBasket(int basketId, String receiver, Context context, final VolleyCallBack callBack)
    {
        String _url = Api.baskets + "/purchase";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("basketId",basketId);
            jsonBody.put("email",receiver);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, _url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
