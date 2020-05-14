package net.ifis.proofofvisitclient.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;

public class AddWalletFragment extends Fragment {

    private Button createBtn;
    private EditText passwordFirstInput;
    private EditText passwordSecondInput;
    private TextView infoTextTv;
    private Button tryAgainBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_wallet, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByIds(view);

        infoTextTv.setVisibility(View.INVISIBLE);
        tryAgainBtn.setVisibility(View.INVISIBLE);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordFirstInput.getText().toString().equals(passwordSecondInput.getText().toString())) {

                    String password = passwordFirstInput.getText().toString();

                    String walletFileName = MainActivity.walletManager.createWallet(password);

                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, walletFileName);
                    MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD, MainActivity.walletManager.encrypt(password));

                    WalletManagerFragment walletManagerFragment = new WalletManagerFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.screen_area, walletManagerFragment).commit();

                } else {
                    passwordFirstInput.setVisibility(View.INVISIBLE);
                    passwordSecondInput.setVisibility(View.INVISIBLE);
                    createBtn.setVisibility(View.INVISIBLE);

                    infoTextTv.setVisibility(View.VISIBLE);
                    tryAgainBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                passwordFirstInput.setText("");
                passwordSecondInput.setText("");

                passwordFirstInput.setVisibility(View.VISIBLE);
                passwordSecondInput.setVisibility(View.VISIBLE);
                createBtn.setVisibility(View.VISIBLE);

                infoTextTv.setVisibility(View.INVISIBLE);
                tryAgainBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void findViewByIds(View view) {
        createBtn = view.findViewById(R.id.createBtn);
        tryAgainBtn = view.findViewById(R.id.tryAgainBtn);
        infoTextTv = view.findViewById(R.id.infoTextTv);
        passwordFirstInput = view.findViewById(R.id.passwordFirstInput);
        passwordSecondInput = view.findViewById(R.id.passwordSecondInput);
    }
}
