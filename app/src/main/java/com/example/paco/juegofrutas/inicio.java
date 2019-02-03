package com.example.paco.juegofrutas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class inicio extends AppCompatActivity {


    Button nivel1, nivel2, nivel3;
    int nivel=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //establecemos los botones

        nivel1=(Button)findViewById(R.id.nivel1);
        nivel2=(Button)findViewById(R.id.nivel2);
        nivel3=(Button)findViewById(R.id.nivel3);

        //desactivamos los niveles 2 y 3 si el usuario no se ha pasado el nivel anterior

        nivel2.setEnabled(false);
        nivel3.setEnabled(false);

        SharedPreferences prefs = getSharedPreferences("nivel", MODE_PRIVATE);
        String bloqueoNivel2 = prefs.getString("bloqueoNivel2", "No name defined");//"No name defined" is the default value.
        String bloqueoNivel3 = prefs.getString("bloqueoNivel3", "No name defined");//"No name defined" is the default value.

        if(bloqueoNivel2.equals("NO")){
            nivel2.setEnabled(true);
        }

        if(bloqueoNivel3.equals("NO")){
            nivel3.setEnabled(true);
        }


        //eventos de botones de nivel

        nivel1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                llamarJuego(1);
            }
        });

        nivel2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                llamarJuego(2);
            }
        });


        nivel3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                llamarJuego(3);
            }
        });


    }

    //te lleva a la activity del juego con el nivel seleccionado

    public void llamarJuego(int nivel){

        Intent i = new Intent(this, MainActivity.class );

        SharedPreferences.Editor editor = getSharedPreferences("nivel", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("nivel", MODE_PRIVATE);

        editor.putString("nivel", Integer.toString(nivel));
        editor.apply();

        startActivity(i);

    }


}
