package com.example.cryptobenchmark.encrypt.symmetric;

import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@FunctionalInterface
public interface SymmetricEncryptOperation {

    Map.Entry<String, IvParameterSpec> encrypt(String message, String mode, String padding, SecretKey key, String provider);

}
