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
import com.example.experiment20249.Interface.OnItemSelectedListener;
import com.example.experiment20249.R;

import java.util.ArrayList;

public class DateViewAdapter extends RecyclerView.Adapter<DateViewHolder>{
    private static final String TAG = "DateViewAdapter";
    public static Typeface frizon;
    private ArrayList<String> weekDate;
    private ArrayList<Boolean> isSelected;
    private OnItemSelectedListener onItemSelectedListener;


    public DateViewAdapter (ArrayList<String> weekDate){
        this.weekDate = weekDate;
        isSelected = new ArrayList<>();
        for (int i = 0; i < weekDate.size(); i++) {
            isSelected.add(false);
        }
        int todayIndex = weekDate.indexOf(Date.getCurrentTime());
        if (todayIndex != -1) {
            isSelected.set(todayIndex, true);
        }

    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_datelist_item,parent,false);
        return new DateViewHolder(itemView,setTyperface(parent.getContext()));
    }
    private static Typeface setTyperface(Context context){
        if(frizon == null){
            AssetManager assetManager = context.getAssets();
            frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
        }
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
            holder.dayView.setOnClickListener(v -> {
                isSelected.set(position, !isSelected.get(position));//切换选中状态
                for(int i = 0; i < isSelected.size(); i++){
                    if(i != position){
                        isSelected.set(i, false);//将其他位置的选中状态设置为false
                    }
                }
                notifyDataSetChanged();
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);//通知监听者返回选择的位置以便可以更新mainPage中的月和日期
                }
            });
            boolean selected = isSelected.get(position);
            holder.dayView.setTextColor(selected ? holder.itemView.getResources().getColor(R.color.white) : holder.itemView.getResources().getColor(R.color.black));
            holder.dayView.setBackgroundColor(selected ? holder.itemView.getResources().getColor(R.color.black) : holder.itemView.getResources().getColor(R.color.white));
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
        // 重置 isSelected 列表
        isSelected = new ArrayList<>();
        for (int i = 0; i < weekDate.size(); i++) {
            isSelected.add(false); // 默认所有日期未选中
        }
        // 更新数据
        notifyDataSetChanged();
    }
    public void refreshData() {
        updateData(Date.getWeekList_String());
    }
    public void setOnItemSelected(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }
}
