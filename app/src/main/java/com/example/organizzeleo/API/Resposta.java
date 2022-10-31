package com.example.organizzeleo.API;

import androidx.annotation.NonNull;

public class Resposta {

    public ticker ticker;

    public ticker getTicker() {
        return ticker;
    }

    public void setTicker() {
        this.ticker = ticker;
    }

    @NonNull
    @Override
    public String toString(){
        return String.valueOf(ticker.getBuy());

        //+ "\n"+ "High: " + ticker.getHigh();
    }
}
