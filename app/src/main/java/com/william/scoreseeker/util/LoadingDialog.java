package com.william.scoreseeker.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.william.scoreseeker.R;

public class LoadingDialog {
   private final Activity activity;
   private AlertDialog dialog;

   public LoadingDialog(Activity myActivity) {
      this.activity = myActivity;
   }
   public void startLoading() {
      AlertDialog.Builder builder = new AlertDialog.Builder(activity);
      LayoutInflater inflater = activity.getLayoutInflater();
      builder.setView(inflater.inflate(R.layout.dialog, null));
      builder.setCancelable(false);
      dialog = builder.create();
      dialog.show();
   }
   public void endDialog() {
      dialog.dismiss();
   }
}
