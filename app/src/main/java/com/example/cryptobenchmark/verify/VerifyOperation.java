package com.example.cryptobenchmark.verify;

import java.security.PublicKey;

public interface VerifyOperation {

    boolean verify(String msg, String signature, String algo, PublicKey pubkey, String provider);

}
