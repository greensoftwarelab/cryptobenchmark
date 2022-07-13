package com.example.cryptobenchmark.misc;

import android.content.Context;
import android.util.Log;

import com.example.cryptobenchmark.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DeviceCryptoPrimitives {

    private String deviceName;
    private Map<String, CryptoProvider> deviceProviders = new HashMap<>();


    public DeviceCryptoPrimitives(Context ctx) {
        JSONObject jo = this.loadSONFile(ctx);
        try {
            this.loadFromJSONObject(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceCryptoPrimitives() {
        this.deviceName = android.os.Build.MODEL;
        try {
            JSONObject jo = this.loadFromDevice();
            this.loadFromJSONObject(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceCryptoPrimitives(String deviceName, Map<String, CryptoProvider> deviceProviders) {
        this.deviceName = deviceName;
        this.deviceProviders = deviceProviders;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Map<String, CryptoProvider> getDeviceProviders() {
        return deviceProviders;
    }

    public void addProvider(CryptoProvider cp){
        this.deviceProviders.put(cp.getProviderName(), cp);
    }

    private JSONObject loadSONFile(Context ctx){
        String filecontent = null;
        JSONObject jo = new JSONObject();
        try {
            filecontent = readStream(ctx.getResources().openRawResource(R.raw.device_primitives));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new JSONObject(filecontent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }

    public void loadFromJSONObject(JSONObject jo) throws JSONException {
        this.deviceName = jo.getString("device") != null ? jo.getString("device") : this.deviceName;
        for (Iterator<String> it = jo.getJSONObject("providers").keys(); it.hasNext(); ) {
            String provider = it.next();
            CryptoProvider cp = new CryptoProvider(provider);
            cp.loadFromJsonObject(jo.getJSONObject("providers").getJSONObject(provider));
            this.addProvider(cp);
        }
    }

    private String readStream(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private JSONObject loadFromDevice() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("device", this.deviceName);
        Provider[] providers = Security.getProviders();
        JSONObject providersInfo = new JSONObject();
        for (Provider provider : providers) {
            JSONObject providerInfo = new JSONObject();
            Set<Provider.Service> services = provider.getServices();
            JSONObject algos = new JSONObject();
            providerInfo.put("version", provider.getVersion());
            for (Provider.Service service : services) {
                JSONObject algo = getAlgoFromName(service.getAlgorithm());
                if(!algos.has(algo.getString("name"))){
                    algos.put(algo.getString("name"), algo);
                }
                else{
                   algos.put(algo.getString("name"), mergeAlgos(algos.getJSONObject(algo.getString("name")), algo));
                }
            }
            providerInfo.put("algorithms", algos);
            providersInfo.put(provider.getName(), providerInfo);
        }
        jo.put("providers", providersInfo);
        return jo;
    }

    private static JSONObject mergeAlgos(JSONObject algo, JSONObject newAlgo) throws JSONException {
        if(newAlgo.has("modes")){
            JSONObject jo = algo.has("modes") ? algo.getJSONObject("modes") : new JSONObject();
            algo.put("modes", mergeJSONObjects(jo, (JSONObject) newAlgo.get("modes")));
        }
        if(newAlgo.has("key_sizes")){
            JSONArray jas = algo.has("key_sizes") ? algo.getJSONArray("key_sizes") : new JSONArray();
            algo.put("key_sizes", mergeJSONArrays(jas, (JSONArray) newAlgo.get("key_sizes")));
        }

        return algo;
    }

    private static JSONObject mergeJSONObjects(JSONObject dest, JSONObject source) throws JSONException {
        Iterator<String> keys = source.keys();
        while(keys.hasNext()){
            String key = keys.next();
            if(dest.has(key)){
                if(dest.get(key) instanceof JSONObject){
                    dest.put(key, mergeJSONObjects(((JSONObject) dest.get(key)), ((JSONObject) source.get(key))));
                }
                else if(dest.get(key) instanceof JSONArray){
                    dest.put(key, mergeJSONArrays(((JSONArray) dest.get(key)), ((JSONArray) source.get(key))));
                }
                else{
                    System.out.println("pumba");
                }
            }
            else{
                dest.put(key, source.get(key));
            }
        }

        return dest;
    }

    private static JSONArray mergeJSONArrays(JSONArray dest, JSONArray source) throws JSONException {
        for (int i = 0; i < source.length(); i++) {
            boolean has = false;
            for (int j = 0; j < dest.length(); j++) {
                if(dest.get(j).equals(source.get(i))){
                    has = true;
                }
            }
            if(!has){
                dest.put(source.get(i));
            }
        }
        return dest;
    }

    private JSONObject getAlgoFromName(String algorithmDefinition) throws JSONException {
        JSONObject algo = new JSONObject();
        String algoDef = algorithmDefinition.toUpperCase();
        String algoName = algoDef.split("-")[0].split("_")[0];
        algo.put("name", algoDef);
        algo.put("type", "unknown");
        algo.put("simple_name", algoName);
        if (algoDef.contains("/")){
            if(algoDef.split("/").length > 1){
                JSONObject jmodes = new JSONObject();
                String[] sls = algoDef.split("/");
                JSONObject jMode = new JSONObject();
                if(sls.length > 2){
                    JSONArray jpads = new JSONArray();
                    jpads.put(sls[2]);
                    jMode.put("paddings", jpads);
                }
                jmodes.put(sls[1], jMode);
                algo.put("modes", jmodes);
                algo.put("simple_name", sls[0]);
            }

        }
        if(algoDef.split("-").length > 1){
            String[] sls = algoDef.split("-");
            JSONArray keys = new JSONArray();
            keys.put(sls[1].split("/")[0].split("_")[0]);
            algo.put("key_sizes", keys);
        }
        if(algoDef.split("_").length > 1){
            String[] sls = algoDef.split("_");
            JSONArray keys = new JSONArray();
            keys.put(sls[1].split("/")[0].split("-")[0]);
            algo.put("key_sizes", keys);
        }
        return algo;
    }
}
