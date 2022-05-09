package com.example.ocean1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class homepageActivity extends AppCompatActivity {


    homepageFragment homepageFragment = new homepageFragment();
    accountFragment accountFragment = new accountFragment();
    couponsFragment couponsFragment = new couponsFragment();
    favoritesFragment favoritesFragment = new favoritesFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        NavigationView navigationView = findViewById(R.id.navmenu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homepageFragment).commit();

       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){
                   case R.id.account:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).commit();
                       return true;
                   case R.id.favorites:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).commit();
                       return true;
                   case R.id.coupon:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,couponsFragment).commit();
                       return true;
                   case R.id.home:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,homepageFragment).commit();
                       return true;
               }
               return false;
           }
       });

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });

    }
}