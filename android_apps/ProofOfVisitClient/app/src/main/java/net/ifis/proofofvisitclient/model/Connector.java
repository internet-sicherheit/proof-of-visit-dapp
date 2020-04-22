package net.ifis.proofofvisitclient.model;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

public class Connector extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            Web3j web3 = Web3j.build(new HttpService("https://core.bloxberg.org/"));
            Log.d("debug", "hallo1");
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
            Log.d("debug", "hallo2");
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Log.d("debug", "hallo3");
            Log.d("debug", clientVersion);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
