package com.example.experiment20249.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experiment20249.Class.Date;
import com.example.experiment20249.R;

import java.util.ArrayList;

public class DateViewAdapter extends RecyclerView.Adapter<DateViewHolder>{
    private static final String TAG = "DateViewAdapter";
    public Typeface frizon;
    private ArrayList<String> weekDate;
    private ArrayList<Boolean> isSelected;


    public DateViewAdapter (ArrayList<String> weekDate){
        this.weekDate = weekDate;
        isSelected = new ArrayList<>();
        for (int i = 0; i < weekDate.size(); i++) {
            if(weekDate.get(i).equals(Date.getCurrentTime())){
                isSelected.add(true);
            }else{
                isSelected.add(false);
            }
        }

    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_datelist_item,parent,false);
        return new DateViewHolder(itemView,setTyperface(parent.getContext()));
    }
    private Typeface setTyperface(Context context){
        AssetManager assetManager = context.getAssets();
        frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
        return frizon;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        if (weekDate != null && position < weekDate.size()) {
            String ymdString = weekDate.get(position);
            String monthString = ymdString.substring(5,7);
            String dayString = ymdString.substring(8);
            holder.dayView.setText(dayString);
            holder.dayView.setTypeface(frizon);
            if (isSelected.get(position)) {
                holder.dayView.setTextColor(holder.itemView.getResources().getColor(R.color.white));
                holder.dayView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.black));
            } else {
                holder.dayView.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                holder.dayView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white));
            }
        } else {
            holder.dayView.setText(""); // 或者设置默认值
        }
    }

    @Override
    public int getItemCount() {
        return weekDate != null ? weekDate.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<String> newWeekDate) {
        weekDate = newWeekDate;
        notifyDataSetChanged();
    }
}
