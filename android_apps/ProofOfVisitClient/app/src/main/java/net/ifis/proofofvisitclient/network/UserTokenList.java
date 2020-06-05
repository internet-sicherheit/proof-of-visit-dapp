package net.ifis.proofofvisitclient.network;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;

import org.web3j.crypto.Credentials;

public class UserTokenList extends Connector {

    public UserTokenList(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            getContract().getUserTokenlist(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}