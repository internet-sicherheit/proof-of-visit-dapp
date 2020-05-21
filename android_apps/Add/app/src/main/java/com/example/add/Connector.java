package com.example.add;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Connector extends AsyncTask {
    private Credentials credentials;

    public Connector (Credentials credentials){
        this.credentials = credentials;

    }
    @Override
    protected Object doInBackground(Object[] objects) {

        // Values for the own gas provider
        BigInteger GAS_LIMIT = BigInteger.valueOf(4300000);
        BigInteger GAS_PRICE = BigInteger.valueOf(22000);

        // create own gas provider
        OwnGasProvider ownGasProvider = new OwnGasProvider(GAS_PRICE, GAS_LIMIT);


            Web3j web3 = Web3j.build(new HttpService("https://core.bloxberg.org/"));
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Log.d("debug", clientVersion);


        // Contract address in Bloxberg
        String contractAddress = "0x0cA8f2CAdC651e865be3da134dF7A3ebEA464B2E";

        Add contract = Add.load(contractAddress,web3,credentials,ownGasProvider);
        try {
            contract.addOne().send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




}
