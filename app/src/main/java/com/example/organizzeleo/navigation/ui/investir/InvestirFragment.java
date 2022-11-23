package com.example.organizzeleo.navigation.ui.investir;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.organizzeleo.API.HttpService;
import com.example.organizzeleo.API.Resposta;
import com.example.organizzeleo.R;
import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.Base64Custom;
import com.example.organizzeleo.helper.CustomAdapter;
import com.example.organizzeleo.helper.ItemCustomSpinner;
import com.example.organizzeleo.model.Carteira;
import com.example.organizzeleo.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class InvestirFragment extends Fragment {

    private Spinner spinnerCoin;
    private TextView textoRespostaSpinner, textoNome,textoSaldo, textoQuantidadeMoedas;
    private EditText campoQuantidade;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private Button botaoComprar;
    ArrayList<ItemCustomSpinner> customList;
    private ImageView easterEgg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        botaoComprar = root.findViewById(R.id.buttonComprar);
        textoNome = root.findViewById(R.id.textoNomeInvestir);
        textoSaldo = root.findViewById(R.id.textoSaldoInvestir);
        campoQuantidade = root.findViewById(R.id.editQuantidadeCompra);
        textoQuantidadeMoedas = root.findViewById(R.id.textQuantidadeMoeda);
        easterEgg = root.findViewById(R.id.easterEgg);

        easterEgg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getActivity(), "Developer Leonardo Ama sua Namorada Bianca Borges de Souza", Toast.LENGTH_LONG).show();

                return true;
            }
        });


        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = autenticacao.getCurrentUser().getEmail();

       final String idUsuario = Base64Custom.codificarBase64( email );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuarioBD = snapshot.getValue(Usuario.class);



                textoNome.setText("Olá, "+usuarioBD.getNome());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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






        spinnerCoin = root.findViewById(R.id.spinnerMoedas);
        textoRespostaSpinner = root.findViewById(R.id.textRespostaSpinner);
        customList = getCustomList();
        CustomAdapter adapter = new CustomAdapter(getContext(),customList);
        spinnerCoin.setAdapter(adapter);
        spinnerCoin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {


                ItemCustomSpinner item =(ItemCustomSpinner)parent.getSelectedItem();


               final String coin = item.getSpinnerString();



                if(position ==0){
                        textoRespostaSpinner.setText("");
                }
                else {
                    campoQuantidade.setText(null);
                    try {
                        Resposta retorno = new HttpService(coin).execute().get();

                        final Double retornoDouble = Double.parseDouble(retorno.toString());
                        final DecimalFormat formato = new DecimalFormat("0.####");
                        final String resultadoFormatado = formato.format(retornoDouble);


                        switch (position){

                            case 1:
                                textoRespostaSpinner.setText("Bitcoin\n Preço Atual: R$"+ resultadoFormatado);
                                break;
                            case 2:
                                textoRespostaSpinner.setText("Ethereum\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 3:
                                textoRespostaSpinner.setText("Smooth Love Potion\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 4:
                                textoRespostaSpinner.setText("Solana Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 5:
                                textoRespostaSpinner.setText("Maple\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 6:
                                textoRespostaSpinner.setText("DOGE \nPreço Atual: R$"+resultadoFormatado);
                                break;
                            case 7:
                                textoRespostaSpinner.setText("Axie Infinity\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 8:
                                textoRespostaSpinner.setText("ApeCoin\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 9:
                                textoRespostaSpinner.setText("Cardano\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 10:
                                textoRespostaSpinner.setText("Litecoin\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 11:
                                textoRespostaSpinner.setText("Shiba Inu\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 12:
                                textoRespostaSpinner.setText("Tellor\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 13:
                                textoRespostaSpinner.setText("XRP\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 14:
                                textoRespostaSpinner.setText("Tezos\n Preço Atual: R$"+resultadoFormatado);
                                break;
                            case 15:
                                textoRespostaSpinner.setText("USD Coin\n Preço Atual: R$"+resultadoFormatado);
                                break;






                        }











                       // textoRespostaSpinner.setText("Preço Atual: R$" + resultadoFormatado);
                        System.out.println(retorno.toString());

                        campoQuantidade.addTextChangedListener(new TextWatcher() {



                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String quantiaString = campoQuantidade.getText().toString();
                                if (quantiaString.equals("") || quantiaString.isEmpty()) {
                                    textoQuantidadeMoedas.setText("");

                                } else {





                                    final Double quantiaDouble = Double.parseDouble(quantiaString);
                                    final Double moedasCompradas = (quantiaDouble / retornoDouble);

                                    final String moedasCompradasFormatado = formato.format(moedasCompradas);
                                    textoQuantidadeMoedas.setText(moedasCompradasFormatado + "" + coin+"?");


                                    final DatabaseReference carteiraRef = firebaseRef.child("Carteira").child(idUsuario);

                                    carteiraRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                           final Carteira carteira = snapshot.getValue(Carteira.class);

                                            // carteira.setSaldo(0.0);

                                                botaoComprar.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (position == 0) {
                                                            Toast.makeText(getContext(), "Digite a Quantia Desejada", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            if (campoQuantidade.getText().toString().equals("") || campoQuantidade.getText().toString() == null){
                                                                Toast.makeText(getContext(), "Digite a Quantia Desejada", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                                                                alertDialog.setTitle("Deseja Efetuar a compra?");
                                                                alertDialog.setMessage("O valor será subtraido do seu saldo, deseja continuar?");
                                                                alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                        if (carteira.getSaldo() >= quantiaDouble) {
                                                                            if (position == 1) {
                                                                                carteira.setBTC(moedasCompradas + carteira.getBTC());
                                                                            }
                                                                            if (position == 2) {
                                                                                carteira.setETH(moedasCompradas + carteira.getETH());
                                                                            }
                                                                            if (position == 3) {
                                                                                carteira.setSLP(moedasCompradas + carteira.getSLP());
                                                                            }
                                                                            if (position == 4) {
                                                                                carteira.setSOL(moedasCompradas + carteira.getSOL());
                                                                            }
                                                                            if (position == 5) {
                                                                                carteira.setMPL(moedasCompradas + carteira.getMPL());
                                                                            }
                                                                            if (position == 6) {
                                                                                carteira.setDOGE(moedasCompradas + carteira.getDOGE());
                                                                            }
                                                                            if (position == 7) {
                                                                                carteira.setAXS(moedasCompradas + carteira.getAXS());
                                                                            }
                                                                            if (position == 8) {
                                                                                carteira.setAPE(moedasCompradas + carteira.getAPE());
                                                                            }
                                                                            if (position == 9) {
                                                                                carteira.setADA(moedasCompradas + carteira.getADA());
                                                                            }
                                                                            if (position == 10) {
                                                                                carteira.setLTC(moedasCompradas + carteira.getLTC());
                                                                            }
                                                                            if (position == 11) {
                                                                                carteira.setSHIB(moedasCompradas + carteira.getSHIB());
                                                                            }
                                                                            if (position == 12) {
                                                                                carteira.setTRB(moedasCompradas + carteira.getTRB());
                                                                            }
                                                                            if (position == 13) {
                                                                                carteira.setXRP(moedasCompradas + carteira.getXRP());
                                                                            }
                                                                            if (position == 14) {
                                                                                carteira.setXTZ(moedasCompradas + carteira.getXTZ());
                                                                            }
                                                                            if (position == 15) {
                                                                                carteira.setUSDC(moedasCompradas + carteira.getUSDC());
                                                                            }

                                                                            Double saldoNovo = carteira.getSaldo() - quantiaDouble;
                                                                            textoSaldo.setText("R$" + carteira.getSaldo().toString());
                                                                            carteira.setSaldo(saldoNovo);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();



                                                                            Toast.makeText(getContext(), "Compra efetuada com sucesso", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            Toast.makeText(getContext(), "Saldo Insuficiente vá para sua carteira", Toast.LENGTH_LONG).show();

                                                                        }


                                                                    }
                                                                });

                                                                alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                });
                                                                alertDialog.show();
                                                            }
                                                        }
                                                    }
                                                });


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });





                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });



                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        return root;

            }

            private ArrayList<ItemCustomSpinner>getCustomList(){
        customList = new ArrayList<>();

        customList.add(new ItemCustomSpinner("Selecione aqui",R.drawable.bitocoin));
        customList.add(new ItemCustomSpinner("BTC",R.drawable.ic_btc));
        customList.add(new ItemCustomSpinner("ETH",R.drawable.ic_eth));
        customList.add(new ItemCustomSpinner("SLP",R.drawable.ic_slp));
        customList.add(new ItemCustomSpinner("SOL",R.drawable.ic_sol));
        customList.add(new ItemCustomSpinner("MPL",R.drawable.ic_maple));
        customList.add(new ItemCustomSpinner("DOGE",R.drawable.ic_doge2));
        customList.add(new ItemCustomSpinner("AXS",R.drawable.ic_axs2));
        customList.add(new ItemCustomSpinner("APE",R.drawable.ic_ape2));
        customList.add(new ItemCustomSpinner("ADA",R.drawable.ic_ada));
        customList.add(new ItemCustomSpinner("LTC",R.drawable.ic_ltc));
        customList.add(new ItemCustomSpinner("SHIB",R.drawable.ic_shib));
        customList.add(new ItemCustomSpinner("TRB",R.drawable.ic_trb));
        customList.add(new ItemCustomSpinner("XRP",R.drawable.ic_xrp));
        customList.add(new ItemCustomSpinner("XTZ",R.drawable.ic_xtz));
        customList.add(new ItemCustomSpinner("USDC",R.drawable.ic_usdc));
        return customList;


    }


    }
