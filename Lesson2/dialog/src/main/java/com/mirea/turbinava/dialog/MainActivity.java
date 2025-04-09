package com.mirea.turbinava.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_show_time_dialog).setOnClickListener(v -> {
            MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
            timeDialog.show(getSupportFragmentManager(), "timePicker");
        });

        findViewById(R.id.button_show_date_dialog).setOnClickListener(v -> {
            MyDateDialogFragment dateDialog = new MyDateDialogFragment();
            dateDialog.show(getSupportFragmentManager(), "datePicker");
        });

        findViewById(R.id.button_show_progress_dialog).setOnClickListener(v -> {
            MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
            progressDialog.show(getSupportFragmentManager(), "progress");
        });
    }


}