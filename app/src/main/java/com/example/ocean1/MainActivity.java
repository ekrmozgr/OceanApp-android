package com.example.ocean1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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