package com.example.ocean1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentItemHolder> {

    private Context ctx;
    private ArrayList<Comments> comments;

    public CommentAdapter(Context ctx, ArrayList<Comments> comments)
    {
        this.ctx = ctx;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.comment, parent, false);
        return new CommentItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemHolder holder, int position) {
        Comments currentComment = comments.get(position);

        String name = currentComment.userName + " " + currentComment.userSurname;
        String date = currentComment.dateOfComment;
        String _comment = currentComment.comment;

        holder.tname.setText(name);
        holder.tdate.setText(date);
        holder.tcomment.setText(_comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentItemHolder extends RecyclerView.ViewHolder{

        public TextView tname;
        public TextView tdate;
        public TextView tcomment;

        public CommentItemHolder(@NonNull View itemView) {
            super(itemView);

            tname = itemView.findViewById(R.id.name_surname);
            tdate = itemView.findViewById(R.id.date);
            tcomment = itemView.findViewById(R.id.comment);
        }


    }
}
