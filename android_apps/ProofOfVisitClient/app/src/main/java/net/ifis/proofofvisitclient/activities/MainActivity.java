package net.ifis.proofofvisitclient.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MenuItem;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.fragments.GenerateQRFragment;
import net.ifis.proofofvisitclient.fragments.TokenListFragment;
import net.ifis.proofofvisitclient.fragments.WalletManagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
}
