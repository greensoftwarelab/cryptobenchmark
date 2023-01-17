package com.example.cryptobenchmark.digest;

@FunctionalInterface
public interface DigestOperation {

    String digest(String msg, String provider);
}
