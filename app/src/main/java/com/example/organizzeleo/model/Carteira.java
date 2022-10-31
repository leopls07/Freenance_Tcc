package com.example.organizzeleo.model;

import com.example.organizzeleo.config.ConfiguracaoFirebase;
import com.example.organizzeleo.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Carteira {

    private Double BTC;
    private Double ETH;
    private Double SLP;
    private Double SOL;
    private Double MPL;
    private Double DOGE;
    private Double AXS;
    private Double APE;
    private Double ADA;
    private Double LTC;
    private Double SHIB ;
    private Double TRB;
    private Double XRP;
    private Double XTZ;
    private Double USDC;

    private Double saldo;


    public Carteira() {
    }

    public void Salvar(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        String email = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebaseDatabase.child("Carteira")
                .child(email)
                .setValue(this);


    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }


    public Double getBTC() {
        return BTC;
    }

    public void setBTC(Double BTC) {
        this.BTC = BTC;
    }

    public Double getETH() {
        return ETH;
    }

    public void setETH(Double ETH) {
        this.ETH = ETH;
    }


    public Double getSLP() {
        return SLP;
    }

    public void setSLP(Double SLP) {
        this.SLP = SLP;
    }

    public Double getSOL() {
        return SOL;
    }

    public void setSOL(Double SOL) {
        this.SOL = SOL;
    }

    public Double getMPL() {
        return MPL;
    }

    public void setMPL(Double MPL) {
        this.MPL = MPL;
    }

    public Double getDOGE() {
        return DOGE;
    }

    public void setDOGE(Double DOGE) {
        this.DOGE = DOGE;
    }

    public Double getAXS() {
        return AXS;
    }

    public void setAXS(Double AXS) {
        this.AXS = AXS;
    }

    public Double getAPE() {
        return APE;
    }

    public void setAPE(Double APE) {
        this.APE = APE;
    }

    public Double getADA() {
        return ADA;
    }

    public void setADA(Double ADA) {
        this.ADA = ADA;
    }

    public Double getLTC() {
        return LTC;
    }

    public void setLTC(Double LTC) {
        this.LTC = LTC;
    }

    public Double getSHIB() {
        return SHIB;
    }

    public void setSHIB(Double SHIB) {
        this.SHIB = SHIB;
    }

    public Double getTRB() {
        return TRB;
    }

    public void setTRB(Double TRB) {
        this.TRB = TRB;
    }

    public Double getXRP() {
        return XRP;
    }

    public void setXRP(Double XRP) {
        this.XRP = XRP;
    }

    public Double getXTZ() {
        return XTZ;
    }

    public void setXTZ(Double XTZ) {
        this.XTZ = XTZ;
    }

    public Double getUSDC() {
        return USDC;
    }

    public void setUSDC(Double USDC) {
        this.USDC = USDC;
    }
}
