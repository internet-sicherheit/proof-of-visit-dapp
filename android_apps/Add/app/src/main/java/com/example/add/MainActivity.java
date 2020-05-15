package com.example.add;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView labelSumAddOne, labelNewSum;
    //addOne should start a transaction
    //sum should start a call
    Button addOne, sum;

    int currentSum = 0;
    int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HALLO1", "Hallo");

        Connector connector = (Connector) new Connector().execute();
        Log.d("HALLO2", "Hallo");
        // Values for the own gas provider
        BigInteger GAS_LIMIT = BigInteger.valueOf(4300000);
        BigInteger GAS_PRICE = BigInteger.valueOf(22000);

        // create own gas provider
        OwnGasProvider ownGasProvider = new OwnGasProvider(GAS_PRICE, GAS_LIMIT);
        Log.d("HALLO3", "Hallo");

        try {
            Log.d("HALLO4", "Hallo");
            String file = "C:\\Users\\Cennet\\Master\\ArbeitBlockchain\\DAPPS\\Wallets\\UTC--2020-04-15T11-41-09.546146800Z--40983af4978c7d83ea7d9f74e27dae2957183aaf.json";
            // Load Wallet
            Credentials credentials = WalletUtils.loadCredentials("Hallo1234", file);
            Log.d("HALLO5", "Hallo");
            // Contract address in Bloxberg
            String contractAddress = "0x0cA8f2CAdC651e865be3da134dF7A3ebEA464B2E";

            // Add addContract = Add.load(contractAddress, (Web3j) connector, credentials, ownGasProvider);
            //  BigInteger value = addContract.a().send();
            // } catch (IOException e) {
            //   e.printStackTrace();
            // } catch (CipherException e) {
            //    e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Log.d("A_VALUE", String.valueOf(value));


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
        Log.d("WALLET", "TEST");
        ECKeyPair keyPair = Keys.createEcKeyPair();
        WalletFile wallet = Wallet.createStandard("Hallo12345678", keyPair);


        String fileName = WalletUtils.generateNewWalletFile("test1234", new File("C:\\Users\\Cennet\\Master\\ArbeitBlockchain\\DAPPS\\Wallets"), true);


    }

}