package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import maes.tech.intentanim.CustomIntent;

public class KlasmenActivity extends AppCompatActivity {
    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klasmen);
        getSupportActionBar().hide();
    }
}