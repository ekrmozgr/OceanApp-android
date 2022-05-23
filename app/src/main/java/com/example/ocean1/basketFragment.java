package com.example.ocean1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class basketFragment extends Fragment {
    homepageActivity homepageActivity;
    Context ctx;
    RecyclerView recyclerView;
    BasketAdapter basketAdapter;
    TextView totalPrice;
    Button buybutton;
    AppCompatEditText receiverMail;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        homepageActivity = (homepageActivity) getActivity();
        ctx = homepageActivity.getApplicationContext();
        totalPrice = view.findViewById(R.id.totalprice);
        buybutton = view.findViewById(R.id.buybutton);
        receiverMail = view.findViewById(R.id.receiverMail);

        dialog = new Dialog(ctx);
        Basket basket = new Basket();
        basket.getBasketProducts(basket, Api.user.token, Api.user.id, ctx, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                receiverMail.setText("");
                if (basket.products.size() == 0)
                    buybutton.setEnabled(false);

                BasketAdapter.OnItemClickListener clickListener = new BasketAdapter.OnItemClickListener() {
                    @Override
                    public void onChangeClick(int position) {
                        double price = 0;
                        for (Product product : basket.products) {
                            price += product.discountPrice * product.productQuantity;
                        }
                        totalPrice.setText(Double.toString(price));
                    }

                    @Override
                    public void onProductClick(int position) {
                        Product currentProduct = basket.products.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putInt("productId", currentProduct.productId);
                        bundle.putString("productName", currentProduct.name);
                        bundle.putByteArray("image", currentProduct.image);
                        String description = currentProduct.companyName + "\n" + currentProduct.companyWebsite + "\n\n" + currentProduct.explanation + "\n\n" +
                                "Category : " + currentProduct.categoryName + "\n" +
                                "Level : " + currentProduct.courseLevel + "\n" +
                                "Duration : " + currentProduct.courseHourDuration + "h " + currentProduct.courseMinuteDuration + "m\n";
                        bundle.putString("description", description);
                        String price = "Price : " + currentProduct.discountPrice + " $";
                        bundle.putString("price", price);
                        productFragment productFragment = new productFragment();
                        productFragment.setArguments(bundle);

                        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                        fm.replace(R.id.container, productFragment).addToBackStack(null).commit();
                    }
                };

                basketAdapter = new BasketAdapter(ctx, basket.products);
                basketAdapter.setOnItemClickListener(clickListener);
                recyclerView.setAdapter(basketAdapter);
                recyclerView.addRecyclerListener(new RecyclerView.RecyclerListener() {
                    @Override
                    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                        if (holder.getBindingAdapter().getItemCount() == 0)
                            buybutton.setEnabled(false);
                    }
                });
                totalPrice.setText(Double.toString(basket.price) + " $");
            }
        });

        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket.purchaseBasket(Api.user.id, receiverMail.getText().toString(), ctx, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        System.out.println("satinalmabasarili");
                        Olumlu_Uyarı();
                    }
                });
            }
        });

        receiverMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String receiver = receiverMail.getText().toString();
                if (receiver.length() == 0)
                    buybutton.setEnabled(false);
                else if (basketAdapter != null)
                    if (basketAdapter.getItemCount() != 0)
                        buybutton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void Olumlu_Uyarı() {

        dialog.setContentView(R.layout.ordercomplete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.imageViewKapat);
        Button button_okey = dialog.findViewById(R.id.button_okey);


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        button_okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

