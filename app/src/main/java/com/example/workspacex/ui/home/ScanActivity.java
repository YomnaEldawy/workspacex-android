package com.example.workspacex.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    //camera permission is needed.
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        // Do something with the result here
        Log.v("Result 1", result.getContents()); // Prints scan results
        Log.v("Result 2", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        String s = result.getContents();
        Intent intent = new Intent(ScanActivity.this, ConfirmScan.class);
        intent.putExtra("QR_RESULT", s);
        startActivity(intent);
        onBackPressed();
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}