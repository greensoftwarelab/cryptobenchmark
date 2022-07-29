package com.example.cryptobenchmark;

import com.example.cryptobenchmark.mac.HMAC;
import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.cryptobenchmark.mac.HMAC.mac_MD5;
import static com.example.cryptobenchmark.mac.HMAC.mac_SHA1;
import static com.example.cryptobenchmark.mac.HMAC.mac_SHA224;
import static com.example.cryptobenchmark.mac.HMAC.mac_SHA256;
import static com.example.cryptobenchmark.mac.HMAC.mac_SHA384;
import static com.example.cryptobenchmark.mac.HMAC.mac_SHA512;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class HMacTest {

    public static final int KEY_SIZE = 128;
    public static final int DATA_LEN = 255;

    @Test
    public void test_get_Macimpl() {
        String algo = "HMAC";
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        Map<String, Set<CryptoProvider>> cps =  de.getProvidersImplementingAlgorithm(algo);
        System.out.println(cps);
    }

    @Test
    public void test_get_dig_impls() {
        String algo = "SHA";
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        Map<String, Set<CryptoProvider>> cps =  de.getProvidersImplementingAlgorithm(algo);
        System.out.println(cps);
    }

    @Test
    public void test_all_impls() {
        DeviceCryptoPrimitives dce = new DeviceCryptoPrimitives();
        dce.removeProvider("BC");
        dce.removeProvider("AndroidKeyStore");
        dce.removeProvider("AndroidKeyStoreBCWorkaround");
        HMAC mc  = new HMAC(dce);
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        List<String> res = mc.mac_all(msg, key);
        assertNotEquals(res.size(), 0);
    }


    @Test
    public void testHMACMD5() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_MD5(msg, key);
        assertNotNull(res);
    }

    @Test
    public void testHMACSHA1() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_SHA1(msg, key);
        assertNotNull(res);
    }

    @Test
    public void testHMACSHA224() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_SHA224(msg, key);
        assertNotNull(res);
    }

    @Test
    public void testHMACSHA256() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_SHA256(msg, key);
        assertNotNull(res);
    }

    @Test
    public void testHMACSHA384() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_SHA384(msg, key);
        assertNotNull(res);
    }

    @Test
    public void testHMACSHA512() {
        String msg = (String) StringType.genRandomWithSize(DATA_LEN).getValue();
        String key = (String) StringType.genRandomWithSize(KEY_SIZE).getValue();
        String res = mac_SHA512(msg, key);
        assertNotNull(res);
    }
}
