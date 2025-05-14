package com.mirea.turbinava.simplefragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        isLandscape = findViewById(R.id.fragmentContainer1) != null;

        if (isLandscape) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer1, new FirstFragment())
                    .replace(R.id.fragmentContainer2, new SecondFragment())
                    .commit();
        } else {
            Button btnFirst = findViewById(R.id.btnFirst);
            Button btnSecond = findViewById(R.id.btnSecond);

            btnFirst.setOnClickListener(v ->
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new FirstFragment())
                            .commit());

            btnSecond.setOnClickListener(v ->
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new SecondFragment())
                            .commit());

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new FirstFragment())
                        .commit();
            }
        }
    }
}