package com.example.ocean1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class productFragment extends Fragment {

    homepageActivity homepageActivity;
    Context ctx;
    TextView productName;
    ImageView image;
    TextView description;
    ImageButton whishlistButton;
    ImageButton basketButton;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    TextView tprice;

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
        basketButton = view.findViewById(R.id.basketButton);
        whishlistButton = view.findViewById(R.id.whishlistButton);
        recyclerView = view.findViewById(R.id.recycler_view);
        tprice = view.findViewById(R.id.productprice);

        Bundle bundle = this.getArguments();
        final int productId;
        if(bundle != null){
            productName.setText(bundle.getString("productName"));
            description.setText(bundle.getString("description"));
            tprice.setText(bundle.getString("price"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bundle.getByteArray("image"), 0, bundle.getByteArray("image").length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 200,true);
            image.setImageBitmap(resized);
            productId = bundle.getInt("productId");
        }
        else
            productId = 0;

        if(Api.user.basketProducts.contains(Integer.valueOf(productId)))
            basketButton.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
        else
            basketButton.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        if(Api.user.whishlistProducts.contains(Integer.valueOf(productId)))
            whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        else
            whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);

        basketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Api.user.basketProducts.contains(productId))
                {
                    Api.user.removeFromBasket(productId,ctx,new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            basketButton.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
                        }
                    });
                }
                else
                {
                    try{
                        Api.user.addToBasket(productId,1,ctx, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                basketButton.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
                            }
                        });
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        whishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Api.user.whishlistProducts.contains(productId))
                {
                    Api.user.removeFromWhishlist(productId,ctx,new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        }
                    });
                }
                else
                {
                    try{
                        Api.user.addToWhishlist(productId,ctx, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                            }
                        });
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });


        ArrayList<Comments> comments = new ArrayList<>();
        Product.getProductComments(productId, comments, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                commentAdapter = new CommentAdapter(ctx, comments);
                recyclerView.setAdapter(commentAdapter);
            }
        });


        return view;
    }
}
