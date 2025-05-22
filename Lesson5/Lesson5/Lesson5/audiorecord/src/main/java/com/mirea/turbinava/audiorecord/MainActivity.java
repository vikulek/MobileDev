package com.mirea.turbinava.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mirea.turbinava.audiorecord.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;

    private ActivityMainBinding binding;
    private MediaRecorder recorder;
    private MediaPlayer player;

    private boolean isRecording = false;
    private boolean isPlaying = false;

    private String fileName;

    private final String[] permissions = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ? new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_MEDIA_AUDIO}
            : new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fileName = getExternalFilesDir(null).getAbsolutePath() + "/recording.3gp";

        binding.buttonRecord.setOnClickListener(v -> {
            if (!isRecording && !isPlaying) {
                startRecording();
            } else if (isRecording) {
                stopRecording();
            }
        });

        binding.buttonPlay.setOnClickListener(v -> {
            if (!isPlaying && !isRecording) {
                startPlayback();
            } else if (isPlaying) {
                stopPlayback();
            }
        });

        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    private boolean checkPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            binding.buttonRecord.setText("Остановить запись");
            Toast.makeText(this, "Запись началась", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка записи", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
        binding.buttonRecord.setText("Начать запись. №11, БИСО-03-20");
        Toast.makeText(this, "Запись завершена", Toast.LENGTH_SHORT).show();
    }

    private void startPlayback() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
            isPlaying = true;
            binding.buttonPlay.setText("Остановить воспроизведение");
            player.setOnCompletionListener(mp -> stopPlayback());
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlayback() {
        player.release();
        player = null;
        isPlaying = false;
        binding.buttonPlay.setText("Воспроизвести");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null) recorder.release();
        if (player != null) player.release();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (!checkPermissions()) {
                Toast.makeText(this, "Не все разрешения предоставлены", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}