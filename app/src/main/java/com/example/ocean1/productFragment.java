package com.example.ocean1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class productFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    TextView productName;
    ImageView image;
    TextView description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product, container, false);

        homepageActivity =(homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        productName = view.findViewById(R.id.product_name);
        image = view.findViewById(R.id.photo);
        description = view.findViewById(R.id.description);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            productName.setText(bundle.getString("productName"));
            description.setText(bundle.getString("description"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray("image"), 0, bundle.getByteArray("image").length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 200,true);
            image.setImageBitmap(resized);
        }

        return view;
    }
}
