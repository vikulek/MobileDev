package com.mirea.turbinava.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editAge, editJob;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAge = findViewById(R.id.editAge);
        editJob = findViewById(R.id.editJob);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            String ageText = editAge.getText().toString().trim();
            String jobText = editJob.getText().toString().trim();

            if (!ageText.isEmpty() && !jobText.isEmpty()) {
                int age = Integer.parseInt(ageText);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    Log.d("RESULT", "Я " + jobText + ", мне " + age + " лет");
                }, age * 1000L); // Задержка в мс
            }
        });
    }
}