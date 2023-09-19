package com.example.cryptobenchmark;

import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.misc.CryptoPrimitive;
import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class LocalSymmetricTest {

    
    public void testEncryptAll() {
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        SymmetricEncrypt se = new SymmetricEncrypt(de);
    }
}
