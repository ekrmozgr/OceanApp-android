package com.example.ocean1;

import android.content.Context;
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

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketItemHolder> {

    private ArrayList<Product> products;
    private Context ctx;
    public ImageButton addquantity;
    public ImageButton subquantity;
    public TextView quantity;
    double _quantity;

    public BasketAdapter(Context ctx, ArrayList<Product> products)
    {
        this.ctx = ctx;
        this.products = products;
    }

    public BasketItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.basket_item, parent, false);

        addquantity=v.findViewById(R.id.addquantity);
        subquantity=v.findViewById(R.id.subquantity);
        quantity=v.findViewById(R.id.quantity);


        return new BasketItemHolder(v);
    }

    public void onBindViewHolder(@NonNull BasketAdapter.BasketItemHolder holder, int position) {
        Product currentProduct = products.get(position);
        String productName = currentProduct.name;
        byte[] productImage = currentProduct.image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150,true);

        holder.productName.setText(productName);
        holder.productImage.setImageBitmap(resized);


        boolean isInBasket = false;


        TinyDB tinyDb = new TinyDB(ctx);
        User user = tinyDb.getObject("user",User.class);

        for (int productId: user.basketProducts) {
            if(productId == currentProduct.productId)
            {
                isInBasket = true;
                break;
            }
        }
        if(isInBasket)
            holder.basketButton.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);

        addquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _quantity++;
                quantity.setText(String.valueOf(_quantity));

            }
        });

        subquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _quantity--;
                quantity.setText(String.valueOf(_quantity));
            }
        });

    }

    public int getItemCount() {
        return products.size();
    }


    public class BasketItemHolder extends RecyclerView.ViewHolder{

        public ImageView productImage;
        public TextView productName;
        public ImageButton basketButton;



        public BasketItemHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            basketButton = itemView.findViewById(R.id.basketButton);

        }
    }

}
