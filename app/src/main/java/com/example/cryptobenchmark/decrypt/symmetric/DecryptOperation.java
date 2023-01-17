package com.example.cryptobenchmark.decrypt.symmetric;


import java.security.Key;
import javax.crypto.spec.IvParameterSpec;

@FunctionalInterface
public interface DecryptOperation {
    String decrypt(String ciphertext, String mode, String padding, Key key, String provider,
                   IvParameterSpec iv);
}
