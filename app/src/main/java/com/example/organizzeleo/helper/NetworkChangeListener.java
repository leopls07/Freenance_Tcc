package com.example.organizzeleo.helper;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.organizzeleo.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(!Common.isConnectedToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.alert_dialog_conexao, null);

            builder.setView(layout_dialog);

            AppCompatButton tentaNovamente = layout_dialog.findViewById(R.id.tentaNovamenteBtn);

            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            tentaNovamente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });

        }
    }
}
