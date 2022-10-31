package com.example.organizzeleo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.organizzeleo.R;
import com.example.organizzeleo.helper.NetworkChangeListener;

public class PreLoginActivity extends AppCompatActivity {

    private Button botaoCadastro;
    private Animation animation;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_login);

        botaoCadastro = findViewById(R.id.botaoCadastro);
        animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        botaoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                botaoCadastro.startAnimation(animation);
            }
        });




    }
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

}
