package com.william.scoreseeker.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.william.scoreseeker.KlasmenActivity;
import com.william.scoreseeker.MainActivity;
import com.william.scoreseeker.R;
import com.william.scoreseeker.model.Pertandingan;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
   private Context context;
   private List<Pertandingan> matchList;
   private LayoutInflater inflater;

   public CustomAdapter(Context c, List<Pertandingan> matchList) {
      this.context = c;
      this.matchList = matchList;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      View view = LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.list_match, viewGroup, false);
      return new ViewHolder(view, context);
   }

   @Override
   public void onBindViewHolder(ViewHolder viewHolder, int position) {
      Pertandingan p = matchList.get(position);
      viewHolder.tim1.setText(p.getTimKandang());
      viewHolder.tim2.setText(p.getTimTandang());
      viewHolder.skor1.setText(p.getSkorKandang());
      viewHolder.skor2.setText(p.getSkorTandang());
      Uri uri1 = Uri.parse("https://crests.football-data.org/"+p.getIdKandang()+".svg");
      Uri uri2 = Uri.parse("https://crests.football-data.org/"+p.getIdTandang()+".svg");

      GlideToVectorYou.init().with(context).load(uri1,viewHolder.logo1);
      GlideToVectorYou.init().with(context).load(uri2,viewHolder.logo2);
   }

   @Override
   public int getItemCount() {
      return matchList.size();
   }


   public class ViewHolder extends RecyclerView.ViewHolder {
      private TextView tim1, tim2, skor1, skor2;
      private ImageView logo1, logo2;
      public ViewHolder(View view, Context ctx) {
         super(view);
         context = ctx;
         tim1 = view.findViewById(R.id.tim1);
         tim2 = view.findViewById(R.id.tim2);
         skor1 = view.findViewById(R.id.skor1);
         skor2 = view.findViewById(R.id.skor2);
         logo1 = view.findViewById(R.id.logo1);
         logo2 = view.findViewById(R.id.logo2);
      }
   }
   public void getLogo(ImageView view, String id) {
      String url = "https://api.football-data.org/v2/teams/" + id;
      JsonObjectRequest logoRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
         try {
            GlideToVectorYou.init().with(context).load(Uri.parse((String) response.get("crestUrl")),view);
         } catch (JSONException e) {
            e.printStackTrace();
         }
      }, error -> {
         
      }) {
         @Override
         public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("X-Auth-Token", context.getString(R.string.key));
            return headers;
         }
      };
      MyRequest.getInstance(context).addToRequestQueue(logoRequest);
   }
}
