package com.example.add;

import android.os.AsyncTask;
import android.util.Log;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import java.io.IOException;
import java.math.BigInteger;


public class Connector extends AsyncTask {
    private Credentials credentials;

    // variable for call a
    public BigInteger getA;

    public Connector(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        // Values for the own gas provider
        BigInteger GAS_LIMIT = BigInteger.valueOf(4300000);
        BigInteger GAS_PRICE = BigInteger.valueOf(22000);

        // create own gas provider
        OwnGasProvider ownGasProvider = new OwnGasProvider(GAS_PRICE, GAS_LIMIT);

        //Web3j connection
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

        // load contract
        Add contract = Add.load(contractAddress, web3, credentials, ownGasProvider);

        // transaction (smart contract function addOne)
        try {
            contract.addOne().send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // call a
        try {
            getA = contract.a().send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
