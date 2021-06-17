package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.william.scoreseeker.util.MyRequest;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class DetailActivity extends AppCompatActivity {
   private ImageView logo1, logo2;
   private TextView nameTeam1, nameTeam2, date, venue, namaLiga, score1, score2;
   private TextView win1, draw1, lose1, win2, draw2, lose2;

   @Override
   public void finish() {
      super.finish();
      CustomIntent.customType(this, "right-to-left");
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detail);
      getSupportActionBar().hide();
      String idMatch = getIntent().getStringExtra("id");
      getDetail(idMatch);
   }

   private void getDetail(String id) {
      String url = "https://api.football-data.org/v2/matches/" + id;
      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
         logo1 = (ImageView) findViewById(R.id.logo1);
         logo2 = (ImageView) findViewById(R.id.logo2);
         score1 = findViewById(R.id.score1);
         score2 = findViewById(R.id.score2);
         nameTeam1 = findViewById(R.id.nameTeam1);
         nameTeam2 = findViewById(R.id.nameTeam2);
         date = findViewById(R.id.dateMatch);
         venue = findViewById(R.id.venue);
         namaLiga = findViewById(R.id.namaLiga);
         win1 = findViewById(R.id.win1);
         draw1 = findViewById(R.id.draw1);
         lose1 = findViewById(R.id.lose1);
         win2 = findViewById(R.id.win2);
         draw2 = findViewById(R.id.draw2);
         lose2 = findViewById(R.id.lose2);
         try {
            nameTeam1.setText(response.getJSONObject("head2head").getJSONObject("homeTeam").getString("name"));
            nameTeam2.setText(response.getJSONObject("head2head").getJSONObject("awayTeam").getString("name"));
            score1.setText(response.getJSONObject("match").getJSONObject("score").getJSONObject("fullTime").getString("homeTeam"));
            score2.setText(response.getJSONObject("match").getJSONObject("score").getJSONObject("fullTime").getString("awayTeam"));

            String[] rawDate = response.getJSONObject("match").getString("utcDate").substring(0, 19).replace(":", "-").replace("T", "-").split("-");
            LocalDateTime temp = LocalDateTime.of(Integer.parseInt(rawDate[0]), Integer.parseInt(rawDate[1]), Integer.parseInt(rawDate[2]), Integer.parseInt(rawDate[3]), Integer.parseInt(rawDate[4]));
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formattedDate = temp.format(myFormatObj);
            date.setText(formattedDate);

            venue.setText(response.getJSONObject("match").getString("venue"));
            namaLiga.setText(response.getJSONObject("match").getJSONObject("competition").getString("name"));

            win1.setText(response.getJSONObject("head2head").getJSONObject("homeTeam").getString("wins"));
            draw1.setText(response.getJSONObject("head2head").getJSONObject("homeTeam").getString("draws"));
            lose1.setText(response.getJSONObject("head2head").getJSONObject("homeTeam").getString("losses"));

            win2.setText(response.getJSONObject("head2head").getJSONObject("awayTeam").getString("wins"));
            draw2.setText(response.getJSONObject("head2head").getJSONObject("awayTeam").getString("draws"));
            lose2.setText(response.getJSONObject("head2head").getJSONObject("awayTeam").getString("losses"));

            Uri uri1 = Uri.parse("https://crests.football-data.org/"+response.getJSONObject("head2head").getJSONObject("homeTeam").getString("id")+".svg");
            Uri uri2 = Uri.parse("https://crests.football-data.org/"+response.getJSONObject("head2head").getJSONObject("awayTeam").getString("id")+".svg");
            GlideToVectorYou.init().with(this).load(uri1, logo1);
            GlideToVectorYou.init().with(this).load(uri2, logo2);
         } catch (JSONException e) {
            e.printStackTrace();
         }

      }, error-> {

      }) {
         @Override
         public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("X-Auth-Token", getString(R.string.key));
            return headers;
         }
      };
      MyRequest.getInstance(this).addToRequestQueue(jsonObjectRequest);
   }
}