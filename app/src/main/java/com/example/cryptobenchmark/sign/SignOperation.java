package com.example.cryptobenchmark.sign;

import java.security.PrivateKey;

@FunctionalInterface
public interface SignOperation {
    public String sign(String message, String algoFullDefinition, PrivateKey pk , String provider);
}
