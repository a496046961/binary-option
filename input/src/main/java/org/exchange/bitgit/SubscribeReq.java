package org.exchange.bitgit;

public class SubscribeReq {

    private String instType;
    private String channel;
    private String instId;

    public String getInstType() {
        return instType;
    }

    public void setInstType(String instType) {
        this.instType = instType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public SubscribeReq(String instType, String channel, String instId) {
        this.instType = instType;
        this.channel = channel;
        this.instId = instId;
    }

    public SubscribeReq() {}
}
