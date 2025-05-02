package com.mynexmy.nex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;
import android.media.MediaPlayer;

public class SplashScreen_Activity extends AppCompatActivity {
    VideoView videoViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        videoViewHome = findViewById(R.id.videoViewHome);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.homevideo);
        videoViewHome.setVideoURI(videoUri);

         videoViewHome.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                Intent intent = new Intent(SplashScreen_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        videoViewHome.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoViewHome.isPlaying()) {
            videoViewHome.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!videoViewHome.isPlaying()) {
            videoViewHome.start();
        }
    }
}
