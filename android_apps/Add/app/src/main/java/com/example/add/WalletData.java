package com.example.add;

import android.os.Environment;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class WalletData {
    final static String PATH = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();

    public static String createWallet(String password) {

        String result = null;

        try {
            ECKeyPair keyPair = Keys.createEcKeyPair();
            WalletFile wallet = org.web3j.crypto.Wallet.createLight(password, keyPair);

            String walletFileName = "0x" + wallet.getAddress() + ".json";

            File destination = new File(PATH, walletFileName);

            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            objectMapper.writeValue(destination, wallet);

            result = PATH + "/" + walletFileName;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Credentials loadWallet(String password, String walletFile) throws IOException, CipherException {
        return WalletUtils.loadCredentials(password,  walletFile );
    }

}
