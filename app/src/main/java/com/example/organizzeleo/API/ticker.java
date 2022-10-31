package com.example.organizzeleo.API;

public class ticker {

    private Double buy;

    private Double high;

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public String toString(){
        return String.valueOf(getBuy());

    }

}
