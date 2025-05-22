package com.mirea.turbinava.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFilename, editTextQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextFilename = findViewById(R.id.editTextFilename);
        editTextQuote = findViewById(R.id.editTextQuote);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonLoad = findViewById(R.id.buttonLoad);

        // Запрос разрешения на запись
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        buttonSave.setOnClickListener(v -> saveToFile());
        buttonLoad.setOnClickListener(v -> loadFromFile());
    }

    private void saveToFile() {
        String fileName = editTextFilename.getText().toString().trim() + ".txt";
        String content = editTextQuote.getText().toString();

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);

        try {
            directory.mkdirs();
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "Сохранено в " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String fileName = editTextFilename.getText().toString().trim() + ".txt";

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, fileName);

        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            fis.close();
            editTextQuote.setText(sb.toString().trim());
            Toast.makeText(this, "Загружено из " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при загрузке", Toast.LENGTH_SHORT).show();
        }
    }
}