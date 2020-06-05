package net.ifis.proofofvisitadmin.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.kenai.jffi.Main;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;
import net.ifis.proofofvisitadmin.model.WalletManager;
import net.ifis.proofofvisitadmin.network.RequestToken;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;

public class TransactionFragment extends Fragment {

    private TextView receiverAddress;
    private TextView transactionTextview;
    private Button continueBtn;
    private Button abortBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionTextview = view.findViewById(R.id.transactionTextview);
        receiverAddress = view.findViewById(R.id.receiverAddress);
        continueBtn = view.findViewById(R.id.continueBtn);
        abortBtn = view.findViewById(R.id.abortBtn);

        receiverAddress.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_RECEIVING_ADDRESS));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // WEB3J TRANSACTION !!!
                try {
                    String pw = MainActivity.walletManager.decrypt(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD));
                    Credentials credentials = MainActivity.walletManager.loadWallet(pw, MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
                    new RequestToken(credentials).execute(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS),MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_RECEIVING_ADDRESS));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CipherException e) {
                    e.printStackTrace();
                }

                InformationFragment informationFragment = new InformationFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, informationFragment).commit();
            }
        });

        abortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanQRCodeFragment scanQRCodeFragment = new ScanQRCodeFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, scanQRCodeFragment).commit();
            }
        });
    }
}
