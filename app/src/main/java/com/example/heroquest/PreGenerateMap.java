package com.example.heroquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heroquest.DataBase.entities.Enemy_Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PreGenerateMap extends AppCompatActivity {
    private TextView textoDificultad, generar, textoSalas, textoEnemigos, textoObjetos, textoMuebles, textoPuertas, textoPSecretas, textoPasillos,enemigo,sala,mueble,objeto;
    private int dificultadValue = 0;
    private int puertasValue = 0;
    private int pSecretasValue = 0;
    private int pasillosValue = 0;
    private  HashMap<Integer, Integer> muebles = new HashMap<>();
    private  HashMap<Integer, Integer> objetos = new HashMap<>();
    private  HashMap<String, Integer> salas = new HashMap<>();
    private ArrayList <String>  enemigos;

    private Handler handler;
    private static final int LONG_PRESS_DELAY = 120;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_generate_map);
        Intent intent = getIntent();
        if (intent != null) {
            muebles = (HashMap<Integer, Integer>) intent.getSerializableExtra("muebles");
            objetos = (HashMap<Integer, Integer>) intent.getSerializableExtra("objetos");
            salas = (HashMap<String, Integer>) intent.getSerializableExtra("salas");
            enemigos=(ArrayList<String>) intent.getSerializableExtra("enemigos");

        }
        textoDificultad = findViewById(R.id.textoDificultad);
        textoSalas = findViewById(R.id.textoSalas);
        textoEnemigos = findViewById(R.id.textoEnemigos);
        textoObjetos = findViewById(R.id.textoObjetos);
        textoMuebles = findViewById(R.id.textoMuebles);
        textoPuertas = findViewById(R.id.textoPuertas);
        textoPasillos = findViewById(R.id.textoPasillos);
        generar = findViewById(R.id.generar);

        ImageButton backButton = findViewById(R.id.arrowBack);

        ImageButton decDiff = findViewById(R.id.decDifBttn);
        ImageButton decDoor = findViewById(R.id.decDoorBttn);
        ImageButton decCorr = findViewById(R.id.decCorridorBttn);
        ImageButton incDiff = findViewById(R.id.incDifBttn);
        ImageButton incDoor = findViewById(R.id.incDoorBttn);
        ImageButton incCorri = findViewById(R.id.incCorridorBttn);
        ImageButton addEnemi = findViewById(R.id.aniadirEnemigosBttn);
        ImageButton addRoom = findViewById(R.id.aniadirSalasBttn);
        ImageButton addForniture = findViewById(R.id.aniadirMueblesBttn);
        ImageButton addObject = findViewById(R.id.aniadirObjectsBttn);
        ImageButton crear = findViewById(R.id.crear);


        applyUnderline(generar);
        applyUnderline(textoDificultad);
        applyUnderline(textoSalas);
        applyUnderline(textoEnemigos);
        applyUnderline(textoObjetos);
        applyUnderline(textoMuebles);
        applyUnderline(textoPuertas);
        applyUnderline(textoPasillos);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  muebles,salas,enemigos,objetos
                int dificultad= Integer.parseInt(textoDificultad.getText().toString().substring(textoDificultad.getText().toString().length()-2).trim());
                int puertas= Integer.parseInt(textoPuertas.getText().toString().substring(textoPuertas.getText().toString().length()-2).trim());
                int pasillos= Integer.parseInt(textoPasillos.getText().toString().substring(textoPasillos.getText().toString().length()-2).trim());

            }
        });
        addEnemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreGenerateMap.this, AniadirEnemigo.class);

                startActivity(intent);
            }
        });

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreGenerateMap.this, AniadirSala.class);

                startActivity(intent);
            }
        });

        addForniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreGenerateMap.this, AniadirMueble.class);

                startActivity(intent);
            }
        });

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreGenerateMap.this, AniadirObjeto.class);

                startActivity(intent);            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreGenerateMap.this, MainActivity.class);
                startActivity(intent);
            }
        });

        incDecButtons(incDiff, decDiff, textoDificultad, "DIFICULTAD");
        incDecButtons(incDoor, decDoor, textoPuertas, "PUERTAS");
        incDecButtons(incCorri, decCorr, textoPasillos, "PASILLOS");
    }
    public void setMuebles(HashMap<Integer, Integer> mapaEnemigos) {
        this.muebles = mapaEnemigos;
    }
    private void incDecButtons(final ImageButton incButton, final ImageButton decButton, final TextView textView, final String labelText) {
        handler = new Handler();

        incButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouch(incButton, textView, labelText, true, event.getAction());
                return true;
            }
        });

        decButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouch(decButton, textView, labelText, false, event.getAction());
                return true;
            }
        });
    }

    private void handleTouch(ImageButton button, TextView textView, String labelText, boolean increment, int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            handleButtonDown(button, textView, labelText, increment);
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            handleButtonUp();
        }
    }

    private void handleButtonDown(ImageButton button, final TextView textView, final String labelText, final boolean increment) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (increment) {
                    incrementValueAndSetText(textView, labelText);
                } else {
                    decrementValueAndSetText(textView, labelText);
                }
                handler.postDelayed(this, LONG_PRESS_DELAY);
            }
        });
    }

    private void handleButtonUp() {
        handler.removeCallbacksAndMessages(null);
    }

    private void incrementValueAndSetText(TextView textView, String label) {
        int value = getValueForLabel(label);
        value = Math.max(0, value + 1);
        if(value>99){
            value=99;
        }
        setValueForLabel(label, value);
        label=getText(label);
        textView.setText(label + " " + value);
    }

    private void decrementValueAndSetText(TextView textView, String label) {
        int value = getValueForLabel(label);
        if (value > 0) {
            value--;
            setValueForLabel(label, value);
            label=getText(label);
            textView.setText(label + " " + value);
        }
    }

    private int getValueForLabel(String label) {
        switch (label) {
            case "DIFICULTAD":
                return dificultadValue;
            case "PUERTAS":
                return puertasValue;
            case "PTA SECRETAS":
                return pSecretasValue;
            case "PASILLOS":
                return pasillosValue;
            default:
                return 0;
        }
    }
    private String getText(String label) {
        switch (label) {
            case "DIFICULTAD":
                return "DIFICULTAD   ";
            case "PUERTAS":
                return "PUERTAS       ";

            case "PASILLOS":
                return "PASILLOS %    ";
            default:
                return "";
        }
    }

    private void setValueForLabel(String label, int value) {
        switch (label) {
            case "DIFICULTAD":
                dificultadValue = value;
                break;
            case "PUERTAS":
                puertasValue = value;
                break;
            case "PASILLOS":
                pasillosValue = value;
                break;
        }
    }
    private void showPopup(@LayoutRes int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(layoutResId, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);

        final AlertDialog popup = builder.create();

        // Obtén el tamaño de la pantalla
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.7);
        int height = (int) (displayMetrics.heightPixels * 0.7);

        // Establece el tamaño del AlertDialog
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(popup.getWindow().getAttributes());
        layoutParams.width = width;
        layoutParams.height = height;
        popup.getWindow().setAttributes(layoutParams);

        // Configuración del botón para cerrar el popup
        ImageButton closePopupBtn = popupView.findViewById(R.id.volver);

        if (closePopupBtn != null) {
            closePopupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
        } else {
            Log.e("PreGenerateMap", "El botón 'volver' no se encontró en el diseño del popup.");
        }



        popup.show();
    }



    private void applyUnderline(TextView text) {
        Paint paint = text.getPaint();
        paint.setUnderlineText(true);
    }



}