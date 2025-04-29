package org.exchange.bitgit.entity;

import java.io.Serializable;
import java.util.List;

public class JsonBean implements Serializable {
    private String action;
    private Arg arg;
    private String data;
    private long ts;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Arg getArg() {
        return arg;
    }

    public void setArg(Arg arg) {
        this.arg = arg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}



