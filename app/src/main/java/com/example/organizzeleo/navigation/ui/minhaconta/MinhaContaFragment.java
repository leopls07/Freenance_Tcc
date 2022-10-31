package com.example.organizzeleo.navigation.ui.minhaconta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.organizzeleo.R;
import com.example.organizzeleo.activity.LoginActivity;
import com.example.organizzeleo.activity.MainActivity;
import com.example.organizzeleo.activity.PreLoginActivity;
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.Base64Custom;
import com.example.organizzeleo.helper.NetworkChangeListener;
import com.example.organizzeleo.model.Usuario;
import com.example.organizzeleo.navigation.ui.minhacarteira.MinhaCarteiraFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FederatedAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MinhaContaFragment extends Fragment {

    private TextView textoNome, textoEmail;
    private Button botaoDeslogar;
    private Button botaoExcluir;
    private Button botaoAltSenha, botaoAltNome;
    EditText editTextAlt;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private FirebaseAuth deslogar;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

    public MinhaContaFragment() {

    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        /*Intent intentSenha = new Intent();
        final String senha = intentSenha.getStringExtra("Nome");
        System.out.println(senha);*/


        final FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = autenticacao.getCurrentUser().getEmail();
        textoNome = view.findViewById(R.id.textoNome);
        textoEmail = view.findViewById(R.id.textEmail);
        textoEmail.setText(email);


        final String idUsuario = Base64Custom.codificarBase64(email);
        final DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);


        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);


                textoNome.setText(usuario.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        botaoDeslogar = view.findViewById(R.id.ButtonLogout);
        botaoDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Deseja mesmo encerrar sua sessão?");
                dialog.setMessage("Você terá de logar novamente");
                dialog.setPositiveButton("Deslogar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deslogar = ConfiguracaoFirebase.getFirebaseAutenticacao();
                        deslogar.signOut();
                        Toast.makeText(getContext(), "Deslogado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        botaoExcluir = view.findViewById(R.id.ButtonExcluir);
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Deseja prosseguir com a ação?");
                dialog.setMessage("Se prosseguir com a ação sua conta será excluida e você " +
                        "deverá criar ou entrar com outra conta.");
                dialog.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                      /*  AuthCredential credential = EmailAuthProvider.getCredential(autenticacao.getCurrentUser().getEmail());
                        autenticacao.getCurrentUser().reauthenticate(credential).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Não foi possivel autenticar, faça lofin novamente", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                            }
                        });
*/

                        autenticacao.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Conta Deletada",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "Por favor, encerre a sessão e logue novamente, depois exclua sua conta",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        botaoAltSenha = view.findViewById(R.id.botaoAltSenha);
        botaoAltSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_senha, null);
                 final EditText passwordET = view.findViewById(R.id.passwordEt);
                final  EditText newPasswordET = view.findViewById(R.id.newPasswordEt);
                Button updatePasswordBtn = view.findViewById(R.id.updatePasswordBtn);

                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                builder.setView(view);

                builder.show();

                updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword = passwordET.getText().toString().trim();
                        final String newPassword = newPasswordET.getText().toString().trim();
                        if (TextUtils.isEmpty(oldPassword)) {
                            Toast.makeText(getActivity(),
                                    "Insira a senha atual!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (newPassword.length() < 6) {
                            Toast.makeText(getActivity(),
                                    "Nova senha deverá ter no mínimo 6 caracteres",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getActivity(),
                                                        "Senha alterada com sucesso!",
                                                        Toast.LENGTH_SHORT).show();
                                                builder.dismiss();
                                            }else{Toast.makeText(getContext(),"Erro, Senha não alterada",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(getContext(),"Senha incorreta, tente novamente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                               /* .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        user.updatePassword(newPassword)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getActivity(),
                                                                "Senha alterada com sucesso!",
                                                                Toast.LENGTH_SHORT).show();
                                                        builder.dismiss();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
*/

                    }
                });

            }
        });


        botaoAltNome = view.findViewById(R.id.botaoAltNome);
        botaoAltNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference usuarioRefAlterarNome = firebaseRef.child("usuarios").child(idUsuario);
                final View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_nome,null);
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                builder.show();

                usuarioRefAlterarNome.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        final Usuario usuario = snapshot.getValue(Usuario.class);
                        Button botaoTrocaNome = view.findViewById(R.id.updateNameBtn);
                        editTextAlt = view.findViewById(R.id.newNameAlt);


                        botaoTrocaNome.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (editTextAlt.getText().toString().length() <= 4) {
                                    Toast.makeText(getActivity(), "Seu nome deve ter mais de 4 caractéres", Toast.LENGTH_SHORT).show();
                                } else {
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setTitle("Alterar Nome");
                                    alert.setMessage("Deseja mesmo alterar seu nome?");
                                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            final String novoNome = editTextAlt.getText().toString();
                                            usuario.setNome(novoNome);
                                            Toast.makeText(getActivity(),"Nome alterado com sucesso",Toast.LENGTH_SHORT).show();
                                            usuario.salvarNome();
                                            builder.dismiss();



                                        }
                                    });
                                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.create().show();
                                }
                            }
                        });






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        return view;

    }

  

    private void updatePassword(final String oldPassword, final String newPassword) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(),
                                                "Senha alterada com sucesso!",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Erro ao Alterar senha!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

    }
}

