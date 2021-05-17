package com.william.scoreseeker;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
   private Activity activity;
   private AlertDialog dialog;

   LoadingDialog(Activity myActivity) {
      this.activity = myActivity;
   }
   void startLoading() {
      AlertDialog.Builder builder = new AlertDialog.Builder(activity);
      LayoutInflater inflater = activity.getLayoutInflater();
      builder.setView(inflater.inflate(R.layout.dialog, null));
      builder.setCancelable(true);
      dialog = builder.create();
      dialog.show();
   }
   void endDialog() {
      dialog.dismiss();
   }
}
