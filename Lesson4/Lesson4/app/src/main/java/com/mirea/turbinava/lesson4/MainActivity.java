package com.mirea.turbinava.lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlayPause;
    private TextView songTitle;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        songTitle = findViewById(R.id.songTitle);
        songTitle.setSelected(true);

        // Инициализация медиаплеера
        mediaPlayer = MediaPlayer.create(this, R.raw.tebe_budet_legche_bez_menya);

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    btnPlayPause.setText("▶ Воспроизвести");
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setText("⏸ Пауза");
                }
                isPlaying = !isPlaying;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}