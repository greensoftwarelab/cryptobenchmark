package com.example.cryptobenchmark;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DemoParamTest {

    @Test
    public void print_param() {
        //  adb shell am instrument -w -m  -e debug false -e class 'com.example.cryptobenchmark.DemoParamTest#print_param' com.example.cryptobenchmark.test/android.support.test.runner.AndroidJUnitRunner
        System.out.println(BuildConfig.INPUT_SIZE);
    }
}

