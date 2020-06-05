package net.ifis.proofofvisitadmin.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import net.ifis.proofofvisitadmin.R;
import net.ifis.proofofvisitadmin.activities.MainActivity;
import net.ifis.proofofvisitadmin.constants.SharedPref;

import java.io.IOException;

public class ScanQRCodeFragment extends Fragment {

    private TextView infoTv;
    private ImageView scannerCrossfade;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    private TextView textView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_qrcode, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoTv = view.findViewById(R.id.infoTv);
        scannerCrossfade = view.findViewById(R.id.scannerCrossfade);
        surfaceView = view.findViewById(R.id.camerapreview);
        textView = view.findViewById(R.id.transactionTextview);

        if(MainActivity.isAllInformationAvailable()) {
            infoTv.setVisibility(View.INVISIBLE);

            barcodeDetector = new BarcodeDetector.Builder(this.getContext()).setBarcodeFormats(Barcode.QR_CODE).build();

            cameraSource = new CameraSource.Builder(this.getContext(), barcodeDetector).setRequestedPreviewSize(640,480).build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {

                    SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                    if(qrCodes.size() != 0) {

                        Vibrator vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(1000);

                        MainActivity.sharedPref.add(SharedPref.SHAREDPREFERENCES_RECEIVING_ADDRESS, qrCodes.valueAt(0).displayValue);

                        TransactionFragment transactionFragment = new TransactionFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.screen_area, transactionFragment).commit();

                    }

                }
            });
        } else {
            surfaceView.setVisibility(View.INVISIBLE);
            //textView.setVisibility(View.INVISIBLE);
        }
    }
}
