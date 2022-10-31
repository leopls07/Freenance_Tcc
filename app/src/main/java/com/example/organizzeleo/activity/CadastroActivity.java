package com.example.organizzeleo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzeleo.R;
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.Base64Custom;
import com.example.organizzeleo.helper.NetworkChangeListener;
import com.example.organizzeleo.model.Carteira;
import com.example.organizzeleo.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome,campoEmail,campoSenha;
    private Button buttonCadastro;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    Animation animFadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    campoNome = findViewById(R.id.editNome);
    campoEmail = findViewById(R.id.editEmail);
    campoSenha = findViewById(R.id.editSenha);
    buttonCadastro = findViewById(R.id.buttonCadastrar);

        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);



        buttonCadastro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            buttonCadastro.startAnimation(animFadein);

            String textoNome = campoNome.getText().toString();
            String textoSenha = campoSenha.getText().toString();
            String textoEmail = campoEmail.getText().toString();

            if( !textoNome.isEmpty()){
                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){


                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setNome(textoNome);
                        usuario.setSenha(textoSenha);
                        cadastrarUsuario();

                    }else{
                        Toast.makeText(
                                CadastroActivity.this,
                                "Preencha os campos primeiro",
                                Toast.LENGTH_SHORT
                        ).show();
                    }



                }else{
                    Toast.makeText(
                            CadastroActivity.this,
                            "Preencha os campos primeiro",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }else{
             Toast.makeText(
                     CadastroActivity.this,
                     "Preencha os campos primeiro",
                     Toast.LENGTH_SHORT
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



    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();


                    Toast.makeText(getApplicationContext(),"Cadastrado com sucesso, vá para login",Toast.LENGTH_SHORT).show();


                    Carteira carteira = new Carteira();

                    

                    if(carteira.getSaldo() == null && carteira.getBTC() == null && carteira.getETH() == null ){
                        carteira.setSaldo(0.0);
                        carteira.setETH(0.0);
                        carteira.setBTC(0.0);
                        carteira.setADA(0.0);
                        carteira.setAPE(0.0);
                        carteira.setAXS(0.0);
                        carteira.setDOGE(0.0);
                        carteira.setLTC(0.0);
                        carteira.setMPL(0.0);
                        carteira.setSHIB(0.0);
                        carteira.setSLP(0.0);
                        carteira.setSOL(0.0);
                        carteira.setTRB(0.0);
                        carteira.setUSDC(0.0);
                        carteira.setXRP(0.0);
                        carteira.setXTZ(0.0);
                        carteira.Salvar();
                    }else {
                        Toast.makeText(getApplicationContext(), "oie", Toast.LENGTH_SHORT).show();
                    }

                    finish();

                }else{
                        String exception;
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        exception ="Digite uma senha mais forte!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        exception = "Email inválido, cheque novamente";
                    }catch (FirebaseAuthUserCollisionException e){
                        exception = "Email já cadastrado, vá para o login";
                    }catch (Exception e){
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(
                            CadastroActivity.this,
                            exception,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

}
