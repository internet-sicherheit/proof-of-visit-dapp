package net.ifis.proofofvisitclient.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.adapter.Adapter;
import net.ifis.proofofvisitclient.constants.AdapterMode;
import net.ifis.proofofvisitclient.constants.SharedPref;
import net.ifis.proofofvisitclient.model.TokenManager;
import net.ifis.proofofvisitclient.network.UserTokenList;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;

public class TokenListFragment extends Fragment {

    public static TokenManager tokenManager;

    private TextView infoText;
    public static RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_token_list_view, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByIds(view);
        setUpRecyclerView(view);

        if(!sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            try {
                String pw = MainActivity.walletManager.decrypt(sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_PASSWORD));
                Credentials credentials = MainActivity.walletManager.loadWallet(pw, sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS));
                String jsonTokenList = (String) new UserTokenList(credentials).execute(sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS)).get();

                Log.d("tokenlist", jsonTokenList);

                // json string parsen
                tokenManager = new TokenManager(jsonTokenList);
                tokenManager.showTokens();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(tokenManager.getTokenListSize() == 0) {
                infoText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);

            } else {
                rvAdapter = new Adapter(getContext(), tokenManager.getTokenListSize(), AdapterMode.TOKENVIEW);
                recyclerView.setAdapter(rvAdapter);

                infoText.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }



    }

    private void setUpRecyclerView(View view) {
        // init recyclerView
        rvLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(rvLayoutManager);
    }

    private void findViewByIds(View view) {
        infoText = view.findViewById(R.id.infoText);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}
