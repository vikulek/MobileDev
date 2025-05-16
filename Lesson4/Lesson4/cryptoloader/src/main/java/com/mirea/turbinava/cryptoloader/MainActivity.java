package com.mirea.turbinava.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1;
    private static final String ARG_WORD = "encrypted_text";
    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInput = findViewById(R.id.editTextInput);
        Button buttonEncrypt = findViewById(R.id.buttonEncrypt);

        buttonEncrypt.setOnClickListener(v -> {
            String message = editTextInput.getText().toString().trim();
            if (message.isEmpty()) {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show();
                return;
            }

            // Генерация ключа и шифрование
            SecretKey key = generateKey();
            byte[] encrypted = encryptMsg(message, key);

            // Подготовка данных для Loader
            Bundle bundle = new Bundle();
            bundle.putByteArray(ARG_WORD, encrypted);
            bundle.putByteArray("key", key.getEncoded());

            // Запуск Loader
            LoaderManager.getInstance(this).restartLoader(LOADER_ID, bundle, this);
        });
    }

    // === Реализация LoaderCallbacks ===

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                if (args == null) return null;

                byte[] cryptText = args.getByteArray(ARG_WORD);
                byte[] keyBytes = args.getByteArray("key");

                if (cryptText == null || keyBytes == null) return null;

                SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
                return decryptMsg(cryptText, originalKey);
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data != null) {
            Toast.makeText(this, "Дешифрованное сообщение: " + data, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Ошибка при дешифровке", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Не используется
    }

    // === Вспомогательные методы шифрования ===

    private SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException | java.security.InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}