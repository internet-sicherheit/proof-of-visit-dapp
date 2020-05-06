package net.ifis.proofofvisitclient.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kenai.jffi.Main;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.activities.MainActivity;
import net.ifis.proofofvisitclient.constants.Constant;
import net.ifis.proofofvisitclient.constants.Mode;
import net.ifis.proofofvisitclient.fragments.UnlockWalletFragment;
import net.ifis.proofofvisitclient.fragments.WalletManagerFragment;
import net.ifis.proofofvisitclient.model.WalletManager;

import org.web3j.crypto.Wallet;

import java.lang.reflect.Array;
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

        if(this.adapterMode.equals(Mode.WALLETMANGER)) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wallet_manager, null);
            viewHolder = new WalletManagerView(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if(this.adapterMode.equals(Mode.WALLETMANGER)) {

            WalletManagerView walletManagerView = (WalletManagerView) viewHolder;

            String walletName = WalletManagerFragment.walletManager.getWalletNames()[i];
                walletManagerView.getWalletAddressTv().setText(walletName);

                if(sharedPref.getString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, Constant.SHAREDPREFERENCES_DEFAULT_VALUE).equals(walletName)) {
                    walletManagerView.getCheckBoxIv().setVisibility(View.VISIBLE);
                }

                walletManagerView.getWalletAddressTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setItemsInvisable();
                        walletManagerView.getCheckBoxIv().setVisibility(View.VISIBLE);
                        String selectedWalletAddress = walletManagerView.getWalletAddressTv().getText().toString();
                        SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                        editor.putString(Constant.SHAREDPREFERENCES_WALLET_ADDRESS, selectedWalletAddress);
                        editor.commit();

                        UnlockWalletFragment unlockWalletFragment = new UnlockWalletFragment();
                        FragmentManager fragmentManager = MainActivity.fragmentManager;
                        fragmentManager.beginTransaction().replace(R.id.screen_area, unlockWalletFragment).commit();

                        Toast.makeText(context, selectedWalletAddress, Toast.LENGTH_SHORT).show();
                    }
                });
                items.add(walletManagerView);
        }

        if(this.adapterMode.equals(Mode.TOKENVIEW)) {


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
