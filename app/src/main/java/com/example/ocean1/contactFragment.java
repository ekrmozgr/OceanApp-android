package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class contactFragment extends Fragment {

    TextView _name;
    TextView _email;
    TextView _phone;
    TextView _adress;
    homepageActivity homepageActivity;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        _name = view.findViewById(R.id.name);
        _email = view.findViewById(R.id.email);
        _phone = view.findViewById(R.id.phone);
        _adress = view.findViewById(R.id.adress);

        HashMap<String, String> hashMap =new HashMap<>();
        Api.getCompany(hashMap, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                _name.setText(hashMap.get("companyName"));
                _email.setText(hashMap.get("email"));
                _phone.setText(hashMap.get("phoneNo"));
                _adress.setText(hashMap.get("address"));
            }
        });


        return view;
    }
}