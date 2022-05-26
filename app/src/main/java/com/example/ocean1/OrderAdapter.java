package com.example.ocean1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemHolder>{
    private Context ctx;
    private ArrayList<Order> orders;
    OnItemClickListener listener;

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.orderdetails, parent, false);
        return new OrderItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        Order currentOrder = orders.get(position);
        String orderDate = currentOrder.dateOfOrder;
        double dprice = currentOrder.price;
        String sprice = Double.toString(dprice);
        String recipientMail = currentOrder.recipientMail;

        holder._date.setText(orderDate);
        holder._price.setText(sprice);
        holder._recipient.setText(recipientMail);

        holder._productRecycler.setHasFixedSize(true);
        holder._productRecycler.setLayoutManager(new LinearLayoutManager(ctx));
        holder.orderProductAdapter = new OrderProductAdapter(ctx,currentOrder.products);
        holder._productRecycler.setAdapter(holder.orderProductAdapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public OrderAdapter(Context ctx, ArrayList<Order> orders)
    {
        this.ctx = ctx;
        this.orders = orders;
    }

    public class OrderItemHolder extends RecyclerView.ViewHolder{

        public TextView _date;
        public TextView _price;
        public TextView _recipient;
        public RecyclerView _productRecycler;
        public OrderProductAdapter orderProductAdapter;

        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);
            _date = itemView.findViewById(R.id.date);
            _recipient = itemView.findViewById(R.id.mail);
            _price = itemView.findViewById(R.id.price);
            _productRecycler = itemView.findViewById(R.id.productRecycler);

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
