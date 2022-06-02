package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;

import com.example.cryptobenchmark.digest.Digest;

import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DigestTest {

    @Test
    public void listProviders() {
        // Context of the app under test.
        /*Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            Log.i("CRYPTO","provider: "+provider.getName());
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                Log.i("CRYPTO","  algorithm: "+service.getAlgorithm());
            }
        }
        assertTrue(true);*/
        //listHashAlgorithms();
    }

    public void test_digest_algorithm(String algorithm, int times, String[] params){
        Digest d = new Digest();
        for (int i = 0; i < times ; i++) {
            String target = i > params.length-1 ? params[i % params.length] : params[i];
            d.digest_all(target, algorithm);
        }
    }

    @Test
    public void test_md5(){
        test_digest_algorithm("MD5", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }

    @Test
    public void test_sha1(){
        test_digest_algorithm("SHA1", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }
    @Test
    public void test_sha224(){
        test_digest_algorithm("SHA224", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }
    @Test
    public void test_sha256(){
        test_digest_algorithm("SHA256", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }
    @Test
    public void test_sha384(){
        test_digest_algorithm("SHA384", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }
    @Test
    public void test_sha512(){
        test_digest_algorithm("SHA512", 25, new String[]{"bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj"} );
    }

    @Test
    public void test_all() {
       test_md5();
       test_sha1();
       test_sha224();
       test_sha256();
       test_sha384();
       test_sha512();
    }


}