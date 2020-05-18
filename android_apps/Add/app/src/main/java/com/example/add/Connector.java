package com.example.add;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.crypto.CipherException;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Connector extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            Web3j web3 = Web3j.build(new HttpService("https://core.bloxberg.org/"));
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Log.d("debug", clientVersion);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createWallet() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {

        Log.d("WALLET", "TEST");
        ECKeyPair keyPair = Keys.createEcKeyPair();
        WalletFile wallet = Wallet.createStandard("Hallo12345678", keyPair);

        String fileName = WalletUtils.generateNewWalletFile("test1234", new File("C:/Users/Cennet/Master/ArbeitBlockchain/DAPPS/Wallets"), true);
        Log.d("WALLETDONE","TEST");
    }

    public static void main(String[] args) throws Exception{
        createWallet();
    }
}
