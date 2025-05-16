package com.mirea.turbinava.thread;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.View;
import com.mirea.turbinava.thread.databinding.ActivityMainBinding;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textViewInfo.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 03, НОМЕР ПО СПИСКУ: 17, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Иллюзия обмана");
        binding.textViewInfo.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int threadNumber = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d",
                                threadNumber, "БИСО-03-20", 11));

                        try {
                            int totalPairs = Integer.parseInt(binding.editTextTotalPairs.getText().toString());
                            int studyDays = Integer.parseInt(binding.editTextStudyDays.getText().toString());

                            double average = (double) totalPairs / studyDays;

                            Thread.sleep(2000);

                            runOnUiThread(() -> binding.textViewResult.setText("Среднее количество пар в день: " + average));

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> binding.textViewResult.setText("Ошибка ввода!"));
                        }

                        Log.d("ThreadProject", "Выполнен поток № " + threadNumber);
                    }
                }).start();
            }
        });
    }
}