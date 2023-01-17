package com.example.cryptobenchmark.mac;

@FunctionalInterface
public interface HMACOperation {

    String do_hmac(String input, String message, String provider);
}
