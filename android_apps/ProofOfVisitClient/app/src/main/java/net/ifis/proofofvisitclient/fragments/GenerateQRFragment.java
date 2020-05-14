package net.ifis.proofofvisitclient.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.zxing.WriterException;

import net.ifis.proofofvisitclient.R;
import net.ifis.proofofvisitclient.constants.SharedPref;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static net.ifis.proofofvisitclient.activities.MainActivity.sharedPref;

public class GenerateQRFragment extends Fragment {

    TextView infoTextTv;
    ImageView qrCodeImageView;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generate_qr, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qrCodeImageView = view.findViewById(R.id.qr_code_image_view);
        infoTextTv = view.findViewById(R.id.infoTextTv);

        if(sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS).equals(SharedPref.SHAREDPREFERENCES_DEFAULT_VALUE)) {
            infoTextTv.setVisibility(View.VISIBLE);
            qrCodeImageView.setVisibility(View.INVISIBLE);
        } else {
            infoTextTv.setVisibility(View.INVISIBLE);
            qrCodeImageView.setVisibility(View.VISIBLE);

            String data = sharedPref.getString(SharedPref.SHAREDPREFERENCES_WALLET_ADDRESS);

            qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 800);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrCodeImageView.setImageBitmap(bitmap);
            } catch(WriterException we) {
                we.printStackTrace();
            }
        }

    }
}
