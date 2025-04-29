package org.exchange.bitgit;

@FunctionalInterface
public interface SubscriptionListener {
    void onReceive(String data);
}
