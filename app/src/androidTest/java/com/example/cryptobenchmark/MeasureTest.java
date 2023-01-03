package com.example.cryptobenchmark;

import com.example.cryptobenchmark.misc.datatypes.DataType;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import org.junit.After;
import org.junit.Before;

public class MeasureTest {

    public static int inputSize = Integer.parseInt(BuildConfig.INPUT_SIZE);
    public static int keyLen = Integer.parseInt(BuildConfig.KEY_LEN);
    public static int nTimes = Integer.parseInt(BuildConfig.N_TIMES);
    public static int warmup_time = Integer.parseInt(BuildConfig.WARM_UP_TIME);
    public static int cool_down_time = Integer.parseInt(BuildConfig.COOL_DOWN_TIME);
    public static String provider =BuildConfig.PROVIDER;
    public static String[] inputs =  gen_random_workload(inputSize, nTimes);


    @Before
    public void before_tests(){
        try {
            System.out.println("before");
            Thread.sleep(warmup_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after_tests(){
        try {
            System.out.println("after");
            Thread.sleep(cool_down_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String[] gen_random_workload(int size, int times){
        String[] res = new String[times];
        DataType[] dt = StringType.genRandomWithSize(size, times);
        for (int i = 0; i < dt.length; i++) {
            res[i] = ((String) dt[i].getValue());
        }
        return res;
    }

    public static boolean istheSelectedProvider(String providerId){
        return provider.toLowerCase().equals(providerId.toLowerCase());
    }

}
