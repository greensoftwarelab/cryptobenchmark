package com.example.cryptobenchmark.misc.datatypes;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

import static com.example.cryptobenchmark.misc.Utils.byteArrayToString;
import static com.example.cryptobenchmark.misc.Utils.intToByteArray;

public class StringType implements DataType{

    private String value;

    public StringType() {
        this.value = "";
    }

    public StringType(String value) {
        this.value = value;
    }

    public static DataType genRandomWithSize(int size){
        SecureRandom rnd = new SecureRandom();
        byte[] token = new byte[size];
        rnd.nextBytes(token);
        return new StringType(byteArrayToString(token).substring(0, size));
        //System.out.println(token.length);
        //return new StringType(byteArrayToString(token));
    }

    public static DataType genPseudoRandomWithSize(int size, int seed){
        Random rnd =  new Random();
        rnd.setSeed(seed);
        byte[] token = new byte[size];
        rnd.nextBytes(token);
        return new StringType(byteArrayToString(token).substring(0, size));
    }

    public static DataType[] genRandomWithSize(int size, int count){
        DataType[] x = new StringType[count];
        for (int i = 0; i < count; i++) {
            x[i] = genRandomWithSize(size);
        }
        return x;
    }

    public static DataType[] genPseudoRandomWithSize(int size, int seed, int count){
        DataType[] x = new StringType[count];
        int new_seed = seed;
        for (int i = 0; i < count; i++) {
            x[i] =  genPseudoRandomWithSize(size, new_seed);
            new_seed = new_seed + i;
        }
        return x;
    }

    @Override
    public Object getValue() {
        return value;
    }


    /*public static String[] genRandomStringsWithSize(int size, int count){
        String[] x = new String[count];
        for (int i = 0; i < count; i++) {
            x[i] = ((String) ((StringType) genRandomWithSize(size)).getValue());
        }
        return x;
    }*/
}
