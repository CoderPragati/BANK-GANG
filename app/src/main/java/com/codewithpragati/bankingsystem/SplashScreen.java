package com.codewithpragati.bankingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends Activity {

    TextView codedesignedfor;
    ImageView codelogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        codelogo = findViewById(R.id.logo);
        codedesignedfor = findViewById(R.id.designedfor);
        String name = "FOUNDATION";
        String app_name = "BANK GANG";

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEnterAnimation();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        }, 5500);
    }

    private void startEnterAnimation()
    {
        codedesignedfor.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom));
        codelogo.startAnimation(AnimationUtils.loadAnimation(SplashScreen.this, R.anim.p_in));

        codelogo.setVisibility(View.VISIBLE);
        codedesignedfor.setVisibility(View.VISIBLE);
    }
}

