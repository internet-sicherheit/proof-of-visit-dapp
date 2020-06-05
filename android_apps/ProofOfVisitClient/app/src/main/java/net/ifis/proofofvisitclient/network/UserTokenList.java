package net.ifis.proofofvisitclient.network;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.RemoteFunctionCall;

public class UserTokenList extends Connector {

    public UserTokenList(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String walletAddress = (String) objects[0];

        String jsonTokenList = null;

        try {
            jsonTokenList = getContract().getUserTokenlist(walletAddress).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonTokenList;
    }
}