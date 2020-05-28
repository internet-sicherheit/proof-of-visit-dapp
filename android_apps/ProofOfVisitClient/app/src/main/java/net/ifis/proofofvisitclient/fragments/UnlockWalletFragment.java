package net.ifis.proofofvisitclient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;

import org.web3j.crypto.CipherException;

import java.io.IOException;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;

public class UnlockWalletFragment extends Fragment {

    public static View walletItemView;
    public static String selectedWalletAddress;

    private TextView walletAddressTv;
    private EditText passwordInput;
    private Button unlockBtn;
    private Button tryAgainBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unlock_wallet, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByIds(view);

        walletAddressTv.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));

        tryAgainBtn.setVisibility(View.INVISIBLE);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String walletAddress = MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS);
                String password = passwordInput.getText().toString();

                try {

                    MainActivity.walletManager.loadWallet(password, walletAddress);
                    walletItemView.setVisibility(View.VISIBLE);
                    sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, selectedWalletAddress);
                    // encrypted pw speichern

                } catch (IOException ioe) {
                    Toast.makeText(getContext(), "Walletfile not found.", Toast.LENGTH_SHORT).show();
                    ioe.printStackTrace();
                } catch (CipherException ce) {
                    Toast.makeText(getContext(), "Wrong password.", Toast.LENGTH_SHORT).show();
                    ce.printStackTrace();
                }

                WalletManagerFragment walletManagerFragment = new WalletManagerFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, walletManagerFragment).commit();
            }
        });

        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnlockWalletFragment unlockWalletFragment = new UnlockWalletFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, unlockWalletFragment).commit();
            }
        });
    }

    private void findViewByIds(View view) {
        walletAddressTv = view.findViewById(R.id.walletAddressTv);
        passwordInput = view.findViewById(R.id.passwordInput);
        unlockBtn = view.findViewById(R.id.unlockBtn);
        tryAgainBtn = view.findViewById(R.id.tryAgainBtn);
    }
}
