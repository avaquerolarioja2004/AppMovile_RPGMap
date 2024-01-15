package com.example.heroquest;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heroquest.DataBase.entities.Enemy_Table;
import com.example.heroquest.DataBase.entities.Furniture_Table;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ContenidoEnemigos extends AppCompatActivity {
    private TextView tittle;
    private String idBorrado ="elemento";
    private int id=1;
    private byte[] fotografia;
    private ImageView checkImageView;
    private static final int PICK_IMAGE_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_enemigos);
        ImageButton objetosButton = findViewById(R.id.objetos);
        ImageButton salasButton = findViewById(R.id.salas);
        ImageButton mueblesButton = findViewById(R.id.muebles);
        ImageButton backButton = findViewById(R.id.arrowBack);
        ImageButton infoButton = findViewById(R.id.info);
        ImageButton addButton = findViewById(R.id.addButton);
        List<Enemy_Table> enemyList = MainActivity.db.enemyDAO().getAll();
        for (Enemy_Table enemy : enemyList) {
            String name = enemy.getNombre();
            int idb =enemy.getId();
            id=idb;
            createCustomButton(name, idb);
        }
        id++;
        tittle = findViewById(R.id.options);
        applyUnderline(tittle);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addElement(R.layout.aniadir_enemigo);
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(R.layout.info);
            }
        });

        objetosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoEnemigos.this, ContenidoObjetos.class);
                startActivity(intent);
            }
        });

        salasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoEnemigos.this, ContenidoSalas.class);
                startActivity(intent);
            }
        });

        mueblesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoEnemigos.this, ContenidoMuebles.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoEnemigos.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void showPopup(@LayoutRes int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(layoutResId, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);

        final AlertDialog popup = builder.create();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.7);
        int height = (int) (displayMetrics.heightPixels * 0.7);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(popup.getWindow().getAttributes());
        layoutParams.width = width;
        layoutParams.height = height;
        popup.getWindow().setAttributes(layoutParams);
        MainActivity.db.enemyDAO().getAll();

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
    private void showDelete(@LayoutRes int layoutResId,int id) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(layoutResId, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);

        final AlertDialog popup = builder.create();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.7);
        int height = (int) (displayMetrics.heightPixels * 0.7);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(popup.getWindow().getAttributes());
        layoutParams.width = width;
        layoutParams.height = height;
        popup.getWindow().setAttributes(layoutParams);

        ImageButton closePopupBtn = popupView.findViewById(R.id.volver);
        ImageButton cancelar = popupView.findViewById(R.id.cancelar);
        ImageButton eliminar = popupView.findViewById(R.id.eliminar);


        if (closePopupBtn != null && eliminar != null && cancelar != null) {
            closePopupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.dismiss();
                }
            });
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity.db.enemyDAO().delete(MainActivity.db.enemyDAO().getEnemyById(id));
                    limpiaLinear();
                    popup.dismiss();
                    fotografia=null;

                }

            });
        } else {
            Log.e("PreGenerateMap", "El botón 'volver' no se encontró en el diseño del popup.");
        }


        popup.show();
    }
    private void addElement(@LayoutRes int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(layoutResId, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);

        final AlertDialog popup = builder.create();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.7);
        int height = (int) (displayMetrics.heightPixels * 0.7);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(popup.getWindow().getAttributes());
        layoutParams.width = width;
        layoutParams.height = height;
        popup.getWindow().setAttributes(layoutParams);

        ImageButton closePopupBtn = popupView.findViewById(R.id.volver);
        ImageButton fotoButton = popupView.findViewById(R.id.foto);
        ImageButton crear = popupView.findViewById(R.id.crear);
        checkImageView= popupView.findViewById(R.id.preView2);




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

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editAlto = popupView.findViewById(R.id.name);
                String valorEditText = editAlto.getText().toString();
                EditText dif = popupView.findViewById(R.id.dif);
                if (valorEditText.trim().isEmpty() || !isNumeric(dif.getText().toString()) ||fotografia == null) {
                    Toast.makeText(getApplicationContext(), "Rellene los campos de forma correcta.", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.db.enemyDAO().insertEnemy(valorEditText,Integer.parseInt(dif.getText().toString()),fotografia);

                    createCustomButton(valorEditText, id);
                    id = id + 1;
                    popup.dismiss();
                }

            }
        });
        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
        });




        popup.show();
    }
    private void createCustomButton(String buttonText, final int roomId) {
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));

        ImageButton imageButton = new ImageButton(this);
        imageButton.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        imageButton.setId(roomId);
        imageButton.setBackgroundResource(android.R.color.transparent);
        imageButton.setContentDescription("enemy");
        imageButton.setImageResource(R.drawable.button_option);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tuLayoutResId = R.layout.eliminar_elemento;
                int buttonId = view.getId();
                showDelete(tuLayoutResId,buttonId);
            }
        });

        TextView textView = new TextView(this);
        textView.setLayoutParams(new FrameLayout.LayoutParams(
                720,220

        ));
        textView.setGravity(Gravity.CENTER);
        textView.setText(buttonText);
        /*Typeface vecnaTypeface = Typeface.createFromAsset(getAssets(), "res/font/vecna.ttf");
        textView.setTypeface(vecnaTypeface);*/
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setTextSize(34);


        frameLayout.addView(imageButton);
        frameLayout.addView(textView);





        LinearLayout mainLayout = findViewById(R.id.linearEnemy);
        if (mainLayout != null) {
            mainLayout.addView(frameLayout);
        }}
    private boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.equals("0")){
            return false;
        }

        try {
            Double.parseDouble(str);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    private Bitmap resizeImage(Uri imageUri, int maxWidth, int maxHeight) throws IOException {
        InputStream input = getContentResolver().openInputStream(imageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, options);
        input.close();

        int scale = 1;
        while ((options.outWidth / scale > maxWidth) || (options.outHeight / scale > maxHeight)) {
            scale *= 2;
        }

        input = getContentResolver().openInputStream(imageUri);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap resizedBitmap = BitmapFactory.decodeStream(input, null, options);
        input.close();

        return resizedBitmap;
    }
    private void limpiaLinear(){
        List<Enemy_Table> furnitureList = MainActivity.db.enemyDAO().getAll();

        LinearLayout linearLayout = findViewById( R.id.linearEnemy);
        linearLayout.removeAllViews();
        for (Enemy_Table enemy : furnitureList) {
            String name = enemy.getNombre();
            int idb =enemy.getId();
            id=idb;
            createCustomButton(name, idb);
        }
        id++;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = resizeImage(selectedImageUri, 300, 300);
                fotografia = bitmapToByteArray(bitmap);
                if (checkImageView != null) {


                    checkImageView.setImageBitmap(bitmap);
                    checkImageView.setVisibility(View.VISIBLE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
