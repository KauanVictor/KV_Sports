package com.example.varzea_organizada;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView lyon;
    ImageView kv;
    float v=0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        kv = findViewById(R.id.kv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 3000);

        kv.setTranslationY(300);

        kv.setAlpha(v);

        kv.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();


    }
}