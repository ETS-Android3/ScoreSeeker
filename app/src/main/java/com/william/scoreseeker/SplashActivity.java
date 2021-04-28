package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;

public class SplashActivity extends AppCompatActivity {
private ImageView logoBola;
private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        // Animtion Start Screen
        logoBola = findViewById(R.id.logoBola);
        anim = AnimationUtils.loadAnimation(this, R.anim.appear);
        logoBola.startAnimation(anim);

        // Handler
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                CustomIntent.customType(SplashActivity.this, "left-to-right");
                finish();
            }
        }, 2000);

    }
}