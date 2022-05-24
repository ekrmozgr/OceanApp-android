package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class favoritesFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    RecyclerView recyclerView;
    FavoritesAdapter favoritesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        recyclerView = view.findViewById(R.id.recycler_view);

        BottomNavigationView bottomNavigationView  =  homepageActivity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorites);

        ArrayList<Product> products = new ArrayList<Product>();
        Product.getFavouritesProducts(products, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                FavoritesAdapter.OnItemClickListener clickListener = new FavoritesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Product currentProduct = products.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putInt("productId",currentProduct.productId);
                        bundle.putString("productName",currentProduct.name);
                        bundle.putByteArray("image",currentProduct.image);
                        String description = currentProduct.companyName + "\n" + currentProduct.companyWebsite + "\n\n" + currentProduct.explanation + "\n\n" +
                                "Category : " + currentProduct.categoryName + "\n" +
                                "Level : " + currentProduct.courseLevel + "\n" +
                                "Duration : " + currentProduct.courseHourDuration + "h " + currentProduct.courseMinuteDuration + "m\n";
                        bundle.putString("description", description);
                        String price = "Price : " + currentProduct.discountPrice + " $";
                        bundle.putString("price",price);
                        productFragment productFragment = new productFragment();
                        productFragment.setArguments(bundle);

                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        fm.replace(R.id.container,productFragment).addToBackStack(null).commit();
                    }
                };

                favoritesAdapter = new FavoritesAdapter(ctx, products);
                favoritesAdapter.setOnItemClickListener(clickListener);
                recyclerView.setAdapter(favoritesAdapter);
            }
        });

        return view;
    }
}