package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.william.scoreseeker.model.Pertandingan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pertandingan> pertandingan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String now = getDate();
        String yesterday = getYesterday();
        Log.i("DATE", "onCreate: " + now);
        Log.i("DATE", "onCreate: " + yesterday);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.football-data.org/v2/matches/?competitions=PL,SA,PD,FL1&status=FINISHED&dateFrom=" + yesterday + "&dateTo=" + now;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray fetchArray;
                        JSONObject single, ht, at, score, fulltime;
                        try {
                            fetchArray = response.getJSONArray("matches");
                            Log.i("TAG", "onResponse: " + fetchArray);
                            for (int i = 0; i < 1; i++) {
                                single = fetchArray.getJSONObject(i);
                                ht = single.getJSONObject("homeTeam");
                                at = single.getJSONObject("awayTeam");

                                score = single.getJSONObject("score");
                                fulltime = score.getJSONObject("fullTime");

                                Pertandingan p = new Pertandingan();
                                p.setTimKandang((String) ht.get("name"));
                                p.setTimTandang((String) at.get("name"));
                                p.setSkorKandang(String.valueOf(fulltime.get("homeTeam")));
                                p.setSkorTandang(String.valueOf(fulltime.get("awayTeam")));
                                pertandingan.add(p);
                                Log.i("TAG", "onResponse: " + ht.get("name") + " & " + at.get("name"));
                            }

                            final TextView nameTeam1 = (TextView) findViewById(R.id.nameTeam1);
                            final TextView nameTeam2 = (TextView) findViewById(R.id.nameTeam2);
                            final TextView skor1 = (TextView) findViewById(R.id.score1);
                            final TextView skor2 = (TextView) findViewById(R.id.score2);
                            final ImageView logo1 = (ImageView) findViewById(R.id.logo1);
                            final ImageView logo2 = (ImageView) findViewById(R.id.logo2);

                            nameTeam1.setText(pertandingan.get(0).getTimKandang());
                            nameTeam2.setText(pertandingan.get(0).getTimTandang());
                            skor1.setText(pertandingan.get(0).getSkorKandang());
                            skor2.setText(pertandingan.get(0).getSkorTandang());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Auth-Token", getString(R.string.key));
                return headers;
            }
        };
        queue.add(jsonObjectRequest);


        CardView matchCardView = findViewById(R.id.matchCardView);
        matchCardView.setOnClickListener(e -> {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            startActivity(i);
            CustomIntent.customType(MainActivity.this, "left-to-right");
        });
    }

    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private String getYesterday(){
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        return dtf.format(yesterday.getTime());
    }
}