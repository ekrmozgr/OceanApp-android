package com.example.ocean1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItemHolder> {

    private Context ctx;
    private ArrayList<Product> products;
    OnItemClickListener listener;
    User user;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public ProductAdapter(Context ctx, ArrayList<Product> products)
    {
        this.ctx = ctx;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.product_item, parent, false);
        return new ProductItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        Product currentProduct = products.get(position);
        String productName = currentProduct.name;
        String productExplanation = currentProduct.explanation;
        byte[] productImage = currentProduct.image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150,true);

        if(productExplanation.length() > 83)
        {
            productExplanation = productExplanation.substring(0,83);
            productExplanation += "...";
        }

        holder.productName.setText(productName);
        holder.productExplanation.setText(productExplanation);
        holder.productImage.setImageBitmap(resized);

        boolean isInBasket = false;
        boolean isInWhislist = false;

        TinyDB tinyDb = new TinyDB(ctx);
        user = tinyDb.getObject("user",User.class);

        for (int productId: user.basketProducts) {
            if(productId == currentProduct.productId)
            {
                isInBasket = true;
                break;
            }
        }
        for (int productId: user.whishlistProducts) {
            if(productId == currentProduct.productId)
            {
                isInWhislist = true;
                break;
            }
        }
        if(isInWhislist)
            holder.whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        else
            holder.whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        if(isInBasket)
            holder.basketButton.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
        else
            holder.basketButton.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductItemHolder extends RecyclerView.ViewHolder{

        public ImageView productImage;
        public TextView productName;
        public TextView productExplanation;
        public ImageButton basketButton;
        public ImageButton whishlistButton;


        public ProductItemHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productExplanation = itemView.findViewById(R.id.productExplanation);
            basketButton = itemView.findViewById(R.id.basketButton);
            whishlistButton = itemView.findViewById(R.id.whishlistButton);



            whishlistButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product clickedProduct = products.get(getBindingAdapterPosition());
                    if(user.whishlistProducts.contains(clickedProduct.productId))
                    {
                        user.removeFromWhishlist(clickedProduct.productId,ctx,new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                whishlistButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            }
                        });
                    }
                    else
                    {
                        try{
                            user.addToWhishlist(clickedProduct.productId,ctx, new VolleyCallBack() {
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


            basketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Product clickedProduct = products.get(getBindingAdapterPosition());
                    if(user.basketProducts.contains(clickedProduct.productId))
                    {
                        user.removeFromBasket(clickedProduct.productId,ctx,new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                basketButton.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
                            }
                        });
                    }
                    else
                    {
                        try{
                            user.addToBasket(clickedProduct.productId,1,ctx, new VolleyCallBack() {
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
