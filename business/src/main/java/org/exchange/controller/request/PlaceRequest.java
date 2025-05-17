package org.exchange.controller.request;

import java.io.Serializable;


public class PlaceRequest implements Serializable {

    public static final long serialVersionUID = 11L;

    private String symbol;

    private String amount;

    private Integer holding;

    private Integer direction;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getHolding() {
        return holding;
    }

    public void setHolding(Integer holding) {
        this.holding = holding;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "PlaceRequest{" +
                "symbol='" + symbol + '\'' +
                ", amount='" + amount + '\'' +
                ", holding=" + holding +
                ", direction=" + direction +
                '}';
    }
}
