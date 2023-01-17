package com.example.cryptobenchmark;

import com.example.cryptobenchmark.sign.Sign;
import com.example.cryptobenchmark.verify.Verify;
import com.example.cryptobenchmark.verify.VerifyOperation;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;
import java.security.KeyPair;
import java.security.PublicKey;


public class MeasureVerifyTest extends MeasureTest{

    // aks - Ec
    // bc - ecdh, dsa, shaXwithecdsa, shaXwithrsa
    // aossl - ecdh, shaXwithecdsa, shaXwithrsa
    // aksbcw - shaXwithrsa, shaXwithdsa,

    public static int KEY_LEN = keyLen;
    public static String CRYPTO_PROVIDER =  PROVIDER; // "AndroidOpenSSL"; //MeasureTest.provider;
    public static KeyPair KEY_PAIR = gen_key_pair(KEY_LEN, ALGORITHM, CRYPTO_PROVIDER, MODE, PADDING);
    public static  String[] SIGN_OUTPUTS = gen_outputs();


    public static String[] gen_outputs(){
        String[] res = new String[INPUT_MESSAGES.length];
        for (int i = 0; i < INPUT_MESSAGES.length ; i++) {
            res[i] = Sign.sign(INPUT_MESSAGES[i], ALGORITHM, KEY_PAIR.getPrivate() ,PROVIDER);
        }
        return res;
    }

    public void verify(VerifyOperation vop, String algo, PublicKey key, String provider) throws Exception {
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            if(!vop.verify(INPUT_MESSAGES[i], SIGN_OUTPUTS[i], algo, key, provider)){
                throw new Exception("Verification error");
            }
        }
    }
    @HunterDebug
    @Test
    public void test_sign_SHA1WithRSA_AndroidOpenSSL() throws Exception {
        String algo = "SHA1WithRSA", mode = "", padding = "", provider = "AndroidOpenSSL";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA256WithRSA_AndroidOpenSSL() throws Exception {
        String algo = "SHA256WithRSA", mode = "", padding = "", provider = "AndroidOpenSSL";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA384WithRSA_AndroidOpenSSL() throws Exception {
        String algo = "SHA384WithRSA", mode = "", padding = "", provider = "AndroidOpenSSL";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA512WithRSA_AndroidOpenSSL() throws Exception {
        String algo = "SHA512WithRSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA1WithRSA_BC() throws Exception {
        String algo = "SHA1WithRSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA256WithRSA_BC() throws Exception {
        String algo = "SHA256WithRSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA384WithRSA_BC() throws Exception {
        String algo = "SHA384WithRSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA512WithRSA_BC() throws Exception {
        String algo = "SHA512WithRSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }


    /*
    @HunterDebug
    @Test
    public void test_sign_ECDSA_AndroidOpenSSL() throws Exception {
        String algo = "SHA256WithECDSA", mode = "", padding = "", provider = "AndroidOpenSSL";
        KeyPair kp =  gen_key_ECDSA(KEY_LEN); // gen_key_pair(KEY_LEN, algo, provider, padding,  mode);
        SignOperation sop = Sign::sign;
        sign(sop, algo, kp.getPrivate(), provider);
    }*/

    //
    // DSA
    //

    @HunterDebug
    @Test
    public void test_sign_SHA1WithDSA_BC() throws Exception {
        String algo = "SHA1WithDSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA224WithDSA_BC() throws Exception {
        String algo = "SHA224WithDSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA256WithDSA_BC() throws Exception {
        String algo = "SHA256WithDSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA384WithDSA_BC() throws Exception {
        String algo = "SHA384WithDSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

    @HunterDebug
    @Test
    public void test_sign_SHA512WithDSA_BC() throws Exception {
        String algo = "SHA512WithDSA", mode = "", padding = "", provider = "BC";
        VerifyOperation vop = Verify::verify;
        verify(vop, algo, KEY_PAIR.getPublic(), provider);
    }

}
