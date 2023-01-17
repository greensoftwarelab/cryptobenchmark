package com.example.cryptobenchmark;

import com.example.cryptobenchmark.mac.HMAC;
import com.example.cryptobenchmark.mac.HMACOperation;
import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;

import java.util.Map;
import java.util.Set;


public class MeasureHMACTest extends MeasureTest{

    String KEY = (String) StringType.genRandomWithSize(keyLen).getValue();

    public void exec_hmac(HMACOperation ho, String provider) throws Exception{
        for (String param : INPUT_MESSAGES) {
            String res = ho.do_hmac(KEY, param, provider);
            if(res==null){
                throw new Exception("Unable to appy hmac with " + provider);
            }
        }
    }


    @Test
    public void test_get_Macimpl() {
        String algo = "HMAC";
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        Map<String, Set<CryptoProvider>> cps =  de.getProvidersImplementingAlgorithm(algo);
        System.out.println(cps);
    }
   /*
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
    public void testHMACMD5_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_MD5, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA1_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_SHA1, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA224_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_SHA224, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA256_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_SHA256, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA384_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_SHA384, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA512_BC() throws Exception {
        String provider = "BC";
        exec_hmac(HMAC::mac_SHA512, provider);
    }

    // OPEN SSL

    @HunterDebug
    @Test
    public void testHMACMD5_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_MD5, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA1_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_SHA1, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA224_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_SHA224, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA256_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_SHA256, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA384_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_SHA384, provider);
    }

    @HunterDebug
    @Test
    public void testHMACSHA512_AndroidOpenSSL()  throws Exception {
        String provider = "AndroidOpenSSL";
        exec_hmac(HMAC::mac_SHA512, provider);
    }

}
