package com.example.ocean1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;


public class loginFragment extends Fragment {

    MainActivity mainActivity;
    EditText etemail, etpassword;
    String email, password;
    Context ctx;
    sharedPrefence sharedPrefence;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etemail=view.findViewById(R.id.emails);
        etpassword=view.findViewById(R.id.password);
        sharedPrefence=new sharedPrefence();

        View goRegister = view.findViewById(R.id.backMain);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, new registerFragment());
                fr.commit();

            }
        });

        Button signin =view.findViewById(R.id.signin);
        mainActivity=(MainActivity)getActivity();
        ctx = mainActivity.getApplicationContext();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etemail.getText().toString();
                password = etpassword.getText().toString();
                User user = new User();
                try {
                    user.initializeUser(email,password,ctx,new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            sharedPrefence.save(ctx,email);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


   return view;
    }
}