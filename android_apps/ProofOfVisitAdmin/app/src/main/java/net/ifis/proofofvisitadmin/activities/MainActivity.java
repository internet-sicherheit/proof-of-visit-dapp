package net.ifis.proofofvisitadmin.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.constants.SharedPref;
import net.ifis.proofofvisitadmin.fragments.FaucetBergsFragment;
import net.ifis.proofofvisitadmin.fragments.InformationFragment;
import net.ifis.proofofvisitadmin.fragments.LocationSettingsFragment;
import net.ifis.proofofvisitadmin.fragments.ScanQRCodeFragment;
import net.ifis.proofofvisitadmin.fragments.UnlockWalletFragment;
import net.ifis.proofofvisitadmin.fragments.WalletImportFragment;
import net.ifis.proofofvisitadmin.model.WalletManager;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static SharedPref sharedPref;
    public static WalletManager walletManager;
    public static Credentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        walletManager = new WalletManager(getApplicationContext());
        sharedPref = new SharedPref(getApplicationContext().getSharedPreferences(SharedPref.SHAREDPREFERENCES_FILENAME, 0));

        loadInformationFragment();

        if(!sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            try {
                walletManager.loadWallet(walletManager.decrypt(sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD)),sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadInformationFragment() {
        InformationFragment informationFragment = new InformationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, informationFragment).commit();
    }

    public void loadFaucetBergsFragment() {
        FaucetBergsFragment faucetBergsFragment = new FaucetBergsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, faucetBergsFragment).commit();
    }

    public void loadLocationSettingsFragment() {
        LocationSettingsFragment locationSettingsFragment = new LocationSettingsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, locationSettingsFragment).commit();
    }

    public void loadWalletImport() {
        WalletImportFragment walletImportFragment = new WalletImportFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, walletImportFragment).commit();
    }

    public void loadScanQRCodeFragment() {
        ScanQRCodeFragment scanQRCodeFragment = new ScanQRCodeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, scanQRCodeFragment).commit();
    }

    public static boolean isAllInformationAvailable() {

        boolean isAllInformationAvailable = false;

        if (!MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)
                && !MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)
                && !MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_LOCATION_NANME).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)
                && !MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_TOKEN_NAME).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)
                && !MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_TOKEN_SYMBOL).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            isAllInformationAvailable = true;
        }
        return isAllInformationAvailable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information) {
            Log.d("debug", "information");

            loadInformationFragment();

            return true;
        }
        if (id == R.id.action_faucet_bergs) {
            Log.d("debug", "faucetbergs");

            loadFaucetBergsFragment();

            return true;
        }
        if (id == R.id.action_location_settings) {
            Log.d("debug", "location settings");

            loadLocationSettingsFragment();

            return true;
        }
        if (id == R.id.action_wallet_import) {
            Log.d("debug", "wallet import");

            loadWalletImport();

            return true;
        }
        if (id == R.id.action_scan_qrcode) {
            Log.d("debug", "scan qr-code");

            loadScanQRCodeFragment();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("life", "resume");

        if(walletManager.isDirectoryEmpty()) {
            sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
            sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
            sharedPref.add(SharedPref.SHAREDPREFERENCES_LOCATION_NANME, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
            sharedPref.add(SharedPref.SHAREDPREFERENCES_TOKEN_NAME, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
            sharedPref.add(SharedPref.SHAREDPREFERENCES_TOKEN_SYMBOL, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);

        }

    }


    @Override
    public void onStop() {
        super.onStop();
    }
}
