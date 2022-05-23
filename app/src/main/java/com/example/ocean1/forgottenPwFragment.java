package com.example.ocean1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class forgottenPwFragment extends Fragment {
    MainActivity mainActivity;
    Context ctx;
    Button sendpw;
    TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgottenpw, container, false);

        mainActivity=(MainActivity)getActivity();
        ctx = mainActivity.getApplicationContext();
        sendpw = view.findViewById(R.id.sendpw);
        email = view.findViewById(R.id.emails);

        sendpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = email.getText().toString();
                Api.forgottenPw(_email, ctx, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        email.setText("");
                        Toast.makeText(ctx, "Password sent to Email", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        View signIn = view.findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, new loginFragment());
                fr.commit();
            }
        });

        View signUp = view.findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, new registerFragment());
                fr.commit();
            }
        });

        return view;
    }
}
