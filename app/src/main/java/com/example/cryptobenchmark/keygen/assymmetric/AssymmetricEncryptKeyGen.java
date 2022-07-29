package com.example.cryptobenchmark.keygen.assymmetric;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AssymmetricEncryptKeyGen {


    public static KeyPair  gen_key_RSA_AndroidKeyStore(int keylen) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");

        //We are creating the key pair with sign and verify purposes
        KeyGenParameterSpec parameterSpec = new KeyGenParameterSpec.Builder("cryptobenchmark",
                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_DECRYPT )
                .setUserAuthenticationRequired(false)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(false)
                .setKeySize(keylen)
                .build();

        //Initialization of key generator with the parameters we have specified above
        keyPairGenerator.initialize(parameterSpec);
        //Generates the key pair
        return keyPairGenerator.genKeyPair();
    }

    public static KeyPair  gen_key_RSA(int keylen) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
        keyPairGenerator.initialize(keylen);
        /*
        //We are creating the key pair with sign and verify purposes
        KeyGenParameterSpec parameterSpec = new KeyGenParameterSpec.Builder("cryptobenchmark",
                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_DECRYPT )
                .setUserAuthenticationRequired(false)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(false)
                .setKeySize(keylen)
                .build();

        //Initialization of key generator with the parameters we have specified above
        keyPairGenerator.initialize(parameterSpec);*/
        //Generates the key pair
        return keyPairGenerator.genKeyPair();
    }

    public static KeyPair  gen_key(int keylen, String algo) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = java.security.KeyPairGenerator.getInstance(algo);
        keyPairGenerator.initialize(keylen);
        /*
        //We are creating the key pair with sign and verify purposes
        KeyGenParameterSpec parameterSpec = new KeyGenParameterSpec.Builder("cryptobenchmark",
                KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_DECRYPT )
                .setUserAuthenticationRequired(false)
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setRandomizedEncryptionRequired(false)
                .setKeySize(keylen)
                .build();

        //Initialization of key generator with the parameters we have specified above
        keyPairGenerator.initialize(parameterSpec);*/
        //Generates the key pair
        return keyPairGenerator.genKeyPair();
    }
}
