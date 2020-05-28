package net.ifis.proofofvisitclient.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.SharedPref;
import net.ifis.proofofvisitclient.constants.AdapterMode;
import net.ifis.proofofvisitclient.fragments.TokenListFragment;
import net.ifis.proofofvisitclient.fragments.UnlockWalletFragment;
import net.ifis.proofofvisitclient.fragments.WalletManagerFragment;
import net.ifis.proofofvisitclient.model.Token;

import java.util.ArrayList;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WalletManagerView> itemsWallet;
    private ArrayList<TokenListView> itemsToken;

    private Context context;
    private String adapterMode;

    private int listLength;

    public Adapter(Context context, int listLength, String adapterMode) {
        this.itemsWallet = new ArrayList<>();
        this.itemsToken = new ArrayList<>();
        this.context = context;
        this.listLength = listLength;
        this.adapterMode = adapterMode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder viewHolder = null;
        View itemView;

        if(this.adapterMode.equals(AdapterMode.WALLETMANGER)) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallet_manager, null);
            viewHolder = new WalletManagerView(itemView);
        }

        if(this.adapterMode.equals(AdapterMode.TOKENVIEW)) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_token_list, null);
            viewHolder = new TokenListView(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if(this.adapterMode.equals(AdapterMode.WALLETMANGER)) {

            WalletManagerView walletManagerView = (WalletManagerView) viewHolder;

            String walletName = WalletManagerFragment.walletManager.getWalletNames()[i];
            walletManagerView.getWalletAddressTv().setText(walletName);

                if(sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS).equals(walletName)) {
                    walletManagerView.getCheckBoxIv().setVisibility(View.VISIBLE);
                }

                walletManagerView.getWalletAddressTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setItemsInvisable();
                        String selectedWalletAddress = walletManagerView.getWalletAddressTv().getText().toString();

                        UnlockWalletFragment.walletItemView = v;
                        UnlockWalletFragment.selectedWalletAddress = selectedWalletAddress;

                        UnlockWalletFragment unlockWalletFragment = new UnlockWalletFragment();
                        FragmentManager fragmentManager = MainActivity.fragmentManager;
                        fragmentManager.beginTransaction().replace(R.id.screen_area, unlockWalletFragment).commit();

                        Toast.makeText(context, selectedWalletAddress, Toast.LENGTH_SHORT).show();
                    }
                });
            itemsWallet.add(walletManagerView);
        }

        if(this.adapterMode.equals(AdapterMode.TOKENVIEW)) {

            TokenListView tokenListView = (TokenListView) viewHolder;

            ArrayList<Token> tokenList = TokenListFragment.tokenManager.getTokenList();

            tokenListView.getLocationNameTv().setText(tokenList.get(i).getLocationName());
            tokenListView.getTokenAmountTv().setText("" + tokenList.get(i).getTokenAmount());
            tokenListView.getTokenSymbolTv().setText(tokenList.get(i).getTokenSymbol());

            itemsToken.add(tokenListView);
        }
    }

    private void setItemsInvisable() {
        for (WalletManagerView item : itemsWallet) {
            item.getCheckBoxIv().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listLength;
    }
}
