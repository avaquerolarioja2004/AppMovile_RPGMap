package com.rpgmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heroquest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreGenerateMap extends AppCompatActivity {

    public static final int RESULT_UNCHANGED = 0;
    public static final int RESULT_ROOM = 1;
    public static final int RESULT_ENEMY = 2;
    public static final int RESULT_OBJECT = 3;
    public static final int RESULT_FURNITURE = 4;

    private  HashMap<Integer, Integer> muebles = new HashMap<>();
    private  HashMap<Integer, Integer> objetos = new HashMap<>();
    private  HashMap<String, Integer> salas = new HashMap<>();
    private ArrayList <Integer>  enemigos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pre_generate_map);

        this.<TextView>findViewById(R.id.generar).getPaint().setUnderlineText(true);

        this.<TextView>findViewById(R.id.textoSalas).getPaint().setUnderlineText(true);
        this.<TextView>findViewById(R.id.textoEnemigos).getPaint().setUnderlineText(true);
        this.<TextView>findViewById(R.id.textoObjetos).getPaint().setUnderlineText(true);
        this.<TextView>findViewById(R.id.textoMuebles).getPaint().setUnderlineText(true);


        TextView textoDificultad = findViewById(R.id.textoDificultad);
        TextView textoPuertas = findViewById(R.id.textoPuertas);
        TextView textoPasillos = findViewById(R.id.textoPasillos);

        textoDificultad.getPaint().setUnderlineText(true);
        textoPuertas.getPaint().setUnderlineText(true);
        textoPasillos.getPaint().setUnderlineText(true);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent intent = result.getData();

                switch (result.getResultCode()) {
                    case RESULT_ROOM:
                        salas = (HashMap<String, Integer>) intent.getSerializableExtra("salas");
                        break;
                    case RESULT_ENEMY:
                        enemigos = (ArrayList<Integer>) intent.getSerializableExtra("enemigos");
                        break;
                    case RESULT_OBJECT:
                        objetos = (HashMap<Integer, Integer>) intent.getSerializableExtra("objectos");
                        break;
                    case RESULT_FURNITURE:
                        muebles = (HashMap<Integer, Integer>) intent.getSerializableExtra("muebles");
                        break;
                }
            }
        );

        findViewById(R.id.aniadirEnemigosBttn).setOnClickListener(view ->
                launcher.launch(new Intent(this, AniadirEnemigo.class)));
        findViewById(R.id.aniadirSalasBttn).setOnClickListener(view ->
                launcher.launch(new Intent(this, AniadirSala.class)));
        findViewById(R.id.aniadirMueblesBttn).setOnClickListener(view ->
                launcher.launch(new Intent(this, AniadirMueble.class)));
        findViewById(R.id.aniadirObjectsBttn).setOnClickListener(view ->
                launcher.launch(new Intent(this, AniadirObjeto.class)));
        findViewById(R.id.arrowBack).setOnClickListener(view -> finish());

        ValueWrapper difficulty = new ValueWrapper(
            textoDificultad,
            findViewById(R.id.incDifBttn),
            findViewById(R.id.decDifBttn),
            "DIFICULTAD  %4d",
            1000,
            100
        );

        ValueWrapper secretRatio = new ValueWrapper(
            textoPuertas,
            findViewById(R.id.incDoorBttn),
            findViewById(R.id.decDoorBttn),
            "P. SECRETAS %3d%%",
            100,
            100
        );

        ValueWrapper corridors = new ValueWrapper(
            textoPasillos,
            findViewById(R.id.incCorridorBttn),
            findViewById(R.id.decCorridorBttn),
            "PASILLOS    %4d",
            1000,
            100
        );

        findViewById(R.id.crear).setOnClickListener(view -> {
            String json = getJson(
                difficulty.getValue(),
                secretRatio.getValue(),
                corridors.getValue()
            );

            String response = Algorithm.generate(json);

            if(response.charAt(0) == '{') {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    startActivity(
                        new Intent(this, GeneratedActivity.class)
                        .putExtra("json", json)
                        .putExtra("path", jsonObj.getString("p"))
                        .putExtra("width", jsonObj.getInt("w"))
                        .putExtra("height", jsonObj.getInt("h"))
                    );
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                new android.app.AlertDialog.Builder(this)
                    .setTitle("Error al generar el tablero")
                    .setMessage(response)
                    .setNeutralButton("Aceptar", (d, w) -> this.finish())
                .show();
            }
        });
    }

    private String getJson(int difficulty, int secretRatio, int corridors) {
        StringBuilder json = new StringBuilder();

        json.append("{\"rooms\":[");

        if(salas.size() > 0) {
            for(Map.Entry<String, Integer> entry : salas.entrySet()) {
                String[] wh = entry.getKey().split(" X ");
                int w = Integer.parseInt(wh[0]);
                int h = Integer.parseInt(wh[1]);
                json.append("{\"w\":").append(w)
                        .append(",\"h\":").append(h)
                        .append(",\"q\":").append(entry.getValue())
                        .append("},");
            }

            json.replace(json.length() - 1, json.length(), "");
        }

        json.append("],\"enemy_ids\":[");

        if(!enemigos.isEmpty()) {
            for (int enemyId : enemigos) {
                json.append(enemyId).append(',');
            }

            json.replace(json.length() - 1, json.length(), "");
        }

        json.append("],\"objects\":[");

        if(!objetos.isEmpty()) {
            for(Map.Entry<Integer, Integer> entry : objetos.entrySet()) {
                json.append("{\"id\":").append(entry.getKey())
                        .append(",\"q\":").append(entry.getValue())
                        .append("},");
            }

            json.replace(json.length() - 1, json.length(), "");
        }

        json.append("],\"furniture\":[");

        if(!muebles.isEmpty()) {
            for(Map.Entry<Integer, Integer> entry : muebles.entrySet()) {
                json.append("{\"id\":").append(entry.getKey())
                        .append(",\"q\":").append(entry.getValue())
                        .append("},");
            }

            json.replace(json.length() - 1, json.length(), "");
        }

        return json.append("],\"difficulty\":").append(difficulty)
            .append(",\"corridors\":").append(corridors)
            .append(",\"secret_ratio\":").append(secretRatio)
        .append("}").toString();
    }
}