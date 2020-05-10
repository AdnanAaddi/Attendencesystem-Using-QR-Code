package com.example.amna.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.amna.fragments.ScannerFragment.flag;

public class ScanerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    public static String course_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        getSupportActionBar().setTitle("Scanner");
        setContentView(scannerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void handleResult(Result result) {
        course_code=result.getText();
        if(course_code.equals("")){
            flag=0;
        }
        else {
            flag=1;
        }
        Toast.makeText(this,course_code, Toast.LENGTH_SHORT).show();
        Log.e("Couse_code Added",result.getText());
//            Intent intent=new Intent(getApplicationContext(),ScannerFragment.class);
//            startActivity(intent);


    }
    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
