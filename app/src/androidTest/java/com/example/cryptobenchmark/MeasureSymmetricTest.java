package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;


import com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt;
import com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecryptOperation;
import com.example.cryptobenchmark.encrypt.assymmetric.AssymmetricEncrypt;
import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncryptOperation;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;
import com.hunter.library.debug.HunterDebug;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt.encrypt_BLOWFISH;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_3DES_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_BC;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_BLOWFISH_BC;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ChaCha20;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_DES_BC;

@RunWith(AndroidJUnit4.class)
public class MeasureSymmetricTest extends MeasureTest {

    public static void encrypt_decrypt(SymmetricEncryptOperation so, SymmetricDecryptOperation sdo,
                                       SecretKey sk, String[] params,
                                       String provider, String padding, String mode){
        for (String param : params) {
            Map.Entry<String, IvParameterSpec> res = so.encrypt(param, mode, padding, sk, provider);
            String originaltext = sdo.decrypt(res.getKey(), mode, padding, sk, provider, res.getValue());
        }
    }

    public static void encrypt(SymmetricEncryptOperation so, SymmetricDecryptOperation sdo,
                                       SecretKey sk, String[] params,
                                       String provider, String padding, String mode){
        for (String param : params) {
            Map.Entry<String, IvParameterSpec> res = so.encrypt(param, mode, padding, sk, provider);
        }
    }

    // CHACHA20
    @HunterDebug
    @Test
    public void test_CHA_CHA_20_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String provider = "AndroidOpenSSL";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey sk = gen_key_ChaCha20(keyLen);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk = gen_key_AES_AndroidKeyStore(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk = gen_key_AES_AndroidOpenSSL(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_DES_BC(keyLen, mode, padd);
        for (int i = 0; i <  nTimes; i++) {
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
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_3DES_AndroidOpenSSL(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_3DES_AndroidKeyStore(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        SecretKey sk =  gen_key_3DES_AndroidKeyStore(keyLen, mode, padd);
        for (int i = 0; i < nTimes ; i++) {
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
        String[] params = gen_random_workload(inputSize, nTimes);
        SymmetricEncryptOperation so = SymmetricEncrypt::encrypt_ARC4;
        SymmetricDecryptOperation deo = SymmetricDecrypt::decrypt_ARC4;
        SecretKey sk =  gen_key_ARC4_BC(keyLen, mode, padd);
        encrypt_decrypt(so, deo, sk, params, provider, padd, mode);
    }

    @Test
    @HunterDebug
    public void test_ARC4_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        String mode = "";
        String padd = "";
        String provider = "AndroidOpenSSL";
        String[] params = gen_random_workload(inputSize, nTimes);
        SymmetricEncryptOperation so = SymmetricEncrypt::encrypt_ARC4;
        SymmetricDecryptOperation deo = SymmetricDecrypt::decrypt_ARC4;
        SecretKey sk =  gen_key_ARC4_AndroidOpenSSL(keyLen, mode, padd);
        encrypt_decrypt(so, deo, sk, params, provider, padd, mode);
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
    public void test_BLOWFISH_BC() {
        String mode = "";
        String padd = "";
        String provider = "BC";
        SecretKey sk =  gen_key_BLOWFISH_BC(keyLen, mode, padd);
        String[] params = gen_random_workload(inputSize, nTimes);
        SymmetricEncryptOperation so = SymmetricEncrypt::encrypt_BLOWFISH;
        SymmetricDecryptOperation deo = SymmetricDecrypt::decrypt_BLOWFISH;
        encrypt_decrypt(so, deo, sk, params, provider, padd, mode);
    }
}
