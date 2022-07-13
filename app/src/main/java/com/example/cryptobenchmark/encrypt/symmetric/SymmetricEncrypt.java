package com.example.cryptobenchmark.encrypt.symmetric;

import com.example.cryptobenchmark.keygen.symmetric.SymmetricKeyGen;
import com.example.cryptobenchmark.misc.CryptoPrimitive;
import com.example.cryptobenchmark.misc.CryptoProvider;
import com.example.cryptobenchmark.misc.DeviceCryptoPrimitives;
import com.example.cryptobenchmark.misc.datatypes.StringType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.AbstractMap;
import java.util.Arrays;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import static com.example.cryptobenchmark.misc.Utils.byteArrayToString;
import static com.example.cryptobenchmark.misc.Utils.getMethod;


public class SymmetricEncrypt {

    Map<String, Set<String>> encrypt_providers = new HashMap<>();
    private static Set<String> symmetric_primitives = new HashSet<>(
            Arrays.asList("AES", "DES", "BLOWFISH", "ARC4")
    );

    public SymmetricEncrypt(DeviceCryptoPrimitives dcp){
        for (CryptoProvider cp : dcp.getDeviceProviders().values()){
            for (String algoId : symmetric_primitives){
                List<String> matchingPrimitives =  cp.getProviderPrimitives().values().stream().filter(x -> x.getSimpleName().matches(""+algoId+".*")).map(CryptoPrimitive::getPrimitiveName).collect(Collectors.toList());
                for (String s : matchingPrimitives){
                    addPrimitive(s, cp.getProviderName());
                }
            }
        }
    }

    public void addPrimitive(String primitiveName, String primitiveProvider){
        if(this.encrypt_providers.containsKey(primitiveName)){
            this.encrypt_providers.get(primitiveName).add(primitiveProvider);
        }
        else{
            this.encrypt_providers.put(primitiveName, new HashSet<>(Collections.singletonList(primitiveProvider)));
        }
    }

    /*
    public SymmetricEncrypt() {
        // AES

        // AES no padding
        //this.encrypt_providers.put( "AES/CBC/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL")));
        this.encrypt_providers.put("AES/CTR/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL")));
        //this.encrypt_providers.put( "AES/ECB/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL")));
        this.encrypt_providers.put("AES/GCM/NoPadding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround", "AndroidOpenSSL")));
        // AES PKCS5Padding
        this.encrypt_providers.put("AES/CBC/PKCS5Padding", new HashSet<>(Arrays.asList("AndroidOpenSSL")));
        this.encrypt_providers.put("AES/ECB/PKCS5Padding", new HashSet<>(Arrays.asList("AndroidOpenSSL")));
        // AES PKCS7Padding
        this.encrypt_providers.put("AES/CBC/PKCS7Padding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround")));
        //this.encrypt_providers.put( "AES/ECB/PKCS7Padding", new HashSet<>(Arrays.asList("AndroidKeyStoreBCWorkaround")));
        // DES
        // DES no padding
        this.encrypt_providers.put("DES/CBC/NoPadding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/CTR/NoPadding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/ECB/NoPadding", new HashSet<>(Arrays.asList("BC")));
        //this.encrypt_providers.put( "DES/GCM/NoPadding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/OFB/NoPadding", new HashSet<>(Arrays.asList("BC")));

        // DES PKCS5Padding
        this.encrypt_providers.put("DES/CBC/PKCS5Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/CTR/PKCS5Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/ECB/PKCS5Padding", new HashSet<>(Arrays.asList("BC")));
        //this.encrypt_providers.put( "DES/GCM/PKCS5Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/OFB/PKCS5Padding", new HashSet<>(Arrays.asList("BC")));

        // DES PKCS7Padding
        this.encrypt_providers.put("DES/CBC/PKCS7Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/CTR/PKCS7Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/ECB/PKCS7Padding", new HashSet<>(Arrays.asList("BC")));
        //this.encrypt_providers.put( "DES/GCM/PKCS7Padding", new HashSet<>(Arrays.asList("BC")));
        this.encrypt_providers.put("DES/OFB/PKCS7Padding", new HashSet<>(Arrays.asList("BC")));


        // BLOWFISH
        this.encrypt_providers.put("BLOWFISH", new HashSet<>(Arrays.asList("BC")));
        // ARC4
        this.encrypt_providers.put("ARC4", new HashSet<>(Arrays.asList("ARC4")));
    }
*/
    public List<String> get_supported_algorithm_modes(String algo) {
        return this.encrypt_providers.keySet().stream()
                .filter(x -> x.startsWith(algo))
                .map(z -> z.split("/").length > 1 ? z.split("/")[1] : z.split("/")[0])
                .collect(Collectors.toList());
    }

    public List<String> get_supported_algorithm_padds(String algo, String mode) {
        return this.encrypt_providers.keySet().stream()
                .filter(x -> x.startsWith(String.format("%s/%s", algo, mode)))
                .map(z -> z.split("/")[2])
                .collect(Collectors.toList());
    }

    public Set<String> get_providers_supporting_combo(String algo, String mode, String paddingmode) {
        return this.encrypt_providers.get(String.format("%s/%s/%s", algo, mode, paddingmode));
    }

    public Map<String, IvParameterSpec> encrypt_all(String msg, String algorithm, SecretKey privateKey) {
        if (this.get_supported_algorithm_modes(algorithm).isEmpty()) {
            return null;
        }
        Map<String, IvParameterSpec> x = new HashMap<>();
        for (String cyphermode : this.get_supported_algorithm_modes(algorithm)) {
            for (String pd : this.get_supported_algorithm_padds(algorithm, cyphermode)) {
                for (String provd : this.get_providers_supporting_combo(algorithm, cyphermode, pd)) {
                    Method method = getMethod(this.getClass().getName(),
                            String.format("encrypt_%s", algorithm),
                            new Class[]{String.class, String.class, String.class, SecretKey.class, String.class});
                    try {
                        // x.add(encrypt_AES(msg, cyphermode, pd, privateKey, provd));
                        Map.Entry<String, IvParameterSpec> res = (Map.Entry<String, IvParameterSpec>) method.invoke(this, new Object[]{msg, cyphermode, pd, privateKey, provd});
                        x.put(res.getKey(), res.getValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return x;
    }

    public Map<String, IvParameterSpec> encrypt_all(String msg, String algorithm, int keylen) {
        if (this.get_supported_algorithm_modes(algorithm).isEmpty()) {
            return null;
        }
        Map<String, IvParameterSpec> x = new LinkedHashMap<>();
        for (String cyphermode : this.get_supported_algorithm_modes(algorithm)) {
            for (String pd : this.get_supported_algorithm_padds(algorithm, cyphermode)) {
                for (String provd : this.get_providers_supporting_combo(algorithm, cyphermode, pd)) {
                    System.out.println(" a gerar key de  " + keylen + " com provider " + provd + " com modo " + cyphermode + " e padd " + pd);
                    SecretKey sk = getKey(algorithm, keylen, cyphermode, pd, provd);
                    Method method = getMethod(this.getClass().getName(),
                            String.format("encrypt_%s", algorithm),
                            new Class[]{String.class, String.class, String.class, SecretKey.class, String.class});
                    try {
                        // x.add(encrypt_AES(msg, cyphermode, pd, privateKey, provd));
                        Map.Entry<String, IvParameterSpec> res = (Map.Entry<String, IvParameterSpec>) method.invoke(this, new Object[]{msg, cyphermode, pd, sk, provd});
                        x.put(res.getKey(), res.getValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return x;
    }

    public static SecretKey getKey(String algo, int keylen, String mode, String padding, String provider) {
        try {
            Method method = getMethod(SymmetricKeyGen.class.getName(),
                    String.format("gen_key_%s_%s", algo, provider),
                    new Class[]{int.class, String.class, String.class});
            return (SecretKey) method.invoke(null, new Object[]{keylen, mode, padding});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, IvParameterSpec> encrypt_all(String msg, String algorithm, SecretKey privateKey, String provider) {
        if (this.get_supported_algorithm_modes(algorithm).isEmpty()) {
            return null;
        }
        Map<String, IvParameterSpec> x = new HashMap<>();
        for (String cyphermode : this.get_supported_algorithm_modes(algorithm)) {
            for (String pd : this.get_supported_algorithm_padds(algorithm, cyphermode)) {
                if (this.get_providers_supporting_combo(algorithm, cyphermode, pd).contains(provider)) {
                    Method method = getMethod(this.getClass().getName(),
                            String.format("encrypt_%s", algorithm),
                            new Class[]{String.class, String.class, String.class, SecretKey.class, String.class});
                    try {
                        // x.add(encrypt_AES(msg, cyphermode, pd, privateKey, provd));
                        Map.Entry<String, IvParameterSpec> res = (Map.Entry<String, IvParameterSpec>) method.invoke(this, new Object[]{msg, cyphermode, pd, privateKey, provider});
                        x.put(res.getKey(), res.getValue());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return x;
    }

    public static Map.Entry<String, IvParameterSpec> encrypt_AES(String message, String mode, String padding, SecretKey key, String provider) {
        if (provider.equals("Empty")) {
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
        return new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(cipher.getIV()));
    }

    /*
     *  DES
     *   requires key of 8 bytes (64 bits)
     * */
    public static Map.Entry<String, IvParameterSpec> encrypt_DES(String message, String mode, String padding, SecretKey key, String provider) {
        if (provider.equals("Empty")) {
            return new AbstractMap.SimpleEntry<>(message, new IvParameterSpec(new byte[]{}));
        }
        Cipher cipher = null;
        byte[] ciphertext = null;
        try {
            cipher = Cipher.getInstance(String.format("DES/%s/%s", mode, padding), provider);
            if (!mode.equals("ECB")) {
                byte[] iv = new byte[8];
                IvParameterSpec ips = new IvParameterSpec(iv);
                //desCipher.init(Cipher.ENCRYPT_MODE, key, ips);
                cipher.init(Cipher.ENCRYPT_MODE, key, ips);
                ciphertext = cipher.doFinal(message.getBytes());
                return new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(cipher.getIV()));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                ciphertext = cipher.doFinal(message.getBytes());
                return new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(new byte[]{}));
            }

        } catch (NoSuchProviderException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     *  Blowfish
     *   requires key of 8 bytes (64 bits)
     * */
    public static Map.Entry<String, IvParameterSpec> encrypt_BLOWFISH(String message, String mode, String padding, SecretKey key, String provider) {
        if (provider.equals("Empty")) {
            return new AbstractMap.SimpleEntry<>(message, new IvParameterSpec(new byte[]{}));
        }
        Cipher cipher = null;
        byte[] ciphertext = null;
        try {
            cipher = Cipher.getInstance("BLOWFISH", provider);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = cipher.doFinal(message.getBytes());
            return new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(new byte[]{}));


        } catch (NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map.Entry<String, IvParameterSpec> encrypt_ARC4(String message, String mode, String padding, SecretKey key, String provider) {
        if (provider.equals("Empty")) {
            return new AbstractMap.SimpleEntry<>(message, new IvParameterSpec(new byte[]{}));
        }
        Cipher cipher = null;
        byte[] ciphertext = null;
        try {
            cipher = Cipher.getInstance("ARC4", provider);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = cipher.doFinal(message.getBytes());
            return new AbstractMap.SimpleEntry<>(byteArrayToString(ciphertext), new IvParameterSpec(new byte[]{}));


        } catch (NoSuchProviderException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

}