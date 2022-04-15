package com.example.proyectobluetooh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private MediaPlayer mMP;
    private ProgressBar progressBar;
    private TextView textViewProgreso;
    private Timer timer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        textViewProgreso = findViewById(R.id.progreso);

        //Reproducir el archivo música
        mMP = MediaPlayer.create(this, R.raw.musica2);
        mMP.start();

        progressBar.setProgress(0);
        final long intervalo = 45;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    progressBar.setProgress(i);
                    i++;
                } else {
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this, DispositivosVinculados.class);
                    startActivity(intent);
                }
            }
        }, 0, intervalo);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMP != null) {
            mMP.stop();
            mMP.release();
            mMP = null;
        }
    }
}