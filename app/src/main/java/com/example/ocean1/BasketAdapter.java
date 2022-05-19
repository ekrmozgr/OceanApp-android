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

import org.json.JSONException;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketItemHolder> {

    private ArrayList<Product> products;
    OnItemClickListener listener;
    private Context ctx;

    public interface OnItemClickListener {
        void onChangeClick(int position);
        void onProductClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    public BasketAdapter(Context ctx, ArrayList<Product> products)
    {
        this.ctx = ctx;
        this.products = products;
    }

    public BasketItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.basket_item, parent, false);
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
        holder.quantity.setText(Integer.toString(currentProduct.productQuantity));
        holder.basketButton.setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
        holder.totalprice.setText(Double.toString(currentProduct.productQuantity * currentProduct.discountPrice));
    }

    public int getItemCount() {
        return products.size();
    }


    public class BasketItemHolder extends RecyclerView.ViewHolder{

        public ImageView productImage;
        public TextView productName;
        public ImageButton basketButton;
        public TextView quantity;
        public TextView totalprice;
        public ImageButton addquantity;
        public ImageButton subquantity;

        public BasketItemHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            basketButton = itemView.findViewById(R.id.basketButton);
            quantity = itemView.findViewById(R.id.quantity);
            totalprice = itemView.findViewById(R.id.totalPrice);
            addquantity = itemView.findViewById(R.id.addquantity);
            subquantity = itemView.findViewById(R.id.subquantity);

            addquantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int _quantity = Integer.parseInt(quantity.getText().toString());
                    if(_quantity < 10)
                        _quantity++;
                    TinyDB tinyDB = new TinyDB(ctx);
                    User user = tinyDB.getObject("user",User.class);
                    products.get(getBindingAdapterPosition()).productQuantity = _quantity;

                    try {
                        user.changeProductQuantity(products.get(getBindingAdapterPosition()).productId, _quantity, ctx, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                listener.onChangeClick(getBindingAdapterPosition());
                                notifyItemChanged(getBindingAdapterPosition());
                            }
                        });
                    } catch (JSONException e) {
                        products.get(getBindingAdapterPosition()).productQuantity -= 1;
                        e.printStackTrace();
                    }
                }
            });

            subquantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int _quantity = Integer.parseInt(quantity.getText().toString());
                    if(_quantity >= 2)
                        _quantity--;
                    TinyDB tinyDB = new TinyDB(ctx);
                    User user = tinyDB.getObject("user",User.class);
                    products.get(getBindingAdapterPosition()).productQuantity = _quantity;

                    try {
                        user.changeProductQuantity(products.get(getBindingAdapterPosition()).productId, _quantity, ctx, new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                listener.onChangeClick(getBindingAdapterPosition());
                                notifyItemChanged(getBindingAdapterPosition());
                            }
                        });
                    } catch (JSONException e) {
                        products.get(getBindingAdapterPosition()).productQuantity += 1;
                        e.printStackTrace();
                    }
                }
            });

            basketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TinyDB tinyDB = new TinyDB(ctx);
                    User user = tinyDB.getObject("user",User.class);
                    user.removeFromBasket(products.get(getBindingAdapterPosition()).productId, ctx, new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            products.remove(getBindingAdapterPosition());
                            listener.onChangeClick(getBindingAdapterPosition());
                            notifyItemRemoved(getBindingAdapterPosition());
                        }
                    });
                }
            });
        }
    }

}
