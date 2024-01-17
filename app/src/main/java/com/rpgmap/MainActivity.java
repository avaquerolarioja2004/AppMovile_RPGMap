package com.rpgmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.EditText;

import com.example.heroquest.R;
import com.rpgmap.database.AppDataBase;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    public static AppDataBase db;
    private static final String dbName = "RPGMap.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_page);
        db = Room.databaseBuilder(getApplicationContext(),
        AppDataBase.class, dbName).allowMainThreadQueries().build();
        ImageView imageView = findViewById(R.id.fondo);
        imageView.setRotation(90);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();

        int screenHeight = display.getHeight();

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = screenHeight;
        imageView.setLayoutParams(layoutParams);

        ImageButton  generarButton = findViewById(R.id.generar);
        generarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PreGenerateMap.class);

                startActivity(intent);
            }
        });
        ImageButton  contenidoButton = findViewById(R.id.contenido);
        contenidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ContenidoSalas.class);


                startActivity(intent);
            }
        });

        String path = getDatabasePath(dbName).getParent();
        String errMsg = Algorithm.initialize(path);

        if(!errMsg.isEmpty()) {
            new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errMsg)
                .setNeutralButton("Aceptar", (d, w) -> {
                    this.finish();
                    System.exit(0);
                })
            .show();
        }
    }
}