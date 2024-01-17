package com.rpgmap;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.heroquest.R;
import com.rpgmap.database.entities.Room_Table;

import java.util.HashMap;
import java.util.List;

public class AniadirSala extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_rooms);

        MainActivity.db.roomDAO().getAll().forEach(r ->
            createCustomButton(r.getX() + " X " + r.getY()));

        findViewById(R.id.volver).setOnClickListener(view -> {
            setResult(PreGenerateMap.RESULT_UNCHANGED);
            finish();
        });

        findViewById(R.id.aniadirSalas).setOnClickListener(view -> {
            HashMap<String, Integer> mapaObjetos = obtieneCantidad(findViewById(R.id.linearRooms));
            setResult(PreGenerateMap.RESULT_ROOM, new Intent().putExtra("salas", mapaObjetos));
            finish();
        });
    }

    private void createCustomButton(String buttonName) {
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setGravity(Gravity.CENTER_VERTICAL);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                41, 220, 1
        ));
        textView.setTypeface(ResourcesCompat.getFont(this, R.font.vecna));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(getResources().getColor(R.color.black, getTheme()));

        String adjustedName = adjustTextLength(buttonName, 15 - 2);
        String buttonText = adjustedName + " 0";
        textView.setText(buttonText);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

        LinearLayout rightButtonsLayout = new LinearLayout(this);
        rightButtonsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                100, 160
        ));
        rightButtonsLayout.setOrientation(LinearLayout.VERTICAL);

        ImageButton incDoorButton = new ImageButton(this);
        incDoorButton.setLayoutParams(new LinearLayout.LayoutParams(
                80, 80
        ));
        incDoorButton.setBackgroundResource(android.R.color.transparent);
        incDoorButton.setContentDescription("less");
        incDoorButton.setImageResource(R.drawable.arrow);
        LinearLayout.LayoutParams incParams = (LinearLayout.LayoutParams) incDoorButton.getLayoutParams();
        incParams.setMargins(0, 0, 0, 10);
        incDoorButton.setLayoutParams(incParams);
        ImageButton decDoorButton = new ImageButton(this);
        decDoorButton.setLayoutParams(new LinearLayout.LayoutParams(
                80, 80
        ));
        incDoorButton.setOnClickListener(v -> {
            String currentText = textView.getText().toString();
            String numero=currentText.substring(currentText.length() - 2).trim();
            int newValue = Integer.parseInt(numero) + 1;
            if(newValue>99){
                newValue=99;
            }
            textView.setText(currentText.substring(0, currentText.length() - numero.length()) + newValue);
        });
        decDoorButton.setBackgroundResource(android.R.color.transparent);
        decDoorButton.setContentDescription("less");
        decDoorButton.setRotation(180);
        decDoorButton.setImageResource(R.drawable.arrow);
        decDoorButton.setOnClickListener(v -> {
            String currentText = textView.getText().toString();
            String numero = currentText.substring(currentText.length() - 2).trim();
            int newValue = Integer.parseInt(numero) -1;
            if(newValue<0){
                newValue=0;
            }

            textView.setText(currentText.substring(0, currentText.length() - numero.length()) + newValue);
        });
        rightButtonsLayout.addView(incDoorButton);
        rightButtonsLayout.addView(decDoorButton);

        parentLayout.addView(textView);
        parentLayout.addView(rightButtonsLayout);

        LinearLayout mainLayout = findViewById(R.id.linearRooms);
        if (mainLayout != null) {
            mainLayout.addView(parentLayout);
        }
    }

    private String adjustTextLength(String text, int targetLength) {
        StringBuilder adjustedText = new StringBuilder(text);
        while (adjustedText.length() < targetLength) {
            adjustedText.append(" ");
        }
        return adjustedText.toString();
    }

    private HashMap<String, Integer> obtieneCantidad(ViewGroup viewGroup) {
        HashMap<String, Integer> mapaContenido = new HashMap<>();

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof TextView) {
                TextView textView = (TextView) child;
                String sala = textView.getText().toString();
                String textViewContent = sala.substring(sala.length() - 2).trim();
                int cantidad = Integer.parseInt(textViewContent);

                if (cantidad != 0) {
                    mapaContenido.put(sala.substring(0,12).trim(), cantidad);
                }
            }
            else if (child instanceof ViewGroup) {
                mapaContenido.putAll(obtieneCantidad((ViewGroup) child));
            }
        }

        return mapaContenido;
    }
}
