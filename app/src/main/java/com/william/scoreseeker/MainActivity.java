package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.william.scoreseeker.model.Pertandingan;
import com.william.scoreseeker.util.CustomAdapter;
import com.william.scoreseeker.util.LoadingDialog;
import com.william.scoreseeker.util.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
   Pertandingan p = new Pertandingan();
   LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

   private final ArrayList<Pertandingan> matchList = new ArrayList<>();
   private RecyclerView recyclerView;
   private CustomAdapter recyclerViewAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      getSupportActionBar().hide();

      loadingDialog.startLoading();
      getMatchData();
      getFirstData();

      CardView matchCardView = findViewById(R.id.matchCardView);
      matchCardView.setOnClickListener(e -> {
         Intent i = new Intent(MainActivity.this, DetailActivity.class);
         i.putExtra("id", p.getId());
         startActivity(i);
         CustomIntent.customType(MainActivity.this, "left-to-right");
      });

      ImageView klasmenBanner = findViewById(R.id.klasmen_banner);
      klasmenBanner.setOnClickListener(e -> {
         startActivity(new Intent(MainActivity.this, KlasmenMenuActivity.class));
         CustomIntent.customType(MainActivity.this, "left-to-right");
      });
   }

   private String getDate() {
//      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//      LocalDateTime now = LocalDateTime.now();
      Calendar now = Calendar.getInstance();
      now.add(Calendar.DATE, -30);
      DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
      return dtf.format(now.getTime());
   }
   private String getYesterday() {
      Calendar yesterday = Calendar.getInstance();
     yesterday.add(Calendar.DATE, -40);
      DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
      return dtf.format(yesterday.getTime());
   }

   private void getFirstData() {
      String now = getDate();
      String yesterday = getYesterday();
      String url = "https://api.football-data.org/v2/matches/?competitions=PL,SA,PD,FL1&status=FINISHED&dateFrom=" + yesterday + "&dateTo=" + now;

      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
              (Request.Method.GET, url, null, response -> {
                 JSONArray fetchArray = null;
                 JSONObject single, ht, at, score, fulltime;
                 try {
                    fetchArray = response.getJSONArray("matches");
                    single = fetchArray.getJSONObject(0);
                    ht = single.getJSONObject("homeTeam");
                    at = single.getJSONObject("awayTeam");
                    score = single.getJSONObject("score");
                    fulltime = score.getJSONObject("fullTime");

                    p.setTimKandang((String) ht.get("name"));
                    p.setTimTandang((String) at.get("name"));
                    p.setSkorKandang(String.valueOf(fulltime.get("homeTeam")));
                    p.setSkorTandang(String.valueOf(fulltime.get("awayTeam")));
                    p.setId(String.valueOf(single.get("id")));
                    String[] rawDate = single.getString("utcDate").substring(0, 19).replace(":", "-").replace("T", "-").split("-");
                    p.setIdKandang(String.valueOf(ht.get("id")));
                    p.setIdTandang(String.valueOf(at.get("id")));
                    LocalDateTime temp = LocalDateTime.of(Integer.parseInt(rawDate[0]), Integer.parseInt(rawDate[1]), Integer.parseInt(rawDate[2]), Integer.parseInt(rawDate[3]), Integer.parseInt(rawDate[4]));
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                    String formattedDate = temp.format(myFormatObj);
                    p.setTanggal(formattedDate);

                    final TextView nameTeam1 = findViewById(R.id.nameTeam1);
                    final TextView nameTeam2 = findViewById(R.id.nameTeam2);
                    final TextView skor1 = findViewById(R.id.score1);
                    final TextView skor2 = findViewById(R.id.score2);
                    final TextView tanggal = findViewById(R.id.dateMatch);
                    final ImageView logo1 = findViewById(R.id.logo1);
                    final ImageView logo2 = findViewById(R.id.logo2);

                    nameTeam1.setText(p.getTimKandang());
                    nameTeam2.setText(p.getTimTandang());
                    skor1.setText(p.getSkorKandang());
                    skor2.setText(p.getSkorTandang());
                    tanggal.setText(p.getTanggal());

                    Uri uri1 = Uri.parse("https://crests.football-data.org/"+p.getIdKandang()+".svg");
                    Uri uri2 = Uri.parse("https://crests.football-data.org/"+p.getIdTandang()+".svg");
                    GlideToVectorYou.init().with(this).load(uri1, logo1);
                    GlideToVectorYou.init().with(this).load(uri2, logo2);
                    loadingDialog.endDialog();
                 } catch (JSONException e) {
                    e.printStackTrace();
                 }
              }, error -> {
                 Toast.makeText(MainActivity.this, "Cannot Fetch Data From API", Toast.LENGTH_SHORT).show();
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
   private void getMatchData() {
      recyclerView = findViewById(R.id.recyclerView);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      String now = getDate();
      String yesterday = getYesterday();
      String url = "https://api.football-data.org/v2/matches/?competitions=PL,SA,PD,FL1&status=FINISHED&dateFrom=" + yesterday + "&dateTo=" + now;
      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
              (Request.Method.GET, url, null, response -> {
                 JSONArray fetchArray;
                 try {
                    fetchArray = response.getJSONArray("matches");
                    for (int i = 1; i < fetchArray.length(); i++) {
                       JSONObject s = fetchArray.getJSONObject(i);
                       Pertandingan p = new Pertandingan();
                       p.setTimKandang((String) s.getJSONObject("homeTeam").get("name"));
                       p.setTimTandang((String) s.getJSONObject("awayTeam").get("name"));
                       p.setSkorKandang(String.valueOf(s.getJSONObject("score").getJSONObject("fullTime").get("homeTeam")));
                       p.setSkorTandang(String.valueOf(s.getJSONObject("score").getJSONObject("fullTime").get("awayTeam")));
                       p.setId(String.valueOf(s.get("id")));
                       p.setIdKandang(String.valueOf(s.getJSONObject("homeTeam").get("id")));
                       p.setIdTandang(String.valueOf(s.getJSONObject("awayTeam").get("id")));
                       String[] rawDate = s.getString("utcDate").substring(0, 19).replace(":", "-").replace("T", "-").split("-");
                       LocalDateTime temp = LocalDateTime.of(Integer.parseInt(rawDate[0]), Integer.parseInt(rawDate[1]), Integer.parseInt(rawDate[2]), Integer.parseInt(rawDate[3]), Integer.parseInt(rawDate[4]));
                       DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                       String formattedDate = temp.format(myFormatObj);
                       p.setTanggal(formattedDate);
                       matchList.add(p);
                    }
                    recyclerViewAdapter = new CustomAdapter(MainActivity.this, matchList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                 } catch (JSONException e) {
                    e.printStackTrace();
                 }
              }, error -> {
                 Toast.makeText(MainActivity.this, "Can't Fetch Data From API", Toast.LENGTH_SHORT).show();
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

