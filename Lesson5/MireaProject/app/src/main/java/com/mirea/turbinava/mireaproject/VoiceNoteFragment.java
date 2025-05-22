package com.mirea.turbinava.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mirea.turbinava.mireaproject.R;

import java.io.File;
import java.io.IOException;

public class VoiceNoteFragment extends Fragment {

    private static final int REQUEST_MIC_PERMISSION = 200;

    private MediaRecorder recorder;
    private MediaPlayer player;
    private String audioFilePath;

    private Button startRecordingButton, stopRecordingButton, playRecordingButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_note, container, false);

        startRecordingButton = view.findViewById(R.id.startRecordingButton);
        stopRecordingButton = view.findViewById(R.id.stopRecordingButton);
        playRecordingButton = view.findViewById(R.id.playRecordingButton);

        audioFilePath = getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                + "/recorded_audio.3gp";

        startRecordingButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_MIC_PERMISSION);
            } else {
                startRecording();
            }
        });

        stopRecordingButton.setOnClickListener(v -> stopRecording());
        playRecordingButton.setOnClickListener(v -> playRecording());

        return view;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(audioFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            Toast.makeText(getContext(), "Запись начата", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Запись начата", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(getContext(), "Запись завершена", Toast.LENGTH_SHORT).show();
        }
    }

    private void playRecording() {
        player = new MediaPlayer();
        try {
            player.setDataSource(audioFilePath);
            player.prepare();
            player.start();
            Toast.makeText(getContext(), "Воспроизведение...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Воспроизведение...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_MIC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            } else {
                Toast.makeText(getContext(), "Разрешение не предоставлено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}