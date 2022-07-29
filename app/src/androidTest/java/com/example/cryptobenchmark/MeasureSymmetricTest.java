package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;


import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;
import java.util.Set;


import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_BC;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_BLOWFISH_BC;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ChaCha20;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_DES_BC;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class MeasureSymmetricTest {

    private static int N_EXECS = 200;
    private static int KEY_LEN = 256;

    @Test
    public void test_get_impls() {
        String algo = "SHA";
        DeviceCryptoPrimitives de = new DeviceCryptoPrimitives();
        Map<String, Set<CryptoProvider>> cps =  de.getProvidersImplementingAlgorithm(algo);
        System.out.println(cps);
    }

    // CHACHA20
    @Test
    @HunterDebug
    public void test_CHA_CHA_20_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_ChaCha20(KEY_LEN);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_ChaCha20(target, sk);
        }
    }

    // AES GCM

    @Test
    @HunterDebug
    public void test_AES_GCM_NoPadding_AndroidKeyStore() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "GCM";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidKeyStore(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_GCM_NoPadding_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "GCM";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    // AES GCM - SIV

    @Test
    @HunterDebug
    public void test_AES_GCM_siv_NoPadding_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "GCM-SIV";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    // CBC

    @Test
    @HunterDebug
    public void test_AES_CBC_NoPadding_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CBC_PKCS7Padding_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 256;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CBC_PKCS5Padding_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    // ECB NO padding

    @Test
    @HunterDebug
    public void test_AES_ECB_NOPADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_ECB_NOPADDING_AndroidKeyStoreBCWorkaround() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_AES_AndroidKeyStore(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }


    @Test
    @HunterDebug
    public void test_AES_ECB_PKCS5PADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_ECB_PKCS7PADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    // CTR NO padding

    @Test
    @HunterDebug
    public void test_AES_CTR_NOPADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CTR_NOPADDING_AndroidKeyStoreBCWorkaround() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_AES_AndroidKeyStore(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_AES(target, mode, padd, sk, provider);
        }
    }

    // DES - ECB

    @Test
    @HunterDebug
    public void test_DES_ECB_NOPADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "BC";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_ECB_PKCS5PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_ECB_PKCS7PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        int keylen = 128;
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(keylen, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    // DES - CTR

    @Test
    @HunterDebug
    public void test_DES_CTR_NOPADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CTR_PKCS5PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CTR";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CTR_PKCS7PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CTR";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    // DES - CBC

    @Test
    @HunterDebug
    public void test_DES_CBC_NOPADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CBC_PKCS5PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CBC_PKCS7PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    // DES - OFB

    @Test
    @HunterDebug
    public void test_DES_OFB_NOPADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "OFB";
        String padd = "NOPADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_OFB_PKCS5PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "OFB";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_OFB_PKCS7PADDING_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "OFB";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_DES_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_DES(target, mode, padd, sk, provider);
        }
    }

    // 3DES (DESEDE) - CBC

    @Test
    @HunterDebug
    public void test_3DES_CBC_NOPADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_3DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_CBC_PKCS5PADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_3DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_CBC_PKCS7PADDING_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_3DES(target, mode, padd, sk, provider);
        }
    }

    // 3DES (DESEDE) - CBC

    @Test
    @HunterDebug
    public void test_3DES_ECB_NOPADDING_AndroidKeyStoreBCWorkaround() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_3DES_AndroidKeyStore(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_3DES(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_ECB_PKCS7PADDING_AndroidKeyStoreBCWorkaround() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_3DES_AndroidKeyStore(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_3DES(target, mode, padd, sk, provider);
        }
    }

    // ARC4

    @Test
    @HunterDebug
    public void test_ARC4_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "";
        String padd = "";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_ARC4_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_ARC4(target, mode, padd, sk, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_ARC4_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "";
        String padd = "";
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_ARC4_AndroidOpenSSL(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            se.encrypt_ARC4(target, mode, padd, sk, provider);
        }
    }

    // BLOWFISH
    @Test
    @HunterDebug
    public void test_BLOWFISH_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "";
        String padd = "";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_BLOWFISH_BC(KEY_LEN, mode, padd);
        for (int i = 0; i < N_EXECS ; i++) {
            String target = msg + (i % 9);
            Map.Entry<String, IvParameterSpec> res = se.encrypt_BLOWFISH(target, mode, padd, sk, provider);
            assertNotNull(res);
        }
    }
}
