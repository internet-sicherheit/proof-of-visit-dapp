package net.ifis.proofofvisitclient.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MenuItem;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.constants.SharedPref;
import net.ifis.proofofvisitclient.fragments.GenerateQRFragment;
import net.ifis.proofofvisitclient.fragments.TokenListFragment;
import net.ifis.proofofvisitclient.fragments.WalletManagerFragment;
import net.ifis.proofofvisitclient.model.WalletManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

public class MainActivity extends AppCompatActivity {

    public static SharedPref sharedPref;

    public static WalletManager walletManager;

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBouncyCastle();

        sharedPref = new SharedPref(getSharedPreferences(SharedPref.SHAREDPREFERENCES_FILENAME, Context.MODE_PRIVATE));

        fragmentManager = getSupportFragmentManager();

        walletManager = new WalletManager(getApplicationContext());

        loadTokenViewListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
        if (id == R.id.action_generate_qr) {
            Log.d("debug", "qr-code");

            loadGenerateQRFragment();

            return true;
        }
        if (id == R.id.action_wallet_manager) {
            Log.d("debug", "wallet-manager");

            loadWalletManagerFragment();

            return true;
        }
        if (id == R.id.action_token_list_view) {
            Log.d("debug", "token-list-view");

            loadTokenViewListFragment();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadTokenViewListFragment() {
        TokenListFragment tokenListFragment = new TokenListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, tokenListFragment).commit();
    }

    public void loadWalletManagerFragment() {
        WalletManagerFragment walletManagerFragment = new WalletManagerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, walletManagerFragment).commit();
    }

    public void loadGenerateQRFragment() {
        GenerateQRFragment generateQRFragment = new GenerateQRFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screen_area, generateQRFragment).commit();
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
