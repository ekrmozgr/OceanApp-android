package com.example.ocean1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductItemHolder> {

    private Context ctx;
    private ArrayList<OrderProduct> orderProducts;


    public OrderProductAdapter(Context ctx, ArrayList<OrderProduct> orderProducts)
    {
        this.ctx = ctx;
        this.orderProducts = orderProducts;
    }

    @NonNull
    @Override
    public OrderProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.orderproduct_item, parent, false);
        return new OrderProductItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductItemHolder holder, int position) {
        OrderProduct currentOrderProduct = orderProducts.get(position);

        byte[] productImage = currentOrderProduct.product.image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 150, 150,true);

        String _productName = currentOrderProduct.product.name;
        double _dproductPrice = currentOrderProduct.productPrice;
        String _sproductPrice = Double.toString(_dproductPrice);
        int _iproductQuantity = currentOrderProduct.productQuantity;
        String _productQuantity = Integer.toString(_iproductQuantity);

        holder.image.setImageBitmap(resized);
        holder.name.setText(_productName);
        holder.price.setText(_sproductPrice);
        holder.quantity.setText(_productQuantity);
        StringBuilder _coupons = new StringBuilder();
        for(int i = 0; i < currentOrderProduct.coupons.size(); i++)
        {
            _coupons.append(i+1).append("--");
            _coupons.append(currentOrderProduct.coupons.get(i).couponCode).append("\n");
        }
        holder.coupons.setText(_coupons);
    }

    @Override
    public int getItemCount() {
        return orderProducts.size();
    }

    public class OrderProductItemHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView name;
        TextView price;
        TextView quantity;
        TextView coupons;

        public OrderProductItemHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            coupons = itemView.findViewById(R.id.productExplanation);
        }
    }
}
