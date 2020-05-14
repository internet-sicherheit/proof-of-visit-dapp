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
import net.ifis.proofofvisitclient.fragments.UnlockWalletFragment;
import net.ifis.proofofvisitclient.fragments.WalletManagerFragment;

import java.util.ArrayList;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WalletManagerView> items;

    private Context context;
    private String adapterMode;

    private int listLength;

    public Adapter(Context context, int listLength, String adapterMode) {
        this.items = new ArrayList<>();
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
                        walletManagerView.getCheckBoxIv().setVisibility(View.VISIBLE);
                        String selectedWalletAddress = walletManagerView.getWalletAddressTv().getText().toString();
                        sharedPref.add(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS, selectedWalletAddress);


                        UnlockWalletFragment unlockWalletFragment = new UnlockWalletFragment();
                        FragmentManager fragmentManager = MainActivity.fragmentManager;
                        fragmentManager.beginTransaction().replace(R.id.screen_area, unlockWalletFragment).commit();

                        Toast.makeText(context, selectedWalletAddress, Toast.LENGTH_SHORT).show();
                    }
                });
                items.add(walletManagerView);
        }

        if(this.adapterMode.equals(AdapterMode.TOKENVIEW)) {


        }
    }

    private void setItemsInvisable() {
        for (WalletManagerView item : items) {
            item.getCheckBoxIv().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listLength;
    }
}
