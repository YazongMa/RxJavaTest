package com.castles.RxJavaTest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PATH = "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA17qBbp.img";

    Button button = findViewById(R.id.test);
    ImageView imageView = findViewById(R.id.test_image);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: " + this);
        button.setOnClickListener(view -> downloadImageRxJava());
    }

    private void downloadImageRxJava(){
    }
}