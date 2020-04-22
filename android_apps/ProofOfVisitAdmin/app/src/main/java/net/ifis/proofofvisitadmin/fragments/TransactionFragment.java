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

public class TransactionFragment extends Fragment {

    TextView transactionTextview;
    Button continueBtn;
    Button abortBtn;

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
        continueBtn = view.findViewById(R.id.continueBtn);
        abortBtn = view.findViewById(R.id.abortBtn);

        transactionTextview.setText(transactionTextview.getText() + MainActivity.pref.getString("qrcode", "undefined"));

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // WEB3J TRANSACTION !!!

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
