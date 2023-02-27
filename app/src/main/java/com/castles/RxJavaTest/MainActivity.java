package com.castles.RxJavaTest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PATH = "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA17qBbp.img";

    private Button button;
    private ImageView imageView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: " + this);
        button = findViewById(R.id.test);
        imageView = findViewById(R.id.test_image);
        progressDialog = new ProgressDialog(this);

        button.setOnClickListener(view -> downloadImageRxJava());
    }

    private void downloadImageRxJava(){
        Observable.just(PATH)
                .map(s -> {
                    URL url = new URL(PATH);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
                    httpsURLConnection.setConnectTimeout(5000);
                    int responseCode = httpsURLConnection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        InputStream is = httpsURLConnection.getInputStream();
                        return BitmapFactory.decodeStream(is);
                    }
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressDialog.setView(imageView);
                        progressDialog.setTitle("test");
                        progressDialog.setMessage("in progress");
                        progressDialog.show();
                        Log.d(TAG, "onSubscribe: " + this);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        Log.d(TAG, "onNext: " + this);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + this);
                    }

                    @Override
                    public void onComplete() {
                        progressDialog.dismiss();
                        Log.d(TAG, "onComplete: " + this);
                    }
                });
    }
}