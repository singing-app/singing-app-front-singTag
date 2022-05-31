package com.example.tag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<FriendItem> mFriendList;

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mFriendList.get(position));
    }

    public void setFriendList(ArrayList<FriendItem> list){
        this.mFriendList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_num;
        TextView text_title_insert;
        TextView text_singer_insert;
        TextView text_pitch_insert;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            txt_num = (TextView) itemView.findViewById(R.id.txt_num);
            text_title_insert = (TextView) itemView.findViewById(R.id.text_title_insert);
            text_singer_insert = (TextView) itemView.findViewById(R.id.text_singer_insert);
            text_pitch_insert = (TextView) itemView.findViewById(R.id.text_pitch_insert);
        }

        void onBind(FriendItem item){
            imageView.setImageResource(item.getResourceId());
            txt_num.setText(item.getTxt_num());
            text_title_insert.setText(item.getText_title_insert());
            text_singer_insert.setText(item.getText_singer_insert());
            text_pitch_insert.setText(item.getText_pitch_insert());
        }
    }
}