package com.example.experiment20249.Adapter;

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
    AssetManager assetManager;
    public Typeface Frizon;
    public Date date;
    public ArrayList<String> weekDate;

    public DateViewAdapter(){
        date=new Date();
        weekDate=date.getWeekList_String();
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_datelist_item,parent,false);
        return new DateViewHolder(itemView,setTyperface(parent.getContext()));
    }
    private Typeface setTyperface(Context context){
        AssetManager assetManager = context.getAssets();
        Frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
        return Frizon;
    }
    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String dateString = weekDate.get(position);
        holder.dayView.setText(dateString);
        holder.dayView.setTypeface(Frizon);
    }

    @Override
    public int getItemCount() {
        return weekDate.size();
    }

}
