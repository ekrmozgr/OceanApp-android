package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class couponsFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coupons, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();

        BottomNavigationView bottomNavigationView  =  homepageActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.coupon);

        ArrayList<Order> orders = new ArrayList<Order>();
        Order.getOrders(orders, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                OrderAdapter.OnItemClickListener clickListener = new OrderAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        return;
                    }
                };

                orderAdapter = new OrderAdapter(ctx, orders);
                orderAdapter.setOnItemClickListener(clickListener);
                recyclerView.setAdapter(orderAdapter);
            }
        });

        return view;
    }
}