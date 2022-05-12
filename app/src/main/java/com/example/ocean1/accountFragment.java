package com.example.ocean1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class accountFragment extends Fragment {

    homepageActivity homepageActivity;
    myProfileFragment myProfileFragment=new myProfileFragment();
    Context ctx;
    TextView _myProfile;
    TextView tvemail;
    TextView tvname;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        homepageActivity=(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        TinyDB tinyDB=new TinyDB(ctx);
        user = tinyDB.getObject("user",User.class);
        tvname = view.findViewById(R.id.name);
        tvemail = view.findViewById(R.id.email);
        tvname.setText(user.name);
        tvemail.setText(user.email);
        Button logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.clear();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        _myProfile=view.findViewById(R.id.myProfile);
        _myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container,myProfileFragment).commit();
            }
        });
        return view;
    }
}