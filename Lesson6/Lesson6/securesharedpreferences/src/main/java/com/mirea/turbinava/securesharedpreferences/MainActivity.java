package com.mirea.turbinava.securesharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView poetName = findViewById(R.id.poetName);
        ImageView poetImage = findViewById(R.id.poetImage);

        try {
            MasterKey masterKey = new MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            SharedPreferences encryptedPrefs = EncryptedSharedPreferences.create(
                    this,
                    "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            if (!encryptedPrefs.contains("poet")) {
                encryptedPrefs.edit().putString("poet", "Сергей Александрович Есенин").apply();
            }

            String poet = encryptedPrefs.getString("poet", "Неизвестно");
            poetName.setText(poet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}