package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class couponsFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupons, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();

        BottomNavigationView bottomNavigationView  =  homepageActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.coupon);

        List<Order> orders = new ArrayList<Order>();
        Order.getOrders(orders, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("COUPON ==== " + orders.get(0).coupons.get(1).get(1).couponId);
            }
        });

        return view;
    }
}