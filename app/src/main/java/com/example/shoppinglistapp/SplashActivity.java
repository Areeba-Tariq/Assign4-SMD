package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logoImage = findViewById(R.id.logoImage);

        // Load animations
        Animation translateAnim = AnimationUtils.loadAnimation(this, R.anim.translate_animation);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_animation);

        // Start animations
        logoImage.startAnimation(translateAnim);
        logoImage.startAnimation(scaleAnim);

        // Navigate to LoginActivity after animation
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
