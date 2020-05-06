package net.ifis.proofofvisitclient.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;

public class WalletManagerView extends RecyclerView.ViewHolder {

    private TextView walletAddressTv;
    private ImageView checkBoxIv;

    public WalletManagerView(@NonNull View itemView) {

        super(itemView);

        this.walletAddressTv = (TextView) itemView.findViewById(R.id.walletAddressTv);
        this.checkBoxIv = (ImageView) itemView.findViewById(R.id.checkBoxIv);
        this.checkBoxIv.setVisibility(View.INVISIBLE);

    }

    public TextView getWalletAddressTv() {
        return this.walletAddressTv;
    }

    public ImageView getCheckBoxIv() {
        return this.checkBoxIv;
    }

}
