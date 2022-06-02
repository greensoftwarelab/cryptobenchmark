package com.example.cryptobenchmark;

import com.example.cryptobenchmark.digest.Digest;

import org.junit.Test;

import static com.example.cryptobenchmark.misc.Utils.listHashAlgorithms;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
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
        listHashAlgorithms();
    }

    @Test
    public void test_md5_digests(){
        Digest d = new Digest();
        d.digest_MD5_all("bssdjf sf sdfisfi sif difsifsif sfsifsa aakakoro3 245o2ti 243or deo24 rj");
    }
}