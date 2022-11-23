package com.example.organizzeleo.navigation.ui.vender;

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

public class VenderFragment extends Fragment {


    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private TextView checarMoedasTexto;
    private TextView textBTC, textETH, textSLP, textSOL, textMPL, textDOGE, textAXS, textAPE, textADA, textLTC, textSHIB, textTRB, textXRP, textXTZ, textUSDC;
    private TextView textoNomeVender, textoSaldoVender;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Spinner spinnerVendas;
    private TextView textoRespostaSpinner;
    private ArrayList<ItemCustomSpinner> customList;
    private EditText campoQuantidade;
    private TextView textoQuantidadeMoedasCarteira;
    private TextView valorRecebidoText;
    private Button botaoVender;
    private ImageView botaoFecharDialog;
    private TextView textoVenderTudo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vender, container, false);
        textoNomeVender = view.findViewById(R.id.textoNomeVender);
        textoSaldoVender = view.findViewById(R.id.textoSaldoVender);
        spinnerVendas = view.findViewById(R.id.spinnerVendas);
        campoQuantidade = view.findViewById(R.id.campoEditQuantidadeVendas);
        textoQuantidadeMoedasCarteira = view.findViewById(R.id.textQuantidadeMoedaCarteira);
        valorRecebidoText = view.findViewById(R.id.valorRecebidoVenda);
        botaoVender = view.findViewById(R.id.botaoVender);
        textoVenderTudo = view.findViewById(R.id.venderTudoText);
        //botaoFecharDialog = view.findViewById(R.id.botaoFecharDialog);


        final String email = autenticacao.getCurrentUser().getEmail();
        final String idUsuario = Base64Custom.codificarBase64(email);

        DatabaseReference usuarioref = firebaseRef.child("usuarios").child(idUsuario);
        usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);

                textoNomeVender.setText("Olá, " + usuario.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference carteiraref = firebaseRef.child("Carteira").child(idUsuario);
        carteiraref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Carteira carteira = snapshot.getValue(Carteira.class);

                textoSaldoVender.setText("R$" + carteira.getSaldo().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        checarMoedasTexto = view.findViewById(R.id.checarMoedasCarteira);
        checarMoedasTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.moedas_na_carteira, null);

                botaoFecharDialog = view.findViewById(R.id.botaoFecharDialog);
                textBTC = view.findViewById(R.id.BTCV);
                textETH = view.findViewById(R.id.ETHV);
                textSLP = view.findViewById(R.id.SLPV);
                textSOL = view.findViewById(R.id.SOLV);
                textMPL = view.findViewById(R.id.MPLV);
                textDOGE = view.findViewById(R.id.DOGEV);
                textAXS = view.findViewById(R.id.AXSV);
                textAPE = view.findViewById(R.id.APEV);
                textLTC = view.findViewById(R.id.LTCV);
                textSHIB = view.findViewById(R.id.SHIBV);
                textTRB = view.findViewById(R.id.TRBV);
                textXRP = view.findViewById(R.id.XRPV);
                textXTZ = view.findViewById(R.id.XTZV);
                textUSDC = view.findViewById(R.id.USDCV);
                textADA = view.findViewById(R.id.ADAV);


                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                builder.setView(view);
                builder.show();
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

                botaoFecharDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

            }
        });


        textoRespostaSpinner = view.findViewById(R.id.textValorAtualVendas);
        customList = getCustomList();
        CustomAdapter adapter = new CustomAdapter(getContext(), customList);
        spinnerVendas.setAdapter(adapter);
        spinnerVendas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ItemCustomSpinner item = (ItemCustomSpinner) parent.getSelectedItem();
                final String coin = item.getSpinnerString();

                if (position == 0) {
                    textoRespostaSpinner.setText("Escolha uma moeda para verificar seu preço");
                    textoQuantidadeMoedasCarteira.setText("...");
                } else {
                    campoQuantidade.setText(null);

                    try {
                        final Resposta retorno = new HttpService(coin).execute().get();


                        final Double retornoDouble = Double.parseDouble(retorno.toString());
                        final DecimalFormat formato = new DecimalFormat("0.####");
                        final String resultadoFormatado = formato.format(retornoDouble);


                        DatabaseReference carteiraref = firebaseRef.child("Carteira").child(idUsuario);
                        carteiraref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final Carteira carteira = snapshot.getValue(Carteira.class);


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
                                final String DOGEformatado = formato.format(carteira.getDOGE());
                                String AXSformatado = formato.format(carteira.getAXS());
                                String APEformatado = formato.format(carteira.getAPE());
                                String ADAformatado = formato.format(carteira.getADA());
                                String LTCformatado = formato.format(carteira.getLTC());
                                String SHIBformatado = formato.format(carteira.getSHIB());
                                String TRBformatado = formato.format(carteira.getTRB());
                                String XRPformatado = formato.format(carteira.getXRP());
                                String XTZformatado = formato.format(carteira.getXTZ());
                                String USDCformatado = formato.format(carteira.getUSDC());


                                switch (position) {

                                    case 1:
                                        textoRespostaSpinner.setText("Bitcoin\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + BTCformatado + " Bitcoin na carteira");
                                        break;
                                    case 2:
                                        textoRespostaSpinner.setText("Ethereum\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + ETHformatado + " Ethereum na carteira");
                                        break;
                                    case 3:
                                        textoRespostaSpinner.setText("Smooth Love Potion\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + SLPformatado + " SLP na carteira");
                                        break;
                                    case 4:
                                        textoRespostaSpinner.setText("Solana Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + SOLformatado + " Solana na carteira");
                                        break;
                                    case 5:
                                        textoRespostaSpinner.setText("Maple\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + MPLformatado + " Maple na carteira");
                                        break;
                                    case 6:
                                        textoRespostaSpinner.setText("DOGE \nPreço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + DOGEformatado + " DOGE na carteira");
                                        break;
                                    case 7:
                                        textoRespostaSpinner.setText("Axie Infinity\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + AXSformatado + " Axie Infinity na carteira");
                                        break;
                                    case 8:
                                        textoRespostaSpinner.setText("ApeCoin\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + APEformatado + " ApeCoin na carteira");
                                        break;
                                    case 9:
                                        textoRespostaSpinner.setText("Cardano\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + ADAformatado + " Cardano na carteira");
                                        break;
                                    case 10:
                                        textoRespostaSpinner.setText("Litecoin\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + LTCformatado + " Litecoin na carteira");
                                        break;
                                    case 11:
                                        textoRespostaSpinner.setText("Shiba Inu\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + SHIBformatado + " Shiba inu na carteira");
                                        break;
                                    case 12:
                                        textoRespostaSpinner.setText("Tellor\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + TRBformatado + " Tellor na carteira");
                                        break;
                                    case 13:
                                        textoRespostaSpinner.setText("XRP\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + XRPformatado + " XRP na carteira");
                                        break;
                                    case 14:
                                        textoRespostaSpinner.setText("Tezos\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + XTZformatado + " Tezos na carteira");
                                        break;
                                    case 15:
                                        textoRespostaSpinner.setText("USD Coin\n Preço Atual: R$" + resultadoFormatado);
                                        textoQuantidadeMoedasCarteira.setText("Atualmente você tem: \n" + USDCformatado + " USD Coin na carteira");
                                        break;

                                }


                                campoQuantidade.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                                        if (campoQuantidade.getText().toString().equals("")) {
                                            valorRecebidoText.setText("Você receberá R$: ...");

                                        } else {
                                            Double quantidadeVendida = Double.parseDouble(campoQuantidade.getText().toString());


                                            final DecimalFormat formato = new DecimalFormat("0.####");
                                            String resultadoFormatado2 = formato.format(quantidadeVendida * retornoDouble);

                                            //Double valorRecebido = (quantidadeVendida* retornoDouble);
                                            valorRecebidoText.setText("Você receberá R$: " + resultadoFormatado2);


                                        }


                                        botaoVender.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                if (position == 0) {
                                                    Toast.makeText(getActivity(), "Selecione uma moeda primeiro", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    if (campoQuantidade.getText().toString().equals("") || campoQuantidade.getText().toString() == null) {

                                                        Toast.makeText(getActivity(), "Digite uma quantia primeiro", Toast.LENGTH_SHORT).show();

                                                    } else {


                                                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                                        dialog.setTitle("Você deseja mesmo vender?");
                                                        dialog.setMessage("O valor da venda será voltado para o seu saldo com a cotação atual da moeda");

                                                        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                Double quantidadeVendida = Double.parseDouble(campoQuantidade.getText().toString());
                                                                DecimalFormat formato = new DecimalFormat("0.###");

                                                                Double saldoNovo;
                                                                String saldoNovoStringFormatado;
                                                                switch (position) {


                                                                    case 1:
                                                                        if (quantidadeVendida <= carteira.getBTC()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setBTC(carteira.getBTC() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");

                                                                        } else if (quantidadeVendida > carteira.getBTC()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem  Bitcoin(BTC) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 2:
                                                                        if (quantidadeVendida <= carteira.getETH()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setETH(carteira.getETH() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getETH()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Ethereum(ETH) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 3:
                                                                        if (quantidadeVendida <= carteira.getSLP()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setSLP(carteira.getSLP() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getSLP()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Smooth Love Potion(SLP) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 4:
                                                                        if (quantidadeVendida <= carteira.getSOL()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setSOL(carteira.getSOL() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getSOL()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Solana(SOL) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 5:
                                                                        if (quantidadeVendida <= carteira.getMPL()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setMPL(carteira.getMPL() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getMPL()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Maple(MPL) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 6:
                                                                        if (quantidadeVendida <= carteira.getDOGE()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setDOGE(carteira.getDOGE() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getDOGE()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Doge Coins(DOGE) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 7:
                                                                        if (quantidadeVendida <= carteira.getAXS()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setAXS(carteira.getAXS() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getAXS()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Axie Infinty Coins(AXS) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 8:
                                                                        if (quantidadeVendida <= carteira.getAPE()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setAPE(carteira.getAPE() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getAPE()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Ape Coins(APE) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 9:
                                                                        if (quantidadeVendida <= carteira.getADA()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setADA(carteira.getADA() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getADA()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Cardano(ADA) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 10:
                                                                        if (quantidadeVendida <= carteira.getLTC()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setLTC(carteira.getLTC() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getLTC()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Litecoins(LTC) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 11:
                                                                        if (quantidadeVendida <= carteira.getSHIB()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setSHIB(carteira.getSHIB() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getSHIB()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Shiba Inu o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 12:
                                                                        if (quantidadeVendida <= carteira.getTRB()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setTRB(carteira.getTRB() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getTRB()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Tellor(TRB) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 13:
                                                                        if (quantidadeVendida <= carteira.getXRP()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setXRP(carteira.getXRP() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getXRP()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem XRP o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 14:
                                                                        if (quantidadeVendida <= carteira.getXTZ()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setXTZ(carteira.getXTZ() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getXTZ()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem Tezos(XTZ) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;
                                                                    case 15:
                                                                        if (quantidadeVendida <= carteira.getUSDC()) {
                                                                            saldoNovo = quantidadeVendida * retornoDouble;
                                                                            carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                            carteira.setUSDC(carteira.getUSDC() - quantidadeVendida);
                                                                            BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                            carteira.setSaldo(bd.doubleValue());
                                                                            carteira.Salvar();
                                                                            campoQuantidade.setText("");
                                                                        } else if (quantidadeVendida > carteira.getUSDC()) {
                                                                            Toast.makeText(getActivity(), "Voce nao tem USD Coins(USDC) o suficiente", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        break;


                                                                }
                                                            }

                                                        });


                                                        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                        dialog.create().show();
                                                    }


                                                }
                                            }
                                        });


                                    }


                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });


                                textoVenderTudo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (position == 0) {
                                            Toast.makeText(getActivity(), "Selecione uma moeda primeiro", Toast.LENGTH_SHORT).show();
                                        } else {

                                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                            dialog.setTitle("Você deseja mesmo vender tudo desta moeda?");
                                            dialog.setMessage("O valor da venda será voltado para o seu saldo com a cotação atual da moeda");

                                            dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                    Double saldoNovo;
                                                    switch (position) {


                                                        case 1:
                                                            if (carteira.getBTC() > 0) {
                                                                saldoNovo = carteira.getBTC() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setBTC(carteira.getBTC() - carteira.getBTC());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 2:
                                                            if (carteira.getETH() > 0) {
                                                                saldoNovo = carteira.getETH() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setETH(carteira.getETH() - carteira.getETH());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 3:
                                                            if (carteira.getSLP() > 0) {
                                                                saldoNovo = carteira.getSLP() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setSLP(carteira.getSLP() - carteira.getSLP());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 4:
                                                            if (carteira.getSOL() > 0) {
                                                                saldoNovo = carteira.getSOL() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setSOL(carteira.getSOL() - carteira.getSOL());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 5:
                                                            if (carteira.getMPL() > 0) {
                                                                saldoNovo = carteira.getMPL() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setMPL(carteira.getMPL() - carteira.getMPL());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 6:
                                                            if (carteira.getDOGE() > 0) {
                                                                saldoNovo = carteira.getDOGE() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setDOGE(carteira.getDOGE() - carteira.getDOGE());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 7:
                                                            if (carteira.getAXS() > 0) {
                                                                saldoNovo = carteira.getAXS() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setAXS(carteira.getAXS() - carteira.getAXS());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 8:
                                                            if (carteira.getAPE() > 0) {
                                                                saldoNovo = carteira.getAPE() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setAPE(carteira.getAPE() - carteira.getAPE());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 9:
                                                            if (carteira.getADA() > 0) {
                                                                saldoNovo = carteira.getADA() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setADA(carteira.getADA() - carteira.getADA());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 10:
                                                            if (carteira.getLTC() > 0) {
                                                                saldoNovo = carteira.getLTC() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setLTC(carteira.getLTC() - carteira.getLTC());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 11:
                                                            if (carteira.getSHIB() > 0) {
                                                                saldoNovo = carteira.getSHIB() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setSHIB(carteira.getSHIB() - carteira.getSHIB());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 12:
                                                            if (carteira.getTRB() > 0) {
                                                                saldoNovo = carteira.getTRB() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setTRB(carteira.getTRB() - carteira.getTRB());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 13:
                                                            if (carteira.getXRP() > 0) {
                                                                saldoNovo = carteira.getXRP() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setXRP(carteira.getXRP() - carteira.getXRP());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 14:
                                                            if (carteira.getXTZ() > 0) {
                                                                saldoNovo = carteira.getXTZ() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setXTZ(carteira.getXTZ() - carteira.getXTZ());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;
                                                        case 15:
                                                            if (carteira.getUSDC() > 0) {
                                                                saldoNovo = carteira.getUSDC() * retornoDouble;
                                                                carteira.setSaldo(carteira.getSaldo() + saldoNovo);
                                                                carteira.setUSDC(carteira.getUSDC() - carteira.getUSDC());
                                                                BigDecimal bd = new BigDecimal(carteira.getSaldo()).setScale(3, RoundingMode.HALF_EVEN);
                                                                carteira.setSaldo(bd.doubleValue());
                                                                carteira.Salvar();
                                                                campoQuantidade.setText("");
                                                                Toast.makeText(getActivity(), "Vendido com sucesso, cheque seu novo saldo", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(getActivity(), "Você não tem nada dessa moeda ", Toast.LENGTH_SHORT).show();
                                                            }
                                                            break;


                                                    }
                                                }

                                            });


                                            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.create().show();


                                        }
                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

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


        return view;

    }


    private ArrayList<ItemCustomSpinner> getCustomList() {
        customList = new ArrayList<>();

        customList.add(new ItemCustomSpinner("Escolha uma moeda", R.drawable.bitocoin));
        customList.add(new ItemCustomSpinner("BTC", R.drawable.ic_btc));
        customList.add(new ItemCustomSpinner("ETH", R.drawable.ic_eth));
        customList.add(new ItemCustomSpinner("SLP", R.drawable.ic_slp));
        customList.add(new ItemCustomSpinner("SOL", R.drawable.ic_sol));
        customList.add(new ItemCustomSpinner("MPL", R.drawable.ic_maple));
        customList.add(new ItemCustomSpinner("DOGE", R.drawable.ic_doge2));
        customList.add(new ItemCustomSpinner("AXS", R.drawable.ic_axs2));
        customList.add(new ItemCustomSpinner("APE", R.drawable.ic_ape2));
        customList.add(new ItemCustomSpinner("ADA", R.drawable.ic_ada));
        customList.add(new ItemCustomSpinner("LTC", R.drawable.ic_ltc));
        customList.add(new ItemCustomSpinner("SHIB", R.drawable.ic_shib));
        customList.add(new ItemCustomSpinner("TRB", R.drawable.ic_trb));
        customList.add(new ItemCustomSpinner("XRP", R.drawable.ic_xrp));
        customList.add(new ItemCustomSpinner("XTZ", R.drawable.ic_xtz));
        customList.add(new ItemCustomSpinner("USDC", R.drawable.ic_usdc));
        return customList;


    }


}





