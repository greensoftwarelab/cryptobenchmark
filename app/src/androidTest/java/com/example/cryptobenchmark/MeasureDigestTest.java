package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;

import com.example.cryptobenchmark.digest.Digest;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;
import com.hunter.library.debug.HunterDebug;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeasureDigestTest extends MeasureTest{

    public void test_digest_algorithm(String algorithm, int times, String[] params){
        Digest d = new Digest(new DeviceCryptoPrimitives());
        for (int i = 0; i < times ; i++) {
            String target = i > params.length-1 ? params[i % params.length] : params[i];
            d.digest_all(target, algorithm);
        }
    }

    @HunterDebug
    @Test
    public void test_md5(){
        test_digest_algorithm("MD5",
                nTimes, new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }

    @HunterDebug
    @Test
    public void test_sha1(){
        test_digest_algorithm("SHA1", nTimes,
                new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }

    @HunterDebug
    @Test
    public void test_sha224(){
        test_digest_algorithm("SHA224", nTimes,
                new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }

    @HunterDebug
    @Test
    public void test_sha256(){
        test_digest_algorithm("SHA256", nTimes,
                new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }

    @HunterDebug
    @Test
    public void test_sha384(){
        test_digest_algorithm("SHA384", nTimes,
                new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }

    @HunterDebug
    @Test
    public void test_sha512(){
        test_digest_algorithm("SHA512", nTimes,
                new String[]{ (String) StringType.genRandomWithSize(inputSize).getValue()});
    }
}