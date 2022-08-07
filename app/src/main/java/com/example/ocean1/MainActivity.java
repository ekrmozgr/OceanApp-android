package com.example.ocean1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!Build.FINGERPRINT.contains("generic")) {
            Api.initUrl("http://localhost:7157");
        }
        else
            Api.initUrl("http://10.0.2.2:7157");


        TinyDB tinyDb = new TinyDB(this);
        Api.user = tinyDb.getObject("user",User.class);
        if(Api.user != null)
        {
            Intent i=new Intent(this,homepageActivity.class);
            startActivity(i);
            finish();
        }

        Button login = findViewById(R.id.loginBtn);
        Button register = findViewById(R.id.registerBtn);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,new loginFragment()).commit();
                break;
            case R.id.registerBtn:
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container,new registerFragment()).commit();
                break;
        }
    }
}