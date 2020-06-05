package net.ifis.proofofvisitadmin.fragments;

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
import androidx.navigation.fragment.NavHostFragment;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;

public class LocationSettingsFragment extends Fragment {

    private TextView infoTv;

    private TextView locationNameTv;
    private TextView tokenNameTv;
    private TextView tokenSymbolTv;

    private EditText locationNameInput;
    private EditText tokenNameInput;
    private EditText tokenSymbolInput;

    private Button saveBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_settings, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewById(view);

        if(MainActivity.walletManager.isDirectoryEmpty()) {
            setAllInvisible();
        } else {
            infoTv.setVisibility(View.INVISIBLE);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationName = locationNameInput.getText().toString();
                String tokenName = tokenNameInput.getText().toString();
                String tokenSymbol = tokenSymbolInput.getText().toString();

                if(!locationName.isEmpty() && !tokenName.isEmpty() && !tokenSymbol.isEmpty()) {

                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_LOCATION_NANME, locationName);
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_TOKEN_NAME, tokenName);
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_TOKEN_SYMBOL, tokenSymbol);

                    /*WalletImportFragment walletImportFragment = new WalletImportFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.screen_area, walletImportFragment).commit();*/

                    Toast.makeText(getContext(), "Location saved.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Fill out all inputs.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setAllInvisible() {
        locationNameTv.setVisibility(View.INVISIBLE);
        tokenNameTv.setVisibility(View.INVISIBLE);
        tokenSymbolTv.setVisibility(View.INVISIBLE);

        locationNameInput.setVisibility(View.INVISIBLE);
        tokenNameInput.setVisibility(View.INVISIBLE);
        tokenSymbolInput.setVisibility(View.INVISIBLE);

        saveBtn.setVisibility(View.INVISIBLE);
    }

    private void findViewById(View view) {
        infoTv = view.findViewById(R.id.infoTv);

        locationNameTv = view.findViewById(R.id.locationNameTv);
        tokenNameTv = view.findViewById(R.id.tokenNameTv);
        tokenSymbolTv = view.findViewById(R.id.tokenSymbolTv);

        locationNameInput = view.findViewById(R.id.locationNameInput);
        tokenNameInput = view.findViewById(R.id.tokenNameInput);
        tokenSymbolInput = view.findViewById(R.id.tokenSymbolInput);

        saveBtn = view.findViewById(R.id.saveBtn);
    }

}
