package net.ifis.proofofvisitclient.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.adapter.Adapter;
import net.ifis.proofofvisitclient.constants.Constant;
import net.ifis.proofofvisitclient.constants.Mode;
import net.ifis.proofofvisitclient.model.WalletManager;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;

public class WalletManagerFragment extends Fragment {

    ImageButton plusImgBtn;
    ImageButton minusImgBtn;

    TextView walletManagerTv;

    public static WalletManager walletManager;

    public static RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_manager, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByIds(view);
        setUpRecyclerView(view);

        Log.d("debug", "SharedPrefs: " + sharedPref.getString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE));

        walletManager = new WalletManager(getContext());
        walletManager.getWalletNames();

        if(walletManager.isDirectoryEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            walletManagerTv.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            walletManagerTv.setVisibility(View.INVISIBLE);
        }

        rvAdapter = new Adapter(getContext(), walletManager.getNumberOfWallets(), Mode.WALLETMANGER);
        recyclerView.setAdapter(rvAdapter);

        plusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWalletFragment addWalletFragment = new AddWalletFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, addWalletFragment).commit();
            }
        });

        minusImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteWalletFragment deleteWalletFragment = new DeleteWalletFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.screen_area, deleteWalletFragment).commit();
            }
        });

    }

    private void setUpRecyclerView(View view) {
        // init recyclerView
        rvLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rvLayoutManager);
    }

    private void findViewByIds(View view) {
        plusImgBtn = view.findViewById(R.id.plusImgBtn);
        minusImgBtn = view.findViewById(R.id.minusImgBtn);
        walletManagerTv = view.findViewById(R.id.wallet_manager_tv);
        recyclerView = view.findViewById(R.id.recyclerView);

        if(sharedPref.getString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE).equals(Constant.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            walletManagerTv.setVisibility(View.INVISIBLE);
        }
    }



}
