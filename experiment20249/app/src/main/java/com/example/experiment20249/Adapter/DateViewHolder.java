package com.example.experiment20249.Adapter;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.experiment20249.Class.Date;
import com.example.experiment20249.R;

import java.util.ArrayList;

public class DateViewHolder extends BaseViewHolder {
    public TextView dayView;
    public DateViewHolder(@NonNull View itemView, Typeface typeface) {
        super(itemView);
        dayView = itemView.findViewById(R.id.date);
        dayView.setTypeface(typeface);
    }
}
