package net.ifis.proofofvisitclient.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;

public class DeleteWalletFragment extends Fragment {

    private TextView infoTextTV;
    private Button yesBtn;
    private Button noBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_wallet, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByIds(view);

        String infoText = "Delete wallet with address " + MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS) + " ?";
        infoTextTV.setText(infoText);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.walletManager.deleteWallet(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));

                if(MainActivity.walletManager.isDirectoryEmpty()) {
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE);
                } else {
                    String selectedWallet = MainActivity.walletManager.getWalletNames()[0];
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, selectedWallet);
                }

                WalletManagerFragment walletManagerFragment = new WalletManagerFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, walletManagerFragment).commit();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletManagerFragment walletManagerFragment = new WalletManagerFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, walletManagerFragment).commit();
            }
        });

    }


    private void findViewByIds(View view) {
        infoTextTV = view.findViewById(R.id.infoTextTv);
        yesBtn = view.findViewById(R.id.yesBtn);
        noBtn = view.findViewById(R.id.noBtn);
    }
}
