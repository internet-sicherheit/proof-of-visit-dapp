package net.ifis.proofofvisitadmin.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

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
                + "/net.ifis.proofofvisitadmin/wallets/";
        this.walletNames = null;
        this.credentials = null;

        new File(this.PATH).mkdirs();
    }

    public Credentials loadWallet(String password, String walletName) throws IOException, CipherException {

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
        return new File(this.PATH).list().length == 0;
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
