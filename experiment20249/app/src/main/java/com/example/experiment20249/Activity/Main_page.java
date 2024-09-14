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
import com.example.experiment20249.Interface.OnItemSelectedListener;
import com.example.experiment20249.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class Main_page extends AppCompatActivity {
    private static final String TAG = "Main_page";
    private RecyclerView main_page_recyclerView;
    private DateViewAdapter dateViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    private TextView main_page_date_year;
    private TextView main_page_date_month;
    private TextView main_page_date_day;
    private TextView main_page_date_yue;
    private TextView main_page_date_ri;

    private static final int SCROLL_THRESHOLD = 1; // 滑动阈值，可以根据需要调整
    private int totalDx = 0; // 记录上一次滑动的距离
    private int scrollDirection = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initView();
    }
    
    private void initView() {
        Date.getCurrentTime();
        setPageFont();
        initRecyclerView();
        firstSetDate();
        setAdapterListener();

    }
    private void setPageFont(){//设置字体
        try {
            AssetManager assetManager = getAssets();
            Typeface Frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
            Typeface huiwenmingchao = Typeface.createFromAsset(assetManager, "font/汇文明朝体.otf");
            main_page_date_year = findViewById(R.id.main_page_date_year);
            main_page_date_month = findViewById(R.id.main_page_date_month);
            main_page_date_day = findViewById(R.id.main_page_date_day);
            main_page_date_yue = findViewById(R.id.main_page_date_yue);
            main_page_date_ri = findViewById(R.id.main_page_date_ri);
            main_page_date_year.setTypeface(Frizon);
            main_page_date_month.setTypeface(huiwenmingchao);
            main_page_date_day.setTypeface(huiwenmingchao);
            main_page_date_yue.setTypeface(huiwenmingchao);
            main_page_date_ri.setTypeface(huiwenmingchao);
            for(int i = 1 ;i<=7;i++ ){
                TextView textView = findViewById(getResources().getIdentifier("main_page_date_week"+i, "id", getPackageName()));
                textView.setTypeface(huiwenmingchao);
            }
        }catch (Exception e){
            Log.e("FontError", "Font asset not found", e);
        }
    }
    @SuppressLint("SetTextI18n")
    private void firstSetDate(){
        main_page_date_year = findViewById(R.id.main_page_date_year);
        main_page_date_month = findViewById(R.id.main_page_date_month);
        main_page_date_day = findViewById(R.id.main_page_date_day);
        main_page_date_year.setText(Date.getYear());
        main_page_date_month.setText(Date.getMonth());
        main_page_date_day.setText(Date.getDay());
    }
    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        main_page_recyclerView = findViewById(R.id.main_page_date_recycle_view);
        dateViewAdapter = new DateViewAdapter(Date.getWeekList_String());
        main_page_recyclerView.setLayoutManager(linearLayoutManager);
        main_page_recyclerView.setAdapter(dateViewAdapter);
        main_page_recyclerView.setHasFixedSize(true);
        main_page_recyclerView.setItemViewCacheSize(7);

        main_page_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            private int totalDx = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDx += dx;
                if(dx >0){
                    scrollDirection = 1;
                }else if(dx <0){
                    scrollDirection = -1;
                }
            }
            @Override
            public void onScrollStateChanged (@NonNull RecyclerView recyclerView,int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(Math.abs(totalDx)> SCROLL_THRESHOLD) {
                        // 当滚动停止时，重置滑动距离
                        if (scrollDirection == 1) {
                            // 向右滑动超过阈值
                            Date.addWeek();
                        } else if( scrollDirection == -1) {
                            // 向左滑动超过阈值
                            Date.subtractWeek();
                        }
                        dateViewAdapter.refreshData();
                        // 重置累积的滑动距离
                        totalDx = 0;
                        scrollDirection = 0;
                    }
                }
            }
        });
    }
    private void setAdapterListener(){
        dateViewAdapter.setOnItemSelected(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int position) {
                String selectedDate = Date.getWeekList().get(position).toString();
                Log.d(TAG, "Selected date: " + selectedDate);
                String year = selectedDate.substring(0,4);
                String month = selectedDate.substring(5,7);
                String day = selectedDate.substring(8);
                main_page_date_year.setText(year);
                main_page_date_month.setText(month);
                main_page_date_day.setText(day);

            }
        });
    }
}