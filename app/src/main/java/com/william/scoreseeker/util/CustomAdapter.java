package com.william.scoreseeker.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.william.scoreseeker.R;
import com.william.scoreseeker.model.Pertandingan;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
   private Context context;
   private List<Pertandingan> matchList;
   private LayoutInflater inflater;

   public CustomAdapter(Context c, List<Pertandingan> matchList) {
      this.context = c;
      this.matchList = matchList;
   }

   // Create new views (invoked by the layout manager)
   @Override
   public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      // Create a new view, which defines the UI of the list item
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
      viewHolder.skor2.setText(p.getSkorKandang());
   }

   @Override
   public int getItemCount() {
      return matchList.size();
   }


   public class ViewHolder extends RecyclerView.ViewHolder {
      public TextView tim1, tim2, skor1, skor2;

      public ViewHolder(View view, Context ctx) {
         super(view);
         context = ctx;
         tim1 = view.findViewById(R.id.tim1);
         tim2 = view.findViewById(R.id.tim2);
         skor1 = view.findViewById(R.id.skor1);
         skor2 = view.findViewById(R.id.skor2);
      }
   }
}
