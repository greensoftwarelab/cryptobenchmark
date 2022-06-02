package com.example.cryptobenchmark.encrypt.symmetric;

import com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.example.cryptobenchmark.misc.Utils.StringToByteArray;
import static com.example.cryptobenchmark.misc.Utils.byteArrayToString;
import static com.example.cryptobenchmark.misc.Utils.getMethod;


public class SymmetricEncrypt {

    Map<String, Set<String>> encrypt_providers = new HashMap<>();
    Map<String, Set<String>> alg_param = new HashMap<>();

    public SymmetricEncrypt() {
        Set<String> basicTuple = new HashSet<>(
                Arrays.asList(
                        "BC",
                        "AndroidOpenSSL",
                        "AndroidKeyStoreBCWorkaround",
                        "AndroidKeyStore",
                        "Empty"
                ));
        // AES no padding
        this.encrypt_providers.put( "AES/CBC/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL", "Empty")));
        this.encrypt_providers.put( "AES/CTR/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL", "Empty")));
        this.encrypt_providers.put( "AES/ECB/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL", "Empty")));
        this.encrypt_providers.put( "AES/GCM/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL", "Empty")));
        // AES PKCS5Padding
        this.encrypt_providers.put( "AES/CBC/PKCS5Padding", new HashSet<>(Arrays.asList("AndroidOpenSSL", "Empty")));
        this.encrypt_providers.put( "AES/ECB/PKCS5Padding", new HashSet<>(Arrays.asList("AndroidOpenSSL", "Empty")));
        // AES PKCS7Padding
        this.encrypt_providers.put( "AES/CBC/PKCS7Padding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround" , "Empty")));
        this.encrypt_providers.put( "AES/ECB/PKCS7Padding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "Empty")));
    }

    public List<String> get_supported_algorithm_modes(String algo){
        return this.encrypt_providers.keySet().stream()
                .filter(x -> x.startsWith(algo))
                .map(z-> z.split("/")[1])
                .collect(Collectors.toList());
    }

    public List<String> get_supported_algorithm_padds(String algo, String mode){
        return this.encrypt_providers.keySet().stream()
                .filter(x -> x.startsWith(String.format("%s/%s", algo, mode)))
                .map(z-> z.split("/")[2])
                .collect(Collectors.toList());
    }

    public Set<String> get_providers_supporting_combo(String algo, String mode, String paddingmode){
        return this.encrypt_providers.get(String.format("%s/%s/%s", algo, mode, paddingmode));
    }

    public Map<String, IvParameterSpec> encrypt_all(String msg, String algorithm, SecretKey privateKey){
        if( this.get_supported_algorithm_modes(algorithm).isEmpty()){
            return null;
        }
        Map<String, IvParameterSpec> x = new HashMap<>();
        for(String cyphermode : this.get_supported_algorithm_modes(algorithm)){
            for(String pd : this.get_supported_algorithm_padds(algorithm, cyphermode)){
                for(String provd : this.get_providers_supporting_combo(algorithm, cyphermode, pd)){
                    Method method = getMethod(this.getClass().getName(),
                            String.format("encrypt_%s", algorithm),
                            new Class[]{ String.class, String.class, String.class, SecretKey.class, String.class});
                    try {
                        // x.add(encrypt_AES(msg, cyphermode, pd, privateKey, provd));
                        Map.Entry<String, IvParameterSpec>  res = (Map.Entry<String, IvParameterSpec>) method.invoke(this, new Object[]{msg, cyphermode, pd, privateKey, provd});
                        x.put(res.getKey(), res.getValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return x;
    }

    public Map<String, IvParameterSpec> encrypt_all(String msg, String algorithm, SecretKey privateKey, String provider){
        if( this.get_supported_algorithm_modes(algorithm).isEmpty()){
            return null;
        }
        Map<String, IvParameterSpec> x = new HashMap<>();
        for(String cyphermode : this.get_supported_algorithm_modes(algorithm)){
            for(String pd : this.get_supported_algorithm_padds(algorithm, cyphermode)){
                if(this.get_providers_supporting_combo(algorithm, cyphermode, pd).contains(provider)){
                    Method method = getMethod(this.getClass().getName(),
                            String.format("encrypt_%s", algorithm),
                            new Class[]{ String.class, String.class, String.class, SecretKey.class, String.class});
                    try {
                        // x.add(encrypt_AES(msg, cyphermode, pd, privateKey, provd));
                        Map.Entry<String, IvParameterSpec>  res = (Map.Entry<String, IvParameterSpec>) method.invoke(this, new Object[]{msg, cyphermode, pd, privateKey, provider});
                        x.put(res.getKey(), res.getValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return x;
    }


    public List<String> encrypt_all(String msg, String algorithm, String mode){
        if( ! this.encrypt_providers.containsKey(algorithm) ){
            return null;
        }
        Set<String> alg_providers = this.encrypt_providers.get(algorithm);
        List<String> x = new ArrayList<>();
        for(String provider : alg_providers){
            Method method = getMethod(this.getClass().getName(), String.format("digest_%s_%s", algorithm, provider),  new Class[]{ String.class});
            try {
                x.add((String) method.invoke(this, msg));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return x;
    }
    public List<String> encrypt_all(String msg, String algorithm, String mode, String padd){
        if( ! this.encrypt_providers.containsKey(algorithm) ){
            return null;
        }
        Set<String> alg_providers = this.encrypt_providers.get(algorithm);
        List<String> x = new ArrayList<>();
        for(String provider : alg_providers){
            Method method = getMethod(this.getClass().getName(), String.format("digest_%s_%s", algorithm, provider),  new Class[]{ String.class});
            try {
                x.add((String) method.invoke(this, msg));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return x;
    }


    public static Map.Entry<String,IvParameterSpec> encrypt_AES(String message, String mode, String padding, SecretKey key, String provider){
        if(provider.equals("Empty")){
            return new AbstractMap.SimpleEntry<>(message, new IvParameterSpec(new byte[]{}));
        }
        Cipher cipher = null;
        byte[] ciphertext = null;
        try {
            cipher = Cipher.getInstance(String.format("AES/%s/%s", mode, padding), provider);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = cipher.doFinal(message.getBytes());
        } catch (NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
        Map.Entry<String,IvParameterSpec> entry =
                new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(cipher.getIV()));
        return entry;
    }
}
