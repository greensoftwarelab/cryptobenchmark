package com.example.cryptobenchmark;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.Test;

import java.security.Provider;
import java.security.Security;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidOpenSSL;
import static org.junit.Assert.assertNotNull;

public class DeviceCryptoPrimitivesTest {

    @Test
    public void testFileLoad(){
        Context ctx = InstrumentationRegistry.getContext();
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives(ctx);
        assertNotNull(de);

    }

    @Test
    public void testFileGen(){
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        assertNotNull(de);
    }

    @Test
    public void testSymmetricWithFileGen(){
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        String msg = (String) StringType.genRandomWithSize(64).getValue();
        SymmetricEncrypt se = new SymmetricEncrypt(de);
        SecretKey pk = gen_key_AES_AndroidOpenSSL(128, "", "");
        Map<String, IvParameterSpec> m = se.encrypt_all(msg, "AES", pk,"AndroidOpenSSL");
    }
}
