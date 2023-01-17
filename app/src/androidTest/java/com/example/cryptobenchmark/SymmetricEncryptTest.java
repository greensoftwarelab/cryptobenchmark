package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;

import com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt;
import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt.decrypt_ARC4;
import static com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt.decrypt_BLOWFISH;
import static com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt.decrypt_ChaCha20;
import static com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt.decrypt_ChaCha20Poly;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ARC4_AndroidOpenSSL;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_ChaCha20;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SymmetricEncryptTest {

/*
    public void test_decrypt_algorithm(String algorithm, int times, String[] params){
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        for (int i = 0; i < times ; i++) {
            String target = i > params.length-1 ? params[i % params.length] : params[i];
            SecretKey pk = gen_key_AES_AndroidOpenSSL(128, "", "");
            Map<String, IvParameterSpec> m = se.encrypt_all(target, algorithm, pk,"AndroidOpenSSL");
        }
    }


    @Test
    public void test_AES_AndroidKeyStore() {
        String msg = (String) StringType.genRandomWithSize(64).getValue();
        String mode = "CBC";
        String padd = "PKCS7Padding";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey pk = gen_key_AES_AndroidKeyStore(256, mode, padd);
        Map.Entry<String,IvParameterSpec> e = se.encrypt_AES(msg, mode, padd, pk, "AndroidKeyStoreBCWorkaround");
        assertNotNull(e);
        //String dec = SymmetricDecrypt.decrypt_AES(e.getKey(), mode, padd, pk, "AndroidKeyStoreBCWorkaround", e.getValue());
        //assertEquals(dec, msg);
    }
    @Test
    public void test_AES_AndroidOpenSSL() {
        String msg = (String) StringType.genRandomWithSize(64).getValue();
        String mode = "CBC";
        String padd = "PKCS5PADDING";
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        SecretKey pk = gen_key_AES_AndroidOpenSSL(256, "", "");
        Map.Entry<String,IvParameterSpec> e = se.encrypt_AES(msg, mode, padd, pk, "AndroidOpenSSL");
        assertNotNull(e);
        String dec = SymmetricDecrypt.decrypt_AES(e.getKey(), mode, padd, pk, "AndroidOpenSSL", e.getValue());
        assertEquals(dec, msg);
    }

    @Test
    public void test_key_gen_AES() throws NoSuchPaddingException, NoSuchAlgorithmException {
        String algo = "AES";
        int keysize = 128;
        SecretKey  k = gen_key_AES_AndroidKeyStore(keysize);
        assertEquals(k.getAlgorithm(), algo);
        k = SymmetricKeyGen.gen_key_AES_AndroidOpenSSL(keysize,"","");
        assertEquals(k.getAlgorithm(), algo);
        keysize = 256;
        k = gen_key_AES_AndroidKeyStore(keysize);
        assertEquals(k.getAlgorithm(), algo);
        k = SymmetricKeyGen.gen_key_AES_AndroidOpenSSL(keysize,"", "");
        assertEquals(k.getAlgorithm(), algo);
    }

    @Test
    public void test_DES_encrypt() {
        String msg = (String) StringType.genRandomWithSize(16).getValue();
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        Map<String, IvParameterSpec> res  = se.encrypt_all(msg, "DES", 64);
        assertNotNull(res);
    }

    /*@Test
    public void test_all_AES_encrypt() {
        String msg = (String) StringType.genRandomWithSize(256).getValue();
        SymmetricEncrypt se = new SymmetricEncrypt(new DeviceCryptoPrimitives());
        Map<String, IvParameterSpec> enc_res  = se.encrypt_all(msg, "AES", 256);
        assertNotNull(enc_res);
    }*/

    /*
    @Test
    public void test_blowfish() {
        String msg = (String) StringType.genRandomWithSize(123).getValue();
       // SymmetricEncrypt se = new SymmetricEncrypt();
       // Map<String, IvParameterSpec> enc_res  = se.encrypt_all(msg, "AES", 128);
        SecretKey sk = gen_key_BLOWFISH_BC(256, "", "padd");
        Map.Entry<String,IvParameterSpec> e = SymmetricEncrypt.encrypt_BLOWFISH(msg, "", "",  sk, "BC");
        assertNotNull(e);
        String res = decrypt_BLOWFISH(e.getKey(), "", "", sk,  "BC", e.getValue());
        assertEquals(res, msg);
    }

    @Test
    public void test_rc4() {
        String msg = (String) StringType.genRandomWithSize(123).getValue();
        SecretKey sk = gen_key_ARC4_AndroidOpenSSL(256, "", "padd");
        Map.Entry<String,IvParameterSpec> e = SymmetricEncrypt.encrypt_ARC4(msg, "", "padd",  sk, "AndroidOpenSSL");
        assertNotNull(e);
        String res = decrypt_ARC4(e.getKey(), "", "", sk,  "AndroidOpenSSL", e.getValue());
        assertEquals(res, msg);
    }

    @Test
    public void test_chacha20() {
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        SecretKey sk = gen_key_ChaCha20(256);
        Map.Entry<String,IvParameterSpec> e = SymmetricEncrypt.encrypt_ChaCha20(msg, "", "", sk, "");
        assertNotNull(e);
        String res = decrypt_ChaCha20(e.getKey(),"", "", sk, "", e.getValue());
        assertEquals(res, msg);
    }

    @Test
    public void test_chacha20_poly() {
        // provavelmente vai dar ao mesmo
        String msg = (String) StringType.genRandomWithSize(255).getValue();
        SecretKey sk = gen_key_ChaCha20(256);
        // ChaCha20/Poly1305/NoPadding
        Map.Entry<String,IvParameterSpec> e = SymmetricEncrypt.encrypt_ChaCha20(msg, "", "", sk, "");
        assertNotNull(e);
        String res = decrypt_ChaCha20(e.getKey(),"", "", sk, "", e.getValue());
        assertEquals(res, msg);
    }

*/
}