package com.example.ocean1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;


public class loginFragment extends Fragment {

    MainActivity mainActivity;
    EditText etemail, etpassword;
    String email, password;
    String url= "https://localhost:7157/api/login";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etemail=view.findViewById(R.id.emails);
        etpassword=view.findViewById(R.id.password);


        View goRegister = view.findViewById(R.id.backMain);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=etemail.getText().toString().trim();
                password=etpassword.getText().toString().trim();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, new registerFragment());
                fr.commit();

            }
        });


        Button signin =view.findViewById(R.id.signin);
        mainActivity=(MainActivity)getActivity();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),homepageActivity.class);
                startActivity(i);
            }
        });


   return view;
    }

    public void login(View view){




    }


}