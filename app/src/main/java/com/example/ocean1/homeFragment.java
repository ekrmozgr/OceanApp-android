package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class homeFragment extends Fragment {
    homepageActivity homepageActivity;
    Context ctx;
    TextView name;
    AppCompatImageButton button;
    DrawerLayout drawerLayout;
    boolean isItemSelected;
    homepageFragment homepageFragment = new homepageFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        NavigationView navigationView = view.findViewById(R.id.navmenu);
        isItemSelected = false;
        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        drawerLayout = view.findViewById(R.id.drawer);
        name = view.findViewById(R.id.textView4);
        name.setText("Hi, " + Api.user.name);
        button = view.findViewById(R.id.imageView3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((homepageActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( (homepageActivity)getActivity(), drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(isItemSelected)
                {
                    FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.container,homepageFragment).addToBackStack(null).commit();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ((homepageActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId",item.getItemId());
                homepageFragment.setArguments(bundle);
                drawerLayout.closeDrawer(GravityCompat.START);
                isItemSelected = true;
                return true;
            }
        });
        return view;
    }
}
