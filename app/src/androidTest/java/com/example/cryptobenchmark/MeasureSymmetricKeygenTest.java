package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.crypto.SecretKey;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_BC;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_BLOWFISH;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ChaCha20;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_DES;

@RunWith(AndroidJUnit4.class)
public class MeasureSymmetricKeygenTest extends MeasureTest {

    public static int KEY_LEN = keyLen;

    // CHACHA20
    @HunterDebug
    @Test
    public void test_CHA_CHA_20_AndroidOpenSSL() throws Exception {
        String algo = "ChaCha20";
        String provider = "AndroidOpenSSL";
        String padd = "";
        String mode = "";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_ChaCha20(KEY_LEN, provider);
        }
    }

    // AES GCM
    @Test
    @HunterDebug
    public void test_AES_GCM_NoPadding_AndroidKeyStore() throws Exception {
        String mode = "GCM";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES_AndroidKeyStore(KEY_LEN);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_GCM_NoPadding_AndroidOpenSSL() throws Exception {
        String mode = "GCM";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    // AES GCM - SIV

    @Test
    @HunterDebug
    public void test_AES_GCM_siv_NoPadding_AndroidOpenSSL() throws Exception {
        String mode = "GCM-SIV";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    // CBC

    @Test
    @HunterDebug
    public void test_AES_CBC_NoPadding_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CBC_PKCS7Padding_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CBC_PKCS5Padding_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_ECB_NOPADDING_AndroidOpenSSL() throws Exception {
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_ECB_NOPADDING_AndroidKeyStoreBCWorkaround() throws Exception {
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }


    @Test
    @HunterDebug
    public void test_AES_ECB_PKCS5PADDING_AndroidOpenSSL() throws Exception {
        String mode = "ECB";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_ECB_PKCS7PADDING_AndroidOpenSSL() throws Exception {
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    // CTR NO padding

    @Test
    @HunterDebug
    public void test_AES_CTR_NOPADDING_AndroidOpenSSL() throws Exception {
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_AES_CTR_NOPADDING_AndroidKeyStoreBCWorkaround() throws Exception {
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    // DES - ECB

    @Test
    @HunterDebug
    public void test_DES_ECB_NOPADDING_BC() throws Exception {
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk = gen_key_AES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_ECB_PKCS5PADDING_BC() throws Exception {
        String mode = "ECB";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_ECB_PKCS7PADDING_BC() throws Exception {
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    // DES - CTR
    @Test
    @HunterDebug
    public void test_DES_CTR_NOPADDING_BC() throws Exception {
        String mode = "CTR";
        String padd = "NOPADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CTR_PKCS5PADDING_BC() throws Exception {
        String mode = "CTR";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CTR_PKCS7PADDING_BC() throws Exception {
        String mode = "CTR";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    // DES - CBC

    @Test
    @HunterDebug
    public void test_DES_CBC_NOPADDING_BC() throws Exception {
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CBC_PKCS5PADDING_BC() throws Exception {
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_CBC_PKCS7PADDING_BC() throws Exception {
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    // DES - OFB

    @Test
    @HunterDebug
    public void test_DES_OFB_NOPADDING_BC() throws Exception {
        String mode = "OFB";
        String padd = "NOPADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_OFB_PKCS5PADDING_BC() throws Exception {
        String mode = "OFB";
        String padd = "PKCS5PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    @Test
    @HunterDebug
    public void test_DES_OFB_PKCS7PADDING_BC() throws Exception {
        String mode = "OFB";
        String padd = "PKCS7PADDING";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_DES(KEY_LEN, mode, padd, provider);
        }
    }

    // 3DES (DESEDE) - CBC

    @Test
    @HunterDebug
    public void test_3DES_CBC_NOPADDING_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "NOPADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_CBC_PKCS5PADDING_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_CBC_PKCS7PADDING_AndroidOpenSSL() throws Exception {
        String mode = "CBC";
        String padd = "PKCS7PADDING";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_3DES_AndroidOpenSSL(KEY_LEN, mode, padd);
        }
    }

    // 3DES (DESEDE) - CBC

    @Test
    @HunterDebug
    public void test_3DES_ECB_NOPADDING_AndroidKeyStoreBCWorkaround() throws Exception {
        String mode = "ECB";
        String padd = "NOPADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_3DES_AndroidKeyStore(KEY_LEN, mode, padd);
        }
    }

    @Test
    @HunterDebug
    public void test_3DES_ECB_PKCS7PADDING_AndroidKeyStoreBCWorkaround() throws Exception {
        String mode = "ECB";
        String padd = "PKCS7PADDING";
        String provider = "AndroidKeyStoreBCWorkaround";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_3DES_AndroidKeyStore(KEY_LEN, mode, padd);
        }
    }

    // ARC4

    @Test
    @HunterDebug
    public void test_ARC4_BC() throws Exception {
        String mode = "";
        String padd = "";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_ARC4_BC(KEY_LEN, mode, padd);
        }
    }

    @Test
    @HunterDebug
    public void test_ARC4_AndroidOpenSSL() throws Exception {
        String mode = "";
        String padd = "";
        String provider = "AndroidOpenSSL";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_ARC4_AndroidOpenSSL(KEY_LEN, mode, padd);
        }
    }

    /*
    // BLOWFISH
    @HunterDebug
    @Test
    public void test_BLOWFISH_BC() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "";
        String padd = "";
        String provider = "BC";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk =  gen_key_BLOWFISH_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
            String target = msg + (i % 9);
            Map.Entry<String, IvParameterSpec> res = encrypt_BLOWFISH(target, mode, padd, sk, provider);
            assertNotNull(res);
        }
    }*/

    // BLOWFISH
    @HunterDebug
    @Test
    public void test_BLOWFISH_BC() throws Exception {
        String mode = "";
        String padd = "";
        String provider = "BC";
        for (int i = 0; i < INPUT_MESSAGES.length; i++) {
            SecretKey sk =  gen_key_BLOWFISH(KEY_LEN, mode, padd, provider);
        }
    }
}
