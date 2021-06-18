package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.william.scoreseeker.model.Klasmen;
import com.william.scoreseeker.model.Pertandingan;
import com.william.scoreseeker.util.CustomAdapter;
import com.william.scoreseeker.util.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class KlasmenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomAdapter recyclerViewAdapter;
    private final ArrayList<Klasmen> klasmenList = new ArrayList<>();
    private CardView card;
    private TextView namaLiga;
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
        String idKlasmen = getIntent().getStringExtra("id");
        card = findViewById(R.id.card1);
        namaLiga = findViewById(R.id.textView4);
        changeColor(idKlasmen);
        getKlasmenData(idKlasmen);
    }

    private void changeColor(String id) {
        switch (id) {
            case "2021":
                card.setCardBackgroundColor(getResources().getColor(R.color.premier));
                namaLiga.setText("Premier League");
                break;
            case "2014":
                card.setCardBackgroundColor(getResources().getColor(R.color.laliga));
                namaLiga.setText("La Liga Spanyol");
                break;
            case "2015":
                card.setCardBackgroundColor(getResources().getColor(R.color.ligue1));
                namaLiga.setText("Ligue 1 Perancis");
                break;
            case "2019":
                card.setCardBackgroundColor(getResources().getColor(R.color.seriea));
                namaLiga.setText("Serie A Itali");
                break;
        }
    }

    private void getKlasmenData(String id) {
        ImageView[] logos = new ImageView[20];
        TextView[] detail = new TextView[20];
        TextView[] win = new TextView[20];
        TextView[] draw = new TextView[20];
        TextView[] lose = new TextView[20];
        TextView[] pts = new TextView[20];

        String url = "https://api.football-data.org/v2/competitions/"+ id +"/standings";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray teamData = response.getJSONArray("standings").getJSONObject(0).getJSONArray("table");
                for (int i = 0; i < teamData.length(); i++) {
                    logos[i] = (ImageView) findViewById(getResources().getIdentifier( MessageFormat.format("logoKlas{0}", i), "id", getPackageName()));
                    detail[i] = (TextView) findViewById(getResources().getIdentifier( MessageFormat.format("mp{0}", i), "id", getPackageName()));
                    win[i] = (TextView) findViewById(getResources().getIdentifier( MessageFormat.format("win{0}", i), "id", getPackageName()));
                    draw[i] = (TextView) findViewById(getResources().getIdentifier( MessageFormat.format("draw{0}", i), "id", getPackageName()));
                    lose[i] = (TextView) findViewById(getResources().getIdentifier( MessageFormat.format("lose{0}", i), "id", getPackageName()));
                    pts[i] = (TextView) findViewById(getResources().getIdentifier( MessageFormat.format("pts{0}", i), "id", getPackageName()));


                    detail[i].setText(String.valueOf(teamData.getJSONObject(i).get("playedGames")));
                    win[i].setText(String.valueOf(teamData.getJSONObject(i).get("won")));
                    draw[i].setText(String.valueOf(teamData.getJSONObject(i).get("draw")));
                    lose[i].setText(String.valueOf(teamData.getJSONObject(i).get("lost")));
                    pts[i].setText(String.valueOf(teamData.getJSONObject(i).get("points")));

                    Uri uri = Uri.parse(teamData.getJSONObject(i).getJSONObject("team").getString("crestUrl"));
                    GlideToVectorYou.init().with(this).load(uri, logos[i]);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

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