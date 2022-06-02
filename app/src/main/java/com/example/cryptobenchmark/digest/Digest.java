package com.example.cryptobenchmark.digest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.cryptobenchmark.misc.Utils.StringToByteArray;
import static com.example.cryptobenchmark.misc.Utils.byteArrayToString;
import static com.example.cryptobenchmark.misc.Utils.getMethod;

public class Digest {

    Map<String, List<String>> digests_providers = new HashMap<>();


    public Digest(){
        List<String> basicTriple = new ArrayList<>(
                Arrays.asList(
                        "BC",
                        "AndroidOpenSSL",
                        "Empty"
                ));
        this.digests_providers.put( "MD5", basicTriple);
        this.digests_providers.put( "SHA1", basicTriple);
        this.digests_providers.put( "SHA224", basicTriple);
        this.digests_providers.put( "SHA226", basicTriple);
        this.digests_providers.put( "SHA256", basicTriple);
        this.digests_providers.put( "SHA384", basicTriple);
        this.digests_providers.put( "SHA512", basicTriple);
    }

    /*
    *  -------------------- MD5 --------------------
     * */
    public List<String> digest_all(String msg, String algorithm){
        if( ! this.digests_providers.containsKey(algorithm) ){
            return null;
        }
        List<String> md5_providers = this.digests_providers.get(algorithm);
        List<String> x = new ArrayList<>();
        for(String provider : md5_providers){
            Method method = getMethod(this.getClass().getName(), String.format("digest_%s", algorithm),  new Class[]{ String.class, String.class});
            try {
                x.add((String) method.invoke(this, new Object[]{msg, provider}));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return x;
    }

    public static String digest_MD5(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String digest_MD5_Empty(String message){
        return message;
    }

    /*
     *  -------------------- SHA1 --------------------
     * */
    public static String digest_SHA1(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     *  -------------------- SHA224 --------------------
     * */
    public static String digest_SHA224(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-224", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     *  -------------------- SHA256 --------------------
     * */
    public static String digest_SHA256(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-256", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     *  -------------------- SHA384 --------------------
     * */
    public static String digest_SHA384(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-384", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     *  -------------------- SHA512 --------------------
     * */
    public static String digest_SHA512(String message, String provider){
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-512", provider);
            digest.update(StringToByteArray(message));
            return byteArrayToString(digest.digest());
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "";
    }
}
