package org.exchange.bitgit;

import java.util.List;

public class WsBaseReq<T> {
    private String op;
    private List<T> args;


    public List<T> getArgs() {
        return args;
    }

    public void setArgs(List<T> args) {
        this.args = args;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public WsBaseReq(String op, List<T> args) {
        this.op = op;
        this.args = args;
    }

    public WsBaseReq() {}

}
