package com.william.scoreseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.william.scoreseeker.util.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        getKlasmenData();

    }

    private void getKlasmenData() {
        ImageView[] logo = new ImageView[5];
        logo[0] = findViewById(R.id.logoKlas1);
        logo[1] = findViewById(R.id.logoKlas2);
        logo[2] = findViewById(R.id.logoKlas3);
        logo[3] = findViewById(R.id.logoKlas4);
        logo[4] = findViewById(R.id.logoKlas5);
        String url = "https://api.football-data.org/v2/competitions/2021/standings";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray teamData = response.getJSONArray("standings").getJSONObject(0).getJSONArray("table");
                for (int i = 0; i < 5; i++) {
                    Uri uri = Uri.parse(teamData.getJSONObject(i).getJSONObject("team").getString("crestUrl"));
                    Log.d("KlasmenData", "getKlasmenData: " + teamData.getJSONObject(i).getJSONObject("team").getString("crestUrl"));
                    GlideToVectorYou.init().with(this).load(uri, logo[i]);
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