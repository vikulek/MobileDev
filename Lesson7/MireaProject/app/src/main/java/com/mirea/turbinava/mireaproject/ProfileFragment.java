package com.mirea.turbinava.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private EditText editName, editAge, editEmail;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {
        // Пустой конструктор
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editName = view.findViewById(R.id.editName);
        editAge = view.findViewById(R.id.editAge);
        editEmail = view.findViewById(R.id.editEmail);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = requireActivity().getSharedPreferences("profile_data", Context.MODE_PRIVATE);

        // Загрузка данных при открытии
        editName.setText(sharedPreferences.getString("name", ""));
        editAge.setText(String.valueOf(sharedPreferences.getInt("age", 0)));
        editEmail.setText(sharedPreferences.getString("email", ""));

        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString();
            int age = Integer.parseInt(editAge.getText().toString());
            String email = editEmail.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putInt("age", age);
            editor.putString("email", email);
            editor.apply();

            Toast.makeText(getContext(), "Данные сохранены", Toast.LENGTH_SHORT).show();
        });
    }
}