package com.mirea.turbinava.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextGroup, editTextNumber, editTextMovie;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "user_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGroup = findViewById(R.id.editTextGroup);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextMovie = findViewById(R.id.editTextMovie);
        Button buttonSave = findViewById(R.id.buttonSave);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);


        editTextGroup.setText(sharedPreferences.getString("group", ""));
        editTextNumber.setText(sharedPreferences.getString("number", ""));
        editTextMovie.setText(sharedPreferences.getString("movie", ""));

        buttonSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("group", editTextGroup.getText().toString());
            editor.putString("number", editTextNumber.getText().toString());
            editor.putString("movie", editTextMovie.getText().toString());
            editor.apply();
        });
    }
}