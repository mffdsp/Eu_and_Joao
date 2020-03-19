package com.example.tentativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Mudar cor
        getWindow().getDecorView().setBackgroundColor(Color.rgb(255,69, 0));

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        }, 3000);

        ProgressBar spinner =findViewById(R.id.progressBar);

        spinner.getIndeterminateDrawable().setColorFilter(Color.rgb(248,4,156), android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}
