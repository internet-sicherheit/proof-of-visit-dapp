package net.ifis.proofofvisitadmin.network;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class RequestBergsAmount extends Connector {

    public RequestBergsAmount(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String walletAddress = (String) objects[0];

        BigDecimal amount = null;

        try {
            EthGetBalance balanceWei = getWeb3j().ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).send();
            amount = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return amount.toString();
    }
}
