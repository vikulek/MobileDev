package com.mirea.turbinava.data_thread;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView.setText(
                "1. runOnUiThread выполняется сразу (если из другого потока)\n" +
                        "2. post — ставится в очередь сообщений UI-потока\n" +
                        "3. postDelayed — отложено на заданное время\n\n" +
                        "Последовательность выполнения методов:\n"
        );


        new Thread(() -> {
            // 1. View.post (немедленно)
            textView.post(() -> appendText("1. post"));

            // 2. View.postDelayed (через 500 мс)
            textView.postDelayed(() -> appendText("2. postDelayed (500ms)"), 500);

            // 3. Activity.runOnUiThread (немедленно)
            runOnUiThread(() -> appendText("3. runOnUiThread"));
        }).start();
    }

    private void appendText(String text) {
        runOnUiThread(() -> textView.append(text + "\n"));
    }
}