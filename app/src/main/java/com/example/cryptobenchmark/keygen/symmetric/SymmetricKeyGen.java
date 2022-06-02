package com.example.cryptobenchmark.keygen.symmetric;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class SymmetricKeyGen {
    Map<String, Set<String>> keygen_providers = new HashMap<>();


    public SymmetricKeyGen(){
        this.keygen_providers.put( "AES", new HashSet<>(Arrays.asList(
                "AndroidOpenSSL", "AndroidKeyStore", "Empty")));
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static SecretKey gen_key_AES_AndroidOpenSSL(int keysize){
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("AES", "AndroidOpenSSL");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
        keygen.init(keysize);
        return keygen.generateKey();
    }



    public static SecretKey gen_key_AES_AndroidKeyStore(int keysize){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
        try {
            keyGenerator.init(new KeyGenParameterSpec.Builder("benchmark",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setKeySize(keysize)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        return keyGenerator.generateKey();
    }
    public static SecretKey gen_key_AES_AndroidKeyStore(int keysize, String mode, String padding){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
        try {
            keyGenerator.init(new KeyGenParameterSpec.Builder("benchmark",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setKeySize(keysize)
                    .setBlockModes(mode)
                    .setEncryptionPaddings(padding)
                    .build());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        return keyGenerator.generateKey();
    }

}
