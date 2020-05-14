package com.example.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewPropertyAnimatorListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static java.math.BigInteger.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView labelSumAddOne, labelNewSum;
    //addOne should start a transaction
    //sum should start a call
    Button addOne, sum;

    int currentSum = 0;
    int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST0", "Hallo");
        try {
            Web3j web3 = Web3j.build(new HttpService("https://core.bloxberg.org"));  // defaults to http://localhost:8545/
            Log.d("TEST1", "Hallo");
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();

            Log.d("TEST2", "Hallo");
            // Values for the own gas provider
            BigInteger GAS_LIMIT = BigInteger.valueOf(4300000);
            BigInteger GAS_PRICE = BigInteger.valueOf(22000);
            // create own gas provider
            OwnGasProvider ownGasProvider = new OwnGasProvider(GAS_PRICE, GAS_LIMIT);

            // createWallet();

            // load Wallet
            Credentials credentials = WalletUtils.loadCredentials("Hallo1234", "C:\\Users\\Cennet\\Master\\ArbeitBlockchain\\DAPPS\\Wallets\\UTC--2020-04-15T11-41-09.546146800Z--40983af4978c7d83ea7d9f74e27dae2957183aaf.json");
            // Contract address in Bloxberg
            String contractAddress = "0x0cA8f2CAdC651e865be3da134dF7A3ebEA464B2E";

            Add addContract = Add.load(contractAddress, web3, credentials, ownGasProvider);
            BigInteger value = addContract.a().send();
            Log.d("TEST3", "Hallo");
            // Log.d("A_VALUE", String.valueOf(value));
        } catch (Exception e) {
            Log.e("Hallo","Test",e);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // assigning variables to elements
        labelNewSum = (TextView) findViewById(R.id.labelNewSum);
        labelSumAddOne = (TextView) findViewById(R.id.labelSumAddOne);
        addOne = (Button) findViewById(R.id.addOne);
        sum = (Button) findViewById(R.id.sum);

        // set OnClickListener
        addOne.setOnClickListener(this);
        sum.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addOne:
                clicked++;
                labelSumAddOne.setText("clicked " + String.valueOf(clicked) + " times");
                break;
            case R.id.sum:
                currentSum = currentSum + clicked;
                clicked = 0;
                labelSumAddOne.setText("clicked " + String.valueOf(clicked) + " times");
                labelNewSum.setText("New Sum" + " " + String.valueOf(currentSum));
        }
    }

    public void createWallet() throws Exception {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        WalletFile wallet = Wallet.createStandard("Hallo1234", keyPair);

        System.out.println("Private key: " + keyPair.getPrivateKey().toString(16));
        System.out.println("Account: " + wallet.getAddress());

        String fileName = WalletUtils.generateNewWalletFile("test1234", new File("C:\\Users\\Cennet\\Master\\ArbeitBlockchain\\DAPPS\\Wallets"), true);

        System.out.println("File name: " + fileName);
    }

}
