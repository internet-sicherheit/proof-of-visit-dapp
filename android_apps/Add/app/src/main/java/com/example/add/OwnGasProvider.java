package com.example.add;

import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class OwnGasProvider extends StaticGasProvider {

    public OwnGasProvider(BigInteger GAS_PRICE, BigInteger GAS_LIMIT ){
        super(GAS_PRICE, GAS_LIMIT);
    }


}
