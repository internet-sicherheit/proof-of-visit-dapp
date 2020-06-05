package net.ifis.proofofvisitadmin.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kenai.jffi.Main;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;
import net.ifis.proofofvisitadmin.network.RequestBergsAmount;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class InformationFragment extends Fragment {

    private TextView infoTv;

    private TextView locationAddressTv;
    private TextView locationAddress;

    private TextView bergsTv;
    private TextView bergs;
    private TextView locationNameTv;
    private TextView locationName;
    private TextView tokenNameTv;
    private TextView tokenName;
    private TextView tokenSymbolTv;
    private TextView tokenSymbol;

    private Button copyAddressBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsById(view);

        if(MainActivity.isAllInformationAvailable()) {

            infoTv.setVisibility(View.INVISIBLE);

            locationAddress.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
            locationName.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_LOCATION_NANME));
            tokenName.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_TOKEN_NAME));
            tokenSymbol.setText(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_TOKEN_SYMBOL));

            // bergs abfragen
            try {
                String pw = MainActivity.walletManager.decrypt(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD));
                Credentials credentials = MainActivity.walletManager.loadWallet(pw, MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
                String bergsAmount = (String) new RequestBergsAmount(credentials).execute(MainActivity.sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS)).get();
                bergs.setText(bergsAmount);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            setAllInvisible();
        }

        copyAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("wallet_address", locationAddress.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(), "Address copied to clipboard.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void findViewsById(View view) {

        infoTv = view.findViewById(R.id.infoTv);

        locationAddressTv = view.findViewById(R.id.locationAddressTv);
        locationAddress = view.findViewById(R.id.locationAddress);

        bergsTv = view.findViewById(R.id.bergsTv);
        bergs = view.findViewById(R.id.bergs);
        locationNameTv = view.findViewById(R.id.locationNameTv);
        locationName = view.findViewById(R.id.locationName);
        tokenNameTv = view.findViewById(R.id.tokenNameTv);
        tokenName = view.findViewById(R.id.tokenName);
        tokenSymbolTv = view.findViewById(R.id.tokenSymbolTv);
        tokenSymbol = view.findViewById(R.id.tokenSymbol);

        copyAddressBtn = view.findViewById(R.id.copyAddressBtn);
    }

    private void setAllInvisible() {
        locationAddressTv.setVisibility(View.INVISIBLE);
        locationAddress.setVisibility(View.INVISIBLE);

        bergsTv.setVisibility(View.INVISIBLE);
        bergs.setVisibility(View.INVISIBLE);
        locationNameTv.setVisibility(View.INVISIBLE);
        locationName.setVisibility(View.INVISIBLE);
        tokenNameTv.setVisibility(View.INVISIBLE);
        tokenName.setVisibility(View.INVISIBLE);
        tokenSymbolTv.setVisibility(View.INVISIBLE);
        tokenSymbol.setVisibility(View.INVISIBLE);

        copyAddressBtn.setVisibility(View.INVISIBLE);
    }
}
