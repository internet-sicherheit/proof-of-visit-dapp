package net.ifis.proofofvisitclient.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.adapter.Adapter;
import net.ifis.proofofvisitclient.constants.AdapterMode;
import net.ifis.proofofvisitclient.constants.SharedPref;
import net.ifis.proofofvisitclient.model.Connector;
import net.ifis.proofofvisitclient.model.TokenManager;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

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

        // json string parsen
        tokenManager = new TokenManager("[{\"locationaddress\":\"0x123456789\",\"locationname\":\"Westfaelische Hochschule\",\"tokenname\":\"Westi\",\"tokensymbol\":\"WHS\",\"token\":234},{\"locationaddress\":\"0x987654321\",\"locationname\":\"Institut fuer Internetsicherheit\",\"tokenname\":\"IntSich\",\"tokensymbol\":\"IFIS\",\"token\":6454},{\"locationaddress\":\"0x987654321\",\"locationname\":\"Institut fuer Internetsicherheit\",\"tokenname\":\"IntSich\",\"tokensymbol\":\"IFIS\",\"token\":85714},{\"locationaddress\":\"0x123456789\",\"locationname\":\"Westfaelische Hochschule\",\"tokenname\":\"Westi\",\"tokensymbol\":\"WHS\",\"token\":7894}]");
        tokenManager.showTokens();

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
