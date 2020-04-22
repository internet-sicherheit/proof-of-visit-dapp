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
import net.ifis.proofofvisitadmin.fragments.FaucetBergsFragment;
import net.ifis.proofofvisitadmin.fragments.InformationFragment;
import net.ifis.proofofvisitadmin.fragments.LocationSettingsFragment;
import net.ifis.proofofvisitadmin.fragments.ScanQRCodeFragment;
import net.ifis.proofofvisitadmin.fragments.WalletImportFragment;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        loadInformationFragment();
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
}
