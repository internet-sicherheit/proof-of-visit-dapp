package net.ifis.proofofvisitadmin.network;

import android.os.AsyncTask;

import net.ifis.proofofvisitadmin.contract.POVToken;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;

public class Connector extends AsyncTask {

    private final String CONTRACTADDRESS = "0x568a86D9aAbDb00a6bbB87BCF7873d28759c0356";
    /*private final BigInteger GASPRICE = BigInteger.valueOf(22000);
    private final BigInteger GASLIMIT = BigInteger.valueOf(4300000);*/
    private final String URL = "https://core.bloxberg.org/";
    private Web3j web3j;
    private Credentials credentials;
    private POVToken contract;

    public Connector(Credentials credentials) {

        this.credentials = credentials;

        try {
            this.web3j = Web3j.build(new HttpService(URL));
            this.contract = POVToken.load(this.CONTRACTADDRESS, this.web3j, this.credentials, new DefaultGasProvider());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public POVToken getContract() {
        return this.contract;
    }

    public Web3j getWeb3j() {
        return this.web3j;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }


}