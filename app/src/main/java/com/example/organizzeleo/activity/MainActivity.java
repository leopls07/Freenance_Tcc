package com.example.organizzeleo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzeleo.R;
import com.example.organizzeleo.activity.CadastroActivity;
import com.example.organizzeleo.activity.LoginActivity;
import com.example.organizzeleo.activity.PrincipalActivity;
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.NetworkChangeListener;
import com.example.organizzeleo.model.Carteira;
import com.example.organizzeleo.navigation.NavigationActivity;
import com.example.organizzeleo.navigation.ui.minhaconta.MinhaContaFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;
    private Animation animation;
   // Button botaoCadastro;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        /*Carteira carteira = new Carteira();

        if(carteira.getSaldo() == null){
            carteira.setSaldo(0.0);
            carteira.Salvar();
        }else{
            Toast.makeText(getApplicationContext(), "oie", Toast.LENGTH_SHORT).show();
        }*/





        //botaoCadastro = findViewById(R.id.botaoCadastro);
        animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);


        verificarUsuarioLogado();

        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorBackgroumd)
                .fragment(R.layout.slide_um)
                .build()

        );



        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorBackgroumd)
                .fragment(R.layout.slide_dois)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorBackgroumd)
                .fragment(R.layout.slide_tres)
                .build()
        );


        addSlide(new FragmentSlide.Builder()
                .background(R.color.startColorBackgroumd)
                .fragment(R.layout.pre_login)
                .canGoForward(false)
                .build()

        );



    }

    @Override
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

    public void btEntrar(View view){

        startActivity(new Intent(this, LoginActivity.class));

    }

    public void btCadastro(View view){


        Intent intentCadastro = new Intent(this, CadastroActivity.class);

        startActivity(intentCadastro);

    }


    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();
        if(autenticacao.getCurrentUser() != null){

            abrirTelaPrincipal();

        }
    }


    public void abrirTelaPrincipal(){

        startActivity(new Intent(this, NavigationActivity.class));


    }







}