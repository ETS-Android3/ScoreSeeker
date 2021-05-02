package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        CardView matchCardView = findViewById(R.id.matchCardView);
        matchCardView.setOnClickListener(e -> {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(i);
            CustomIntent.customType(MainActivity.this, "left-to-right");
        });
    }
}