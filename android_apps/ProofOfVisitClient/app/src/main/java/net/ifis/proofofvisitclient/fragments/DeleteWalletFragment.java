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
import net.ifis.proofofvisitclient.constants.Constant;

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

        String infoText = "Delete wallet with address " + MainActivity.sharedPref.getString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE) + " ?";
        infoTextTV.setText(infoText);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.walletManager.deleteWallet(MainActivity.sharedPref.getString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE));

                if(MainActivity.walletManager.isDirectoryEmpty()) {
                    SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                    editor.putString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE);
                    editor.commit();
                } else {
                    String selectedWallet = MainActivity.walletManager.getWalletNames()[0];
                    SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                    editor.putString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, selectedWallet);
                    editor.commit();
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
