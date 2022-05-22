package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class myProfileFragment extends Fragment {


    TextView _name;
    TextView _eamil;
    TextView _phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);


        _name = view.findViewById(R.id.name);
        _eamil= view.findViewById(R.id.email);
        _phone = view.findViewById(R.id.phone);
        _name.setText(Api.user.name);
        _eamil.setText(Api.user.email);
        _phone.setText(Api.user.phone);

        return view;
    }
}