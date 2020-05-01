package com.example.weather.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class ObjectsAdapter<T> extends RecyclerView.Adapter<ObjectsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
    private List<T> mItems = new ArrayList<>();
    public void setItems(List<T> items) {
        mItems = items;
    }

    @Override
    public ObjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(ObjectsAdapter.ViewHolder holder, int position) {
        T item = mItems.get(position);
        TextView textView = holder.titleTv;
        textView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}