package com.mirea.turbinava.favorutebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {
    private EditText editTextBookName;
    private EditText editTextQuote;
    private Button buttonSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        editTextBookName = findViewById(R.id.editTextBookName);
        editTextQuote = findViewById(R.id.editTextQuote);
        buttonSendData = findViewById(R.id.buttonSendData);

        TextView textViewDeveloperBook = findViewById(R.id.textViewDeveloperBook);
        TextView textViewQuote = findViewById(R.id.textViewQuote);

        textViewDeveloperBook.setText("Любимая книга разработчика: Маленький принц");
        textViewQuote.setText("Цитата: Зорко одно лишь сердце. Самого главного глазами не увидишь.");

        buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = editTextBookName.getText().toString();
                String quote = editTextQuote.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("bookName", bookName);
                returnIntent.putExtra("quote", quote);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}