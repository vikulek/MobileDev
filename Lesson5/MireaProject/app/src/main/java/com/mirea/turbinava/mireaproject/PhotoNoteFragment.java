package com.mirea.turbinava.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.IOException;

import com.mirea.turbinava.mireaproject.R;

public class PhotoNoteFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    private ImageView photoImageView;
    private EditText noteEditText;
    private Button takePhotoButton, saveNoteButton;

    private Bitmap capturedImageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_note, container, false);

        photoImageView = view.findViewById(R.id.photoImageView);
        noteEditText = view.findViewById(R.id.noteEditText);
        takePhotoButton = view.findViewById(R.id.takePhotoButton);
        saveNoteButton = view.findViewById(R.id.saveNoteButton);

        takePhotoButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        });
        takePhotoButton.setOnClickListener(v -> addSamplePhoto());

        saveNoteButton.setOnClickListener(v -> {
            String noteText = noteEditText.getText().toString();
            if (capturedImageBitmap != null && !noteText.isEmpty()) {
                Toast.makeText(getContext(), "Заметка сохранена!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Добавьте фото и текст!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addSamplePhoto() {
        photoImageView.setImageResource(R.drawable.cabrio);
        Toast.makeText(getContext(), "Фото сделано", Toast.LENGTH_SHORT).show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            capturedImageBitmap = (Bitmap) extras.get("data");
            photoImageView.setImageBitmap(capturedImageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Камера недоступна без разрешения", Toast.LENGTH_SHORT).show();
            }
        }
    }
}