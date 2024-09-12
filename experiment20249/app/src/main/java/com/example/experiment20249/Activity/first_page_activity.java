package com.example.experiment20249.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.experiment20249.R;

public class first_page_activity extends AppCompatActivity {
    private static final String TAG = "first_page_activity";
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);

        initView();
        startSplashActivity();


    }

    private void initView() {
        welcomeFontSet();
    }

    public void welcomeFontSet(){
        try {
            AssetManager assetManager = getAssets();
            Typeface Frizon = Typeface.createFromAsset(assetManager, "font/Frizon.ttf");
            Typeface huiwenmingchao = Typeface.createFromAsset(assetManager, "font/汇文明朝体.otf");
            TextView welcome = findViewById(R.id.first_page_welcome);
            TextView title = findViewById(R.id.first_page_title);
            TextView sec_title = findViewById(R.id.first_page_sec_title);
            welcome.setTypeface(huiwenmingchao);
            title.setTypeface(Frizon);
            sec_title.setTypeface(huiwenmingchao);
        }
        catch (Exception e){
            Log.e("FontError", "Font asset not found", e);
        }
    }
    private void startSplashActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(first_page_activity.this, Main_page.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
            finish();
            Log.d(TAG, "startSplashActivity: ");
        }, SPLASH_TIME_OUT);
    }
}