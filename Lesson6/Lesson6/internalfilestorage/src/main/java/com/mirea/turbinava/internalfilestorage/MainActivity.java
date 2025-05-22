package com.mirea.turbinava.internalfilestorage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "history_note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNote = findViewById(R.id.editTextNote);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Предзаполнение
        editTextNote.setText("17 мая 1918 - начало Гражданской войны в России");

        buttonSave.setOnClickListener(v -> {
            String text = editTextNote.getText().toString();
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                fos.write(text.getBytes());
                Toast.makeText(this, "Сохранено во внутреннее хранилище", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
            }
        });
    }
}