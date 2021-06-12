package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.william.scoreseeker.model.Klasmen;

import maes.tech.intentanim.CustomIntent;

public class KlasmenMenuActivity extends AppCompatActivity {
   ImageView premier, ligue1, laliga, seriea;

   @Override
   public void finish() {
      super.finish();
      CustomIntent.customType(this, "right-to-left");
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_klasmen_menu);
      getSupportActionBar().hide();
      premier = findViewById(R.id.premier);
      ligue1 = findViewById(R.id.ligue1);
      seriea = findViewById(R.id.seriea);
      laliga = findViewById(R.id.laliga);
      move(premier, "2021");
      move(laliga, "2014");
      move(seriea, "2019");
      move(ligue1, "2015");

   }

   public void move(ImageView view, String id) {
      view.setOnClickListener(e -> {
         Intent i = new Intent(KlasmenMenuActivity.this, KlasmenActivity.class);
         i.putExtra("id", id);
         startActivity(i);
         CustomIntent.customType(KlasmenMenuActivity.this, "left-to-right");
      });
   }
}