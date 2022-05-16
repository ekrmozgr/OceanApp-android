package com.example.ocean1;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    public Order()
    {
        products = new ArrayList<OrderProduct>();
        coupons = new HashMap<Integer,List<Coupon>>();
    }

    int orderId;
    int userId;
    double price;
    String recipientMail;
    String dateOfOrder;
    HashMap<Integer,List<Coupon>> coupons;
    List<OrderProduct> products;

    public static void getOrders(List<Order> orders, Context context, final VolleyCallBack callBack)
    {
        TinyDB tinyDb = new TinyDB(context);
        User user = tinyDb.getObject("user",User.class);
        String _url = Api.orders + "/users/" + user.id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, _url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Order order = new Order();

                                order.userId = obj.getInt("userId");
                                order.orderId = obj.getInt("orderId");
                                order.price = obj.getDouble("price");
                                order.recipientMail = obj.getString("recipientMail");
                                order.dateOfOrder = obj.getString("dateOfOrder");

                                JSONArray _coupons = obj.getJSONArray("coupons");
                                for(int j = 0; j < _coupons.length(); j++)
                                {
                                    JSONObject _couponobj = _coupons.getJSONObject(j);
                                    Coupon coupon = new Coupon();

                                    coupon.couponCode = _couponobj.getString("couponCode");
                                    coupon.couponId = _couponobj.getInt("couponId");
                                    coupon.productId = _couponobj.getInt("productId");
                                    coupon.orderId = _couponobj.getInt("orderId");
                                    coupon.userId = _couponobj.getInt("userId");

                                    int key = _couponobj.getInt("productId");
                                    if(order.coupons.get(key) == null)
                                    {
                                        order.coupons.put(key, new ArrayList<Coupon>());
                                    }
                                    order.coupons.get(key).add(coupon);
                                }

                                JSONArray _orderProducts = obj.getJSONArray("orderProducts");
                                for(int k = 0; k < _orderProducts.length();k++)
                                {
                                    JSONObject _orderProductobj = _orderProducts.getJSONObject(k);
                                    OrderProduct orderProduct = new OrderProduct();

                                    orderProduct.productPrice = _orderProductobj.getDouble("productPrice");
                                    orderProduct.productQuantity = _orderProductobj.getInt("productQuantity");
                                    orderProduct.productId = _orderProductobj.getInt("productId");

                                    JSONObject _productobj = _orderProductobj.getJSONObject("product");

                                    orderProduct.product.companyName = _productobj.getString("companyName");
                                    orderProduct.product.companyWebsite = _productobj.getString("companyWebsite");
                                    orderProduct.product.categoryName = _productobj.getString("categoryName");
                                    orderProduct.product.courseHourDuration = _productobj.getString("courseHourDuration");
                                    orderProduct.product.courseLevel = _productobj.getString("courseLevel");
                                    orderProduct.product.productId = _productobj.getInt("productId");
                                    orderProduct.product.courseLevelId = _productobj.getInt("courseLevelId");
                                    orderProduct.product.courseMinuteDuration = _productobj.getString("courseMinuteDuration");
                                    orderProduct.product.discountPercent = _productobj.getInt("discountPercent");
                                    orderProduct.product.discountPrice = _productobj.getDouble("discountPrice");
                                    orderProduct.product.explanation = _productobj.getString("explanation");
                                    orderProduct.product.productCategoryId = _productobj.getInt("productCategoryId");
                                    orderProduct.product.name = _productobj.getString("name");
                                    orderProduct.product.price = _productobj.getDouble("price");
                                    orderProduct.product.image = android.util.Base64.decode(_productobj.getString("base64Image"),android.util.Base64.DEFAULT);
                                    orderProduct.product.instructorId = _productobj.getInt("userId");
                                    orderProduct.product.isAvailable = _productobj.getBoolean("isAvailable");

                                    order.products.add(orderProduct);
                                }

                                orders.add(order);

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
                headerMap.put("Authorization", "Bearer " + user.token);
                return headerMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }
}
