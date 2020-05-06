package net.ifis.proofofvisitclient.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;

import java.io.File;
import java.io.IOException;

public class WalletManager {

    private final String PATH;
    private Context context;
    private String[] walletNames;
    private Credentials credentials;

    public WalletManager(Context context) {

        this.context = context;
        this.PATH = Environment.getExternalStorageDirectory().getPath()
                + "/Android"
                + Environment.getDataDirectory().getPath()
                + "/net.ifis.proofofvisitclient/wallets/";
        this.walletNames = null;
        this.credentials = null;

        new File(this.PATH).mkdirs();
    }

    public String createWallet(String password) {

        String result = null;

        try {

            ECKeyPair keyPair = Keys.createEcKeyPair();
            WalletFile wallet = Wallet.createLight(password, keyPair);

            String walletFileName = "0x" + wallet.getAddress() + ".json";
            result = "0x" + wallet.getAddress();
            File destination = new File(this.PATH, walletFileName);

            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            objectMapper.writeValue(destination, wallet);

            /*System.out.println("Private key: " + keyPair.getPrivateKey().toString(16));
            System.out.println("Account: " + wallet.getAddress());*/

            Toast.makeText(context, "Written to" + walletFileName, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Credentials loadWallet(String password, String walletName) throws  IOException, CipherException {

        this.credentials = WalletUtils.loadCredentials(password, this.PATH + walletName + ".json");

        return this.credentials;
    }

    public void deleteWallet(String walletName) {
        new File(this.PATH + walletName + ".json").delete();
    }

    public int getNumberOfWallets() {
        Log.d("debug", "getNumberOfWallets -> " + this.walletNames.length);
        return this.walletNames.length;
    }

    public String[] getWalletNames() {

        this.walletNames = new File(this.PATH).list();

        for (int i = 0; i < this.walletNames.length; i++) {
            this.walletNames[i] = this.walletNames[i].substring(0, this.walletNames[i].length() - 5);
        }

        return this.walletNames;
    }

    public boolean isDirectoryEmpty() {
        getWalletNames();
        return getNumberOfWallets() == 0;
    }

    public String encrypt(String pw) {
        String id = "1123581321";
        String result = "";
        byte[] pwByteArray = pw.getBytes();
        byte[] idByteArray = id.getBytes();
        int idCharCounter = 0;
        for (int i = 0; i < pwByteArray.length; i++) {
            pwByteArray[i] = (byte) ((int) pwByteArray[i] + (int) idByteArray[idCharCounter]);
            idCharCounter++;
            if (i == idByteArray.length - 1) {
                idCharCounter = 0;
            }
        }
        for (byte bytes : pwByteArray) {
            result = result + String.format("%02X", bytes);
        }
        return result;
    }

    public String decrypt(String pw) {
        String id = "1123581321";
        byte[] idByteArray = id.getBytes();
        byte[] pwByteArray = new byte[pw.length() / 2];
        for (int i = 0; i < pwByteArray.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(pw.substring(index, index + 2), 16);
            pwByteArray[i] = (byte) j;
        }
        int idCharCounter = 0;
        for (int i = 0; i < pwByteArray.length; i++) {
            pwByteArray[i] = (byte) ((int) pwByteArray[i] - (int) idByteArray[idCharCounter]);
            idCharCounter++;
            if (i == idByteArray.length - 1) {
                idCharCounter = 0;
            }
        }
        return new String(pwByteArray);
    }
}
