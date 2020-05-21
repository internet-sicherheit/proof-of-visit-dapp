package com.example.add;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Credentials credentials;
    String WALLET_NAME;
    final String PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    final String PATH_TWO = Environment.getExternalStorageDirectory().getPath()
            + "/Android"
            + Environment.getDataDirectory().getPath()
            + "/net.ifis.proofofvisitclient/wallets";

    TextView labelSumAddOne, labelNewSum;
    //addOne should start a transaction
    //sum should start a call
    Button addOne, sum;

    int currentSum = 0;
    int clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Connector connector = (Connector) new Connector(credentials).execute();
                break;
            case R.id.sum:
                currentSum = currentSum + clicked;
                clicked = 0;
                labelSumAddOne.setText("clicked " + String.valueOf(clicked) + " times");
                labelNewSum.setText("New Sum" + " " + String.valueOf(currentSum));
        }
    }

    private void writeDataToPath(File path, String fileName, String data) {
        File targetFilePath = new File(path, fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(targetFilePath);
            fos.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (fos != null) {
                try {
                    Toast.makeText(this, "Write to <" + targetFilePath.getAbsolutePath() + "> successfully!", Toast.LENGTH_LONG).show();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Failed to write!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String checkSDCardStatus() {
        String check;
        String SDCardStatus = Environment.getExternalStorageState();

        // MEDIA_UNKNOWN: unrecognized SD card
        // MEDIA_REMOVED: no SD card at all
        // MEDIA_UNMOUNTED: SD card exist but not mounted, not available in Android 4.0+
        // MEDIA_CHECKING: preparing SD card, e.g. powered on and booting
        // MEDIA_MOUNTED: mounted and ready to use
        // MEDIA_MOUNTED_READ_ONLY
        switch (SDCardStatus) {
            case Environment.MEDIA_MOUNTED:
                check = "true";
                return check;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                Toast.makeText(this, "SD card is ready only.", Toast.LENGTH_LONG).show();
                check = "false";
                return check;
            default:
                Toast.makeText(this, "SD card is not available.", Toast.LENGTH_LONG).show();
                check = "false";
                return check;
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
