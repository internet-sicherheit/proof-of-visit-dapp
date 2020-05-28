package net.ifis.proofofvisitclient.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ifis.proofofvisitclient.R;

public class TokenListView extends RecyclerView.ViewHolder {

    private TextView locationNameTv;
    private TextView tokenAmountTv;
    private TextView tokenSymbolTv;

    public TokenListView(@NonNull View itemView) {

        super(itemView);

        this.locationNameTv = (TextView) itemView.findViewById(R.id.locationNameTv);
        this.tokenAmountTv = (TextView) itemView.findViewById(R.id.tokenAmountTv);
        this.tokenSymbolTv = (TextView) itemView.findViewById(R.id.tokenSymbolTv);

    }

    public TextView getLocationNameTv() {
        return this.locationNameTv;
    }

    public TextView getTokenAmountTv() {
        return this.tokenAmountTv;
    }

    public TextView getTokenSymbolTv() {
        return this.tokenSymbolTv;
    }

}
