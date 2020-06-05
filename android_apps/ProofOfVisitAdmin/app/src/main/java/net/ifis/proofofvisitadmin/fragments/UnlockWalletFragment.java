package net.ifis.proofofvisitadmin.fragments;

import android.os.Bundle;
import android.util.Log;
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

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;

import org.web3j.crypto.CipherException;

import java.io.IOException;

public class UnlockWalletFragment extends Fragment {

    private TextView walletAddressTv;
    private EditText pwInputText;
    private Button unlockBtn;

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

        walletAddressTv = view.findViewById(R.id.walletAddressTv);
        pwInputText = view.findViewById(R.id.pwInputText);
        unlockBtn = view.findViewById(R.id.unlockBtn);

        String[] walletNames = MainActivity.walletManager.getWalletNames();
        walletAddressTv.setText(walletNames[0]);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pw = pwInputText.getText().toString();
                    String encryptedPw = MainActivity.walletManager.encrypt(pw);

                    MainActivity.credentials = MainActivity.walletManager.loadWallet(pw, walletNames[0]);

                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, walletNames[0]);
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD, encryptedPw);

                    WalletImportFragment walletImportFragment = new WalletImportFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.screen_area, walletImportFragment).commit();

                    Toast.makeText(getContext(), "Success.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No wallet file found.", Toast.LENGTH_SHORT).show();
                } catch (CipherException e) {
                    e.printStackTrace();
                    pwInputText.setText("");
                    Toast.makeText(getContext(), "Wrong password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}