package com.example.ocean1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class homepageActivity extends AppCompatActivity {

    homeFragment homeFragment = new homeFragment();
    accountFragment accountFragment = new accountFragment();
    couponsFragment couponsFragment = new couponsFragment();
    favoritesFragment favoritesFragment = new favoritesFragment();
    basketFragment basketFragment=new basketFragment();

    public static HashMap<String,Integer> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        TinyDB tinyDb = new TinyDB(this);
        Api.user = tinyDb.getObject("user",User.class);

        if(Api.user == null)
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
        }

        categories = new HashMap<String,Integer>();
        Api.getCategories(categories,this, new VolleyCallBack() {
            @Override
            public void onSuccess() { }
        });

        NavigationView navigationView = findViewById(R.id.navmenu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){
                   case R.id.account:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,accountFragment).addToBackStack(null).commit();
                       return true;
                   case R.id.favorites:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,favoritesFragment).addToBackStack(null).commit();
                       return true;
                   case R.id.coupon:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,couponsFragment).addToBackStack(null).commit();
                       return true;
                   case R.id.home:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                       return true;
                   case R.id.basket:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,basketFragment).commit();
                       return true;
               }
               return false;
           }
       });


    }
}