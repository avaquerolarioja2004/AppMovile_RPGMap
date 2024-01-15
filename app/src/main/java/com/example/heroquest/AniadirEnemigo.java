package com.example.heroquest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.heroquest.DataBase.entities.Enemy_Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AniadirEnemigo extends AppCompatActivity {
    private Handler incHandler = new  Handler();
    private Handler decHandler = new Handler();
    private static final int DELAY_MS = 200;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_enemy);
        List<Enemy_Table> enemyList = MainActivity.db.enemyDAO().getAll();
        for (Enemy_Table enemy : enemyList) {
            String name = enemy.getNombre();
            int idb =enemy.getId();
            createCustomButton(name, idb);
        }

        ImageButton volver = findViewById(R.id.volver);
        ImageButton aniadir = findViewById(R.id.aniadir);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AniadirEnemigo.this, PreGenerateMap.class);

                startActivity(intent);
            }
        });
        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AniadirEnemigo.this, PreGenerateMap.class);
                ArrayList <Integer >enemigos = obtenerCheckBoxMarcados(findViewById(R.id.linear456));
                intent.putExtra("enemigos", enemigos);
                startActivity(intent);
            }
        });
    }

    private void createCustomButton(String buttonName, final int roomId) {
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(this);
        textView.setId(roomId);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                41, 220, 1
        ));
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.vecna));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(getResources().getColor(R.color.black));

        String buttonText = buttonName;
        textView.setText(buttonText);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

        CheckBox doorCheckBox = new CheckBox(this);
        doorCheckBox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        doorCheckBox.setId(roomId);
        doorCheckBox.setTextColor(getResources().getColor(R.color.black));

        parentLayout.addView(textView);
        parentLayout.addView(doorCheckBox);

        LinearLayout mainLayout = findViewById(R.id.linear456);
        if (mainLayout != null) {
            mainLayout.addView(parentLayout);
        }
    }


    private ArrayList<Integer> obtenerCheckBoxMarcados(ViewGroup viewGroup) {
        ArrayList<Integer> checkBoxMarcados = new ArrayList<>();

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isChecked()) {
                    checkBoxMarcados.add(checkBox.getId());
                }
            }

            if (child instanceof ViewGroup) {
                checkBoxMarcados.addAll(obtenerCheckBoxMarcados((ViewGroup) child));
            }
        }

        return checkBoxMarcados;
    }

}
