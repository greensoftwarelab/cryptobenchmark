package com.example.cryptobenchmark.decrypt.symmetric;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@FunctionalInterface
public interface SymmetricDecryptOperation {
    String decrypt(String ciphertext, String mode, String padding, SecretKey key, String provider,
                   IvParameterSpec iv);
}
