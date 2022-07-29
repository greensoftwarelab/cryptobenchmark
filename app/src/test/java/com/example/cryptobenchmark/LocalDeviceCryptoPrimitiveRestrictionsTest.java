package com.example.cryptobenchmark;

import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.DevicePrimitiveRestrictions;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class LocalDeviceCryptoPrimitiveRestrictionsTest {

    public static final String filepath = "src/main/res/raw/known_primitive_restrictions.json";

    @Test
    public void testFileLoad(){
        DevicePrimitiveRestrictions dpr = new DevicePrimitiveRestrictions(filepath);
        assertNotNull(dpr);
    }

    @Test
    public void testFilterPrimitives() throws JSONException {
        DevicePrimitiveRestrictions dpr = new DevicePrimitiveRestrictions(filepath);
        DeviceCryptoPrimitives dcp = new DeviceCryptoPrimitives();
        Map<String, CryptoProvider> mp = dpr.filterPrimitives(dcp.getDeviceProviders());
        System.out.println(mp);
    }

}
