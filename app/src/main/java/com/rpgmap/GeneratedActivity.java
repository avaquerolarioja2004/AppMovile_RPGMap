package com.rpgmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.heroquest.R;

import org.json.JSONException;
import org.json.JSONObject;

public class GeneratedActivity extends AppCompatActivity {

    private String json;
    private String path;
    private int width;
    private int height;


    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.generated_layout);

        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        path = intent.getStringExtra("path");
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
        renderBoard();

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
        findViewById(R.id.regenButton).setOnClickListener(view -> {
            String response = Algorithm.generate(json);

            if(response.charAt(0) == '{') {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    path = jsonObj.getString("p");
                    width = jsonObj.getInt("w");
                    height = jsonObj.getInt("h");
                    renderBoard();
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                new android.app.AlertDialog.Builder(this)
                    .setTitle("Error al regenerar el tablero")
                    .setMessage(response)
                    .setNeutralButton("Aceptar", (d, w) -> this.finish())
                .show();
            }
        });
    }

    private void renderBoard() {
        ImageView boardView = findViewById(R.id.boardView);
        Bitmap image = BitmapFactory.decodeFile(path);
        ConstraintLayout layout = findViewById(R.id.boardLayout);

        // Handle rotation
        if(width > height) {
            double ratio = Math.min(
                (double) layout.getMaxWidth() / image.getHeight(),
                (double) layout.getMaxHeight() / image.getWidth()
            );

            boardView.setRotation(90);
            boardView.setImageBitmap(Bitmap.createScaledBitmap(
                image,
                (int) (image.getWidth() * ratio),
                (int) (image.getHeight() * ratio),
                false
            ));
        }
        else {
            double ratio = Math.min(
                (double) layout.getMaxWidth() / image.getWidth(),
                (double) layout.getMaxHeight() / image.getHeight()
            );

            boardView.setRotation(0);
            boardView.setImageBitmap(Bitmap.createScaledBitmap(
                image,
                (int) (image.getWidth() * ratio),
                (int) (image.getHeight() * ratio),
                false
            ));
        }
    }
}
