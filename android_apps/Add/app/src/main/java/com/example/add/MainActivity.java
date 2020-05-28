package com.example.add;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Credentials credentials;
    Connector connector;

    TextView labelSumAddOne, labelNewSum;
    //addOne should start a transaction
    //sum should start a call
    Button addOne, getA;

    int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigning variables to elements
        labelNewSum = (TextView) findViewById(R.id.labelNewSum);
        labelSumAddOne = (TextView) findViewById(R.id.labelSumAddOne);
        addOne = (Button) findViewById(R.id.addOne);
        getA = (Button) findViewById(R.id.getA);

        // set OnClickListener
        addOne.setOnClickListener(this);
        getA.setOnClickListener(this);

        setupBouncyCastle();

        /*String fileName = WalletData.createWallet("password");
        Log.d("Wallet", fileName);*/

        try {
            credentials = WalletData.loadWallet("password", "/storage/emulated/0/Download/0x79c776b62418a09b8107a99f7069bb5cea327b88.json");
            Log.d("credentials", credentials.getAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addOne:
                clicked++;
                labelSumAddOne.setText("clicked " + String.valueOf(clicked) + " times");
                connector = (Connector) new Connector(credentials).execute();
                Toast toast = Toast.makeText(getApplicationContext(), "Transaction complete", Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.getA:
                BigInteger sum = connector.getA;
                clicked = 0;
                labelSumAddOne.setText("clicked " + String.valueOf(clicked) + " times");
                labelNewSum.setText("a = " + " " + String.valueOf(sum));
                break;
            default:

        }
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

}
