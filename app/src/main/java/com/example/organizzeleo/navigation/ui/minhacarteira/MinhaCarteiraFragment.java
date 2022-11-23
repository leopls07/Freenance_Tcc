package com.example.organizzeleo.navigation.ui.minhacarteira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.Base64Custom;
import com.example.organizzeleo.model.Carteira;
import com.example.organizzeleo.model.Usuario;
import com.example.organizzeleo.navigation.ui.investir.InvestirFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;


public class MinhaCarteiraFragment extends Fragment {


    private TextView textBTC, textETH, textSLP, textSOL, textMPL, textDOGE, textAXS, textAPE, textADA, textLTC, textSHIB, textTRB, textXRP, textXTZ, textUSDC;
    private EditText campoSaldo;
    private TextView textoSaldo, textoNome;
    private Button botaoSalvar;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference firebaseRef2 = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mostrarValores();

        textBTC = view.findViewById(R.id.BTC);
        textETH = view.findViewById(R.id.ETH);
        textSLP = view.findViewById(R.id.SLP);
        textSOL = view.findViewById(R.id.SOL);
        textMPL = view.findViewById(R.id.MPL);
        textDOGE = view.findViewById(R.id.DOGE);
        textAXS = view.findViewById(R.id.AXS);
        textAPE = view.findViewById(R.id.APE);
        textLTC = view.findViewById(R.id.LTC);
        textSHIB = view.findViewById(R.id.SHIB);
        textTRB = view.findViewById(R.id.TRB);
        textXRP = view.findViewById(R.id.XRP);
        textXTZ = view.findViewById(R.id.XTZ);
        textUSDC = view.findViewById(R.id.USDC);
        textADA = view.findViewById(R.id.ADA);

        textoNome = view.findViewById(R.id.textNomeMinhaCarteira);


        textBTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = autenticacao.getCurrentUser().getEmail();

        String idUsuario = Base64Custom.codificarBase64(email);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuarioBD = snapshot.getValue(Usuario.class);


                textoNome.setText("Olá, " + usuarioBD.getNome());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        campoSaldo = view.findViewById(R.id.campoSaldo);
        textoSaldo = view.findViewById(R.id.textoSaldo);
        botaoSalvar = view.findViewById(R.id.botaoSalvarSaldo);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Deseja alterar seu saldo?");
                dialog.setMessage("Saldo atual será substituido");
                dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String valorSaldo = campoSaldo.getText().toString();

                        if (valorSaldo.equals("") || valorSaldo.isEmpty()) {
                            Toast.makeText(getContext(), "Digite um Saldo Primeiro", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                            String email = autenticacao.getCurrentUser().getEmail();

                            String idUsuario = Base64Custom.codificarBase64(email);

                            DatabaseReference usuarioRef2 = firebaseRef.child("Carteira").child(idUsuario);

                            usuarioRef2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Carteira carteira = snapshot.getValue(Carteira.class);


                                    textoSaldo.setText("R$" + carteira.getSaldo().toString());
                                    if (campoSaldo.getText().toString().equals("")) {
                                    } else {
                                        carteira.setSaldo(Double.parseDouble(campoSaldo.getText().toString()));
                                        carteira.Salvar();
                                        campoSaldo.setText("");
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //textoSaldo.setText("R$"+carteira.getSaldo());
                            Toast.makeText(getContext(), "Saldo alterado com sucesso", Toast.LENGTH_SHORT).show();


                        }


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


        DatabaseReference usuarioRef2 = firebaseRef.child("Carteira").child(idUsuario);

        usuarioRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Carteira carteira = snapshot.getValue(Carteira.class);


                textoSaldo.setText("R$" + carteira.getSaldo().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


    }

    /*public void salvarSaldo() {
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = autenticacao.getCurrentUser().getEmail();

        String idUsuario = Base64Custom.codificarBase64(email);

        DatabaseReference usuarioRef2 = firebaseRef.child("Carteira").child(idUsuario);

        usuarioRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Carteira carteira = snapshot.getValue(Carteira.class);


                // carteira.setSaldo(0.0);

                textoSaldo.setText("R$" + carteira.getSaldo().toString());

                carteira.setSaldo(Double.parseDouble(campoSaldo.getText().toString()));
                carteira.Salvar();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
*/

    public void mostrarValores() {

        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = autenticacao.getCurrentUser().getEmail();

        String idUsuario = Base64Custom.codificarBase64(email);

        DatabaseReference usuarioRef2 = firebaseRef.child("Carteira").child(idUsuario);

        usuarioRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Carteira carteira = snapshot.getValue(Carteira.class);

                final DecimalFormat formato = new DecimalFormat("0.###");

                carteira.setSaldo(carteira.getSaldo());

                carteira.setETH(carteira.getETH());
                carteira.setBTC(carteira.getBTC());
                carteira.setADA(carteira.getADA());
                carteira.setAPE(carteira.getAPE());
                carteira.setAXS(carteira.getAXS());
                carteira.setDOGE(carteira.getDOGE());
                carteira.setLTC(carteira.getLTC());
                carteira.setMPL(carteira.getMPL());
                carteira.setSHIB(carteira.getSHIB());
                carteira.setSLP(carteira.getSLP());
                carteira.setSOL(carteira.getSOL());
                carteira.setTRB(carteira.getTRB());
                carteira.setUSDC(carteira.getUSDC());
                carteira.setXRP(carteira.getXRP());
                carteira.setXTZ(carteira.getXTZ());

                String BTCformatado = formato.format(carteira.getBTC());
                String ETHformatado = formato.format(carteira.getETH());
                String SLPformatado = formato.format(carteira.getSLP());
                String SOLformatado = formato.format(carteira.getSOL());
                String MPLformatado = formato.format(carteira.getMPL());
                String DOGEformatado = formato.format(carteira.getDOGE());
                String AXSformatado = formato.format(carteira.getAXS());
                String APEformatado = formato.format(carteira.getAPE());
                String ADAformatado = formato.format(carteira.getADA());
                String LTCformatado = formato.format(carteira.getLTC());
                String SHIBformatado = formato.format(carteira.getSHIB());
                String TRBformatado = formato.format(carteira.getTRB());
                String XRPformatado = formato.format(carteira.getXRP());
                String XTZformatado = formato.format(carteira.getXTZ());
                String USDCformatado = formato.format(carteira.getUSDC());


                textBTC.setText("BTC: " + BTCformatado);
                textETH.setText("ETH: " + ETHformatado);
                textSLP.setText("SLP: " + SLPformatado);
                textSOL.setText("SOL: " + SOLformatado);
                textMPL.setText("MPL: " + MPLformatado);
                textDOGE.setText("DOGE: " + DOGEformatado);
                textAXS.setText("AXS: " + AXSformatado);
                textAPE.setText("APE: " + APEformatado);
                textADA.setText("ADA: " + ADAformatado);
                textLTC.setText("LTC: " + LTCformatado);
                textSHIB.setText("SHIB: " + SHIBformatado);
                textTRB.setText("TRB: " + TRBformatado);
                textXRP.setText("XRP: " + XRPformatado);
                textXTZ.setText("XTZ: " + XTZformatado);
                textUSDC.setText("USDC: " + USDCformatado);

                carteira.Salvar();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}