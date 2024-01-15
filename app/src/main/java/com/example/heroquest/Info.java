package com.example.heroquest;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;


import androidx.appcompat.app.AppCompatActivity;

public class Info extends AppCompatActivity {
    private TextView pregunta1, pregunta2, pregunta3, pregunta4,ayuda;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        ayuda = findViewById(R.id.ayuda);

        pregunta1 = findViewById(R.id.pregunta1);
        pregunta2 = findViewById(R.id.pregunta2);
        pregunta3 = findViewById(R.id.pregunta3);
        pregunta4 = findViewById(R.id.pregunta4);

        applyUnderline(ayuda);
        applyUnderline(pregunta1);
        applyUnderline(pregunta2);
        applyUnderline(pregunta3);
        applyUnderline(pregunta4);
    }

    private void applyUnderline(TextView textView) {
        Paint paint = textView.getPaint();
        paint.setUnderlineText(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
