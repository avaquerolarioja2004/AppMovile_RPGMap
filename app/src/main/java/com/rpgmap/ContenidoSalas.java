package com.rpgmap;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heroquest.R;
import com.rpgmap.database.entities.Room_Table;

import java.util.List;

public class ContenidoSalas extends AppCompatActivity {
    private TextView tittle;

    private String idBorrado ="elemento";
    private ImageButton imageButtonToDelete;
    private int id=1;
    private int x=0;
    private int y=0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_salas);
        tittle = findViewById(R.id.options);
        ImageButton calaveraButton = findViewById(R.id.calavera);
        ImageButton mueblesButton = findViewById(R.id.muebles);
        ImageButton objetosButton = findViewById(R.id.objetos);
        ImageButton backButton = findViewById(R.id.arrowBack);
        ImageButton infoButton = findViewById(R.id.info);
        ImageButton addButton = findViewById(R.id.addButton);
        List<Room_Table> furnitureList = MainActivity.db.roomDAO().getAll();
        for (Room_Table room : furnitureList) {
            int x = room.getX();
            int y = room.getY();
            String name=x+" X "+y;
            createCustomButton(name, id);
            id++;
        }
        id++;

        applyUnderline(tittle);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addElement(R.layout.aniadir_sala);
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(R.layout.info);
            }
        });
        calaveraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoSalas.this, ContenidoEnemigos.class);
                startActivity(intent);
            }
        });

        mueblesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoSalas.this, ContenidoMuebles.class);
                startActivity(intent);
            }
        });

        objetosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoSalas.this, ContenidoObjetos.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContenidoSalas.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void applyUnderline(TextView titulo) {
        Paint paint = titulo.getPaint();
        paint.setUnderlineText(true);
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
    private void showDelete(@LayoutRes int layoutResId,int id,String descripcion) {

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
                    int ancho=Integer.parseInt(descripcion.substring(0, 2).trim());
                    int alto=Integer.parseInt(descripcion.substring(descripcion.length() - 2).trim());

                    Room_Table roomTable=new Room_Table(ancho,alto);
                    MainActivity.db.roomDAO().delete(roomTable);
                    limpiaLinear();
                    popup.dismiss();

                }

            });
        } else {
            Log.e("PreGenerateMap", "El botón 'volver' no se encontró en el diseño del popup.");
        }


        popup.show();
    }
    private void addElement(@LayoutRes int layoutResId) {
        final String[] texto = new String[1];
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

         TextView tamanoTextView = popupView.findViewById(R.id.tamano);
        ImageButton closePopupBtn = popupView.findViewById(R.id.volver);
        ImageButton crear = popupView.findViewById(R.id.crear);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonId = v.getId();
                String buttonText = getResources().getResourceEntryName(buttonId);

                String[] parts = buttonText.split("x");
                if (parts.length == 2) {
                    try {
                        int numero1 = Integer.parseInt(parts[0].substring(1));
                        int numero2 = Integer.parseInt(parts[1]);
                        x=numero1;
                        y=numero2;
                        String numerosTexto = numero1 + " X " + numero2;
                        texto[0] =numerosTexto;
                        tamanoTextView.setText(numerosTexto);
                    } catch (NumberFormatException e) {
                        Log.e("PreGenerateMap", "Error al convertir a números: " + e.getMessage());
                    }
                } else {
                    Log.e("PreGenerateMap", "Formato de ID incorrecto: " + buttonText);
                }
            }
        };

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
                    if(x==0||y==0){
                        Toast.makeText(getApplicationContext(), "Rellene los campos de forma correcta.", Toast.LENGTH_SHORT).show();

                    }else{
                        MainActivity.db.roomDAO().insertRoom(x,y);
                        createCustomButton(texto[0],id);
                        id++;
                        popup.dismiss();
                    }

                }
            });


        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                int buttonId = getResources().getIdentifier("p" + i + "x" + j, "id", getPackageName());

                ImageButton popupButton = popupView.findViewById(buttonId);

                if (popupButton != null) {
                    popupButton.setOnClickListener(buttonClickListener);
                }
            }
        }

        popup.show();
    }
    private void createCustomButton(String buttonText, final int roomId) {
        if(buttonText!=null) {


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
            imageButton.setContentDescription(buttonText);
            imageButton.setImageResource(R.drawable.button_option);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tuLayoutResId = R.layout.eliminar_elemento;
                    int buttonId = view.getId();
                    String descripcion =buttonText;
                    showDelete(tuLayoutResId,buttonId,descripcion);
                }
            });

            TextView textView = new TextView(this);
            textView.setLayoutParams(new FrameLayout.LayoutParams(
                    720, 220

            ));
            textView.setGravity(Gravity.CENTER);

            textView.setText(buttonText);
        /*Typeface vecnaTypeface = Typeface.createFromAsset(getAssets(), "./res/font/vecna.ttf");
        textView.setTypeface(vecnaTypeface);*/
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTextSize(34);


            frameLayout.addView(imageButton);
            frameLayout.addView(textView);





        LinearLayout mainLayout = findViewById(R.id.linearSalas);
        if (mainLayout != null) {
            mainLayout.addView(frameLayout);
        }}
    else{
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
            imageButton.setContentDescription("room");
            imageButton.setVisibility(View.INVISIBLE);
            imageButton.setImageResource(R.drawable.button_option);


            TextView textView = new TextView(this);
            textView.setLayoutParams(new FrameLayout.LayoutParams(
                    720, 220

            ));
            textView.setGravity(Gravity.CENTER);

            textView.setText(buttonText);
        /*Typeface vecnaTypeface = Typeface.createFromAsset(getAssets(), "./res/font/vecna.ttf");
        textView.setTypeface(vecnaTypeface);*/
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTextSize(34);


            frameLayout.addView(imageButton);
            frameLayout.addView(textView);





            LinearLayout mainLayout = findViewById(R.id.linearLayout);
            if (mainLayout != null) {
                mainLayout.addView(frameLayout);
            }
    }}
    private void limpiaLinear(){
        List<Room_Table> furnitureList = MainActivity.db.roomDAO().getAll();

        LinearLayout linearLayout = findViewById( R.id.linearSalas);
        linearLayout.removeAllViews();
        for (Room_Table room : furnitureList) {
            int x = room.getX();
            int y = room.getY();
            String name=x+" X "+y;
            createCustomButton(name, id);
            id++;
        }
        id++;
    }
}
