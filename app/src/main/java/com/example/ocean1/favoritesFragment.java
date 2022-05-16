package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class favoritesFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();

        List<Product> products = new ArrayList<Product>();
        Product.getFavouritesProducts(products, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {

            }
        });

        return view;
    }
}