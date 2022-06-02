package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;

import com.example.cryptobenchmark.decrypt.symmetric.SymmetricDecrypt;
import com.example.cryptobenchmark.digest.Digest;
import com.example.cryptobenchmark.encrypt.symmetric.SymmetricEncrypt;
import com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidKeyStore;
import static com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen.gen_key_AES_AndroidOpenSSL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EncryptTest {

    public void test_encrypt_algorithm(String algorithm, int times, String[] params){
        SymmetricEncrypt se = new SymmetricEncrypt();
        for (int i = 0; i < times ; i++) {
            String target = i > params.length-1 ? params[i % params.length] : params[i];
            SecretKey pk = gen_key_AES_AndroidOpenSSL(128);
            Map<String, IvParameterSpec> m = se.encrypt_all(target, algorithm, pk,"AndroidOpenSSL");
        }
    }

    @Test
    public void test_AES_AndroidKeyStore() {
        String msg = (String) StringType.genRandomWithSize(64).getValue();
        String mode = "CBC";
        String padd = "PKCS7Padding";
        SymmetricEncrypt se = new SymmetricEncrypt();
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
        SymmetricEncrypt se = new SymmetricEncrypt();
        SecretKey pk = gen_key_AES_AndroidOpenSSL(256);
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
        k = SymmetricKeyGen.gen_key_AES_AndroidOpenSSL(keysize);
        assertEquals(k.getAlgorithm(), algo);
        keysize = 256;
        k = gen_key_AES_AndroidKeyStore(keysize);
        assertEquals(k.getAlgorithm(), algo);
        k = SymmetricKeyGen.gen_key_AES_AndroidOpenSSL(keysize);
        assertEquals(k.getAlgorithm(), algo);
    }

    @Test
    public void test_all() {
        test_encrypt_algorithm("AES", 1,  new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"});
    }

}