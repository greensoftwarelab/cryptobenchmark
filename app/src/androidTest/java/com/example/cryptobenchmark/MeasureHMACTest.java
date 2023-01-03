package com.example.cryptobenchmark;

import com.example.cryptobenchmark.mac.HMAC;
import com.example.cryptobenchmark.mac.HMACOperation;
import com.example.cryptobenchmark.misc.datatypes.StringType;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;


public class MeasureHMACTest extends MeasureTest{

    public void exec_hmac(HMACOperation ho, String key, String[] params){
        for (String param : params) {
            ho.do_hmac(key, param);
        }
    }

    /*
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
        String msg = (String) StringType.genRandomWithSize(inputSize).getValue();
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        List<String> res = mc.mac_all(msg, key);
        assertNotEquals(res.size(), 0);
    }*/


    @HunterDebug
    @Test
    public void testHMACMD5() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_MD5;
        exec_hmac(hmo, key, msgs);
    }

    @HunterDebug
    @Test
    public void testHMACSHA1() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_SHA1;
        exec_hmac(hmo, key, msgs);
    }

    @HunterDebug
    @Test
    public void testHMACSHA224() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_SHA224;
        exec_hmac(hmo, key, msgs);
    }

    @HunterDebug
    @Test
    public void testHMACSHA256() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_SHA256;
        exec_hmac(hmo, key, msgs);
    }

    @HunterDebug
    @Test
    public void testHMACSHA384() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_SHA384;
        exec_hmac(hmo, key, msgs);
    }

    @HunterDebug
    @Test
    public void testHMACSHA512() {
        String[] msgs = gen_random_workload(inputSize, nTimes);
        String key = (String) StringType.genRandomWithSize(keyLen).getValue();
        HMACOperation hmo = HMAC::mac_SHA512;
        exec_hmac(hmo, key, msgs);
    }
}
