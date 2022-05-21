package com.example.ocean1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class basketFragment extends Fragment {
    homepageActivity homepageActivity;
    Context ctx;
    RecyclerView recyclerView;
    BasketAdapter basketAdapter;
    TextView totalPrice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_basket, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        totalPrice = view.findViewById(R.id.totalprice);
        Basket basket = new Basket();
        basket.getBasketProducts(basket,Api.user.token, Api.user.id, ctx,new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                BasketAdapter.OnItemClickListener clickListener = new BasketAdapter.OnItemClickListener() {
                    @Override
                    public void onChangeClick(int position) {
                        double price = 0;
                        for (Product product: basket.products) {
                            price += product.discountPrice * product.productQuantity;
                        }
                        totalPrice.setText(Double.toString(price));
                    }

                    @Override
                    public void onProductClick(int position) {

                    }
                };

                basketAdapter = new BasketAdapter(ctx, basket.products);
                basketAdapter.setOnItemClickListener(clickListener);
                recyclerView.setAdapter(basketAdapter);
                totalPrice.setText(Double.toString(basket.price) + " $");
            }
        });

        return view;
    }
}