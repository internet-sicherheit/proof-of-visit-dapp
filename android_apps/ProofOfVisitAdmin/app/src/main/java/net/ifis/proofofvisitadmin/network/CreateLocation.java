package net.ifis.proofofvisitadmin.network;

import org.web3j.crypto.Credentials;

public class CreateLocation extends Connector {

    public CreateLocation(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String tokenName = (String) objects[0];
        String tokenSymbol = (String) objects[1];
        String locationName = (String) objects[2];
        String locationAddress = (String) objects[3];

        try {
            getContract().createLocation(tokenName, tokenSymbol, locationName, locationAddress).send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
