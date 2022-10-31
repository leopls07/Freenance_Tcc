package com.example.organizzeleo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeleo.R;
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.NetworkChangeListener;
import com.example.organizzeleo.model.Usuario;
import com.example.organizzeleo.navigation.NavigationActivity;
import com.example.organizzeleo.navigation.ui.minhaconta.MinhaContaFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {


        NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private FirebaseAuth autenticacao;

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;

    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_freenance);

        campoEmail= findViewById(R.id.username);
        campoSenha = findViewById(R.id.password);
        botaoEntrar = findViewById(R.id.buttonLogin);

        animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);




        //login = FirebaseAuth.getInstance();

        //login.signInWithEmailAndPassword(new Si)


        //Teste mostrar senha
        //checkSenha = findViewById(R.id.checkSenha);





                botaoEntrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        botaoEntrar.startAnimation(animation);



                        String textoSenha = campoSenha.getText().toString();
                        String textoEmail = campoEmail.getText().toString();

                        Intent intentSenha = new Intent(getApplicationContext(), MinhaContaFragment.class);
                        intentSenha.putExtra("Senha",textoSenha);


                            if (!textoEmail.isEmpty()){
                                if (!textoSenha.isEmpty()){


                                    usuario = new Usuario();
                                    usuario.setSenha( textoSenha);
                                    usuario.setEmail(textoEmail);
                                    validarLogin();


                                }else{
                                    Toast.makeText(
                                            LoginActivity.this,
                                            "Preencha a senha",
                                            Toast.LENGTH_LONG
                                    ).show();
                                }



                            }else{
                               Toast.makeText(LoginActivity.this,
                                       "Preencha o email",
                                       Toast.LENGTH_LONG
                               ).show();
                            }



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




    public void validarLogin(){

                    autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                    autenticacao.signInWithEmailAndPassword(usuario.getEmail(),
                            usuario.getSenha()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                abrirTelaPrincipal();
                                finish();

                            }else{

                                String exception;

                                try {
                                   throw  task.getException();
                                }catch
                                    (FirebaseAuthInvalidUserException e){
                                    exception = "Usuario não cadastrado";
                                }catch(FirebaseAuthInvalidCredentialsException e){
                                    exception = "Senha inválida";
                                }catch (Exception e){
                                    exception = "Dados Inválidos" + e.getMessage();
                                    e.printStackTrace();
                                }



                                Toast.makeText(LoginActivity.this,exception,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    }

                    public void abrirTelaPrincipal(){

                        startActivity(new Intent(this, NavigationActivity.class));
                        //finish();

                    }


    }

