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

import org.json.JSONException;


public class registerFragment extends Fragment {

    MainActivity mainActivity;
    EditText etname;
    EditText etsurname;
    EditText etmail;
    EditText etphone;
    EditText etpw1;
    EditText etpw2;
    Context ctx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        View goLogin = view.findViewById(R.id.backMain);

        etname = view.findViewById(R.id.name);
        etsurname = view.findViewById(R.id.surname);
        etmail = view.findViewById(R.id.email);
        etphone = view.findViewById(R.id.phone);
        etpw1 = view.findViewById(R.id.password);
        etpw2 = view.findViewById(R.id.passwordAgain);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container, new loginFragment());
                fr.commit();
            }
        });

        Button register =view.findViewById(R.id.register);
        mainActivity=(MainActivity)getActivity();
        ctx = mainActivity.getApplicationContext();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etname.getText().toString();
                String surname = etsurname.getText().toString();
                String mail = etmail.getText().toString();
                String phone = etphone.getText().toString();
                String pw1 = etpw1.getText().toString();
                String pw2 = etpw2.getText().toString();
                if(!pw1.equals(pw2))
                    return;
                User user = new User();
                try {
                    user.register(name,surname,mail,phone,pw1,ctx,new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            TinyDB tinyDb = new TinyDB(ctx);
                            tinyDb.putObject("user",user);
                            Intent i=new Intent(getActivity(),homepageActivity.class);
                            startActivity(i);
                            mainActivity.finish();
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