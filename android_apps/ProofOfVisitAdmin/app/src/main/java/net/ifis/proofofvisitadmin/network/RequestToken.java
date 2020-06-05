package net.ifis.proofofvisitadmin.network;

import org.web3j.crypto.Credentials;

public class RequestToken extends Connector {

    public RequestToken(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String locationAddress = (String) objects[0];
        String requestAddress = (String) objects[1];

        try {
            getContract().requestToken(locationAddress, requestAddress).send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
