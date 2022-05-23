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

import org.w3c.dom.Text;


public class accountFragment extends Fragment {

    homepageActivity homepageActivity;
    myProfileFragment myProfileFragment=new myProfileFragment();
    contactFragment contactFragment = new contactFragment();
    aboutragment aboutragment= new aboutragment();
    Context ctx;
    TextView _myProfile;
    TextView _contact;
    TextView tvemail;
    TextView tvname;
    TextView _about;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        homepageActivity=(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        tvname = view.findViewById(R.id.name);
        tvemail = view.findViewById(R.id.email);
        tvname.setText(Api.user.name);
        tvemail.setText(Api.user.email);
        Button logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TinyDB tinyDb = new TinyDB(ctx);
                tinyDb.clear();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        _myProfile=view.findViewById(R.id.myProfile);
        _myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container,myProfileFragment).addToBackStack(null).commit();
            }
        });
        _contact = view.findViewById(R.id.contact);
        _contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, contactFragment).addToBackStack(null).commit();
            }
        });
        _about=view.findViewById(R.id.about);
        _about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, aboutragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}