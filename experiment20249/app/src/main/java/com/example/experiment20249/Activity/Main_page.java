package com.example.experiment20249.Activity;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.experiment20249.Adapter.DateViewAdapter;
import com.example.experiment20249.Class.Date;
import com.example.experiment20249.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class Main_page extends AppCompatActivity {
    private Date date = new Date();
    private RecyclerView main_page_recyclerView;
    private DateViewAdapter dateViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    private TextView main_page_date_year;
    private TextView main_page_date_month_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initView();
    }
    
    private void initView() {
        setPageFont();
        initRecyclerView();
        setDate();

    }
    private void setPageFont(){//设置字体
        try {
            AssetManager assetManager = getAssets();
            Typeface Frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
            Typeface huiwenmingchao = Typeface.createFromAsset(assetManager, "font/汇文明朝体.otf");

            main_page_date_year = findViewById(R.id.main_page_date_year);
            main_page_date_month_day = findViewById(R.id.main_page_date_month_day);
            main_page_date_year.setTypeface(Frizon);
            main_page_date_month_day.setTypeface(huiwenmingchao);
            for(int i = 1 ;i<=7;i++ ){
                TextView textView = findViewById(getResources().getIdentifier("main_page_date_week"+i, "id", getPackageName()));
                textView.setTypeface(huiwenmingchao);
            }
        }catch (Exception e){
            Log.e("FontError", "Font asset not found", e);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setDate(){
        main_page_date_year = findViewById(R.id.main_page_date_year);
        main_page_date_month_day = findViewById(R.id.main_page_date_month_day);
        main_page_date_year.setText(date.getYear());
        main_page_date_month_day.setText(date.getMonth()+"月"+date.getDay()+"日");
    }
    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        main_page_recyclerView = findViewById(R.id.main_page_date_recycle_view);
        dateViewAdapter = new DateViewAdapter();
        main_page_recyclerView.setLayoutManager(linearLayoutManager);
        main_page_recyclerView.setAdapter(dateViewAdapter);

        main_page_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dx<0){
                    date.setLastWeek();
                    dateViewAdapter.weekDate = date.getWeekList_Last_String();
                    dateViewAdapter.notifyDataSetChanged();
                }
                if(dx>0){
                    date.setNextWeek();
                    dateViewAdapter.weekDate = date.getWeekList_Next_String();
                    dateViewAdapter.notifyDataSetChanged();

                }
                }
            }
        );
    }
}