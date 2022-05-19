package com.example.ocean1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class homepageFragment extends Fragment{

    homepageActivity homepageActivity;
    Context ctx;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    TinyDB tinyDb;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_homepage, container, false);

        NavigationView navigationView = view.findViewById(R.id.navmenu);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((homepageActivity)getActivity()).setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( (homepageActivity)getActivity(), drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ((homepageActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String key = item.getTitle().toString().trim().toLowerCase();
                int id;
                try {
                    id = homepageActivity.categories.get(key);
                    ArrayList<Product> products = new ArrayList<Product>();
                    Product.getCategoryProducts(id, products, ctx,new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            tinyDb = new TinyDB(ctx);
                            user = tinyDb.getObject("user", User.class);

                            user.getWhishlist(ctx, new VolleyCallBack() {
                                @Override
                                public void onSuccess() {
                                    recyclerView = view.findViewById(R.id.recycler_view);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                                    ProductAdapter.OnItemClickListener clickListener = new ProductAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            Product currentProduct = products.get(position);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("productName",currentProduct.name);
                                            bundle.putByteArray("image",currentProduct.image);
                                            String description = currentProduct.companyName + "\n" + currentProduct.companyWebsite + "\n\n" + currentProduct.explanation + "\n\n\n" +
                                                    "Category : " + currentProduct.categoryName + "\n" +
                                                    "Level : " + currentProduct.courseLevel + "\n" +
                                                    "Duration : " + currentProduct.courseHourDuration + "h " + currentProduct.courseMinuteDuration + "m\n" +
                                                     "\n\n" + "Price : " + currentProduct.discountPrice + " $";
                                            bundle.putString("description", description);

                                            productFragment productFragment = new productFragment();
                                            productFragment.setArguments(bundle);

                                            FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                                            fm.replace(R.id.container,productFragment).addToBackStack(null).commit();
                                        }
                                    };

                                    productAdapter = new ProductAdapter(ctx, products, user);
                                    productAdapter.setOnItemClickListener(clickListener);
                                    recyclerView.setAdapter(productAdapter);

                                    drawerLayout.closeDrawer(GravityCompat.START);
                                }
                            });
                        }
                    });
                } catch (Exception e)
                {
                }
                return true;
            }
        });
        return view;
    }
}