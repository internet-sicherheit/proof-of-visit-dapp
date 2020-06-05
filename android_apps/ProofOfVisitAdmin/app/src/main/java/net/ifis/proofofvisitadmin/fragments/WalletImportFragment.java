package net.ifis.proofofvisitadmin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;
import net.ifis.proofofvisitadmin.model.WalletManager;

public class WalletImportFragment extends Fragment {

    private TextView infoTv;
    private TextView walletAddressTv;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_import, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoTv = view.findViewById(R.id.infoTv);
        walletAddressTv = view.findViewById(R.id.transactionTextview);

        MainActivity.walletManager = new WalletManager(getContext());

        Log.d("pw", MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD));

        if(!MainActivity.walletManager.isDirectoryEmpty() && MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            UnlockWalletFragment unlockWalletFragment = new UnlockWalletFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.screen_area, unlockWalletFragment).commit();
        }

        if(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            infoTv.setText("No wallet file found.");
            walletAddressTv.setVisibility(View.INVISIBLE);
        } else {
            walletAddressTv.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
        }
    }
}
