package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;

import com.example.cryptobenchmark.decrypt.assymmetric.AssymmetricDecrypt;
import com.example.cryptobenchmark.encrypt.assymmetric.AssymmetricEncrypt;
import com.example.cryptobenchmark.keygen.assymmetric.AssymmetricEncryptKeyGen;

import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import javax.crypto.spec.IvParameterSpec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AssymmetricEncryptTest {

    @Test
    public void test_sample_rsa() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair kp = AssymmetricEncryptKeyGen.gen_key_RSA_AndroidKeyStore(128);
        assertNotNull(kp);
        DeviceCryptoPrimitives dcp = new DeviceCryptoPrimitives();
        AssymmetricEncrypt ae = new AssymmetricEncrypt(dcp);
        AssymmetricDecrypt ad = new AssymmetricDecrypt(dcp);
        String msg = (String) StringType.genRandomWithSize(32).getValue();
        Map.Entry<String, IvParameterSpec> res = ae.encrypt_RSA(msg, "", "", kp.getPublic(), "AndroidKeyStoreBCWorkaround");
        assertNotNull(res);
        String decrypted_plaintext = ad.decrypt_RSA(res.getKey(), "", "", kp.getPrivate(), "AndroidKeyStoreBCWorkaround", res.getValue());
        System.out.println(msg);
        System.out.println(decrypted_plaintext);
        assertEquals(msg, decrypted_plaintext);
    }

}