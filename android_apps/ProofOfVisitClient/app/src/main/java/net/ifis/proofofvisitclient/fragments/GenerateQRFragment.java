package net.ifis.proofofvisitclient.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.zxing.WriterException;

import net.ifis.proofofvisitclient.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class GenerateQRFragment extends Fragment {

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

        qrgEncoder = new QRGEncoder("hallo", null, QRGContents.Type.TEXT, 800);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeImageView.setImageBitmap(bitmap);
        } catch(WriterException we) {

        }
    }
}
