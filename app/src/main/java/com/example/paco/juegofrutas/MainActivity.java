package com.example.paco.juegofrutas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    private Juego miJuego;
    int nivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("nivel", MODE_PRIVATE);
        String Snivel = prefs.getString("nivel", "No name defined");//"No name defined" is the default value.
        nivel =Integer.parseInt(Snivel);


        miJuego = new Juego(this);
        setContentView(miJuego); // establece la vista de la actividad


//bucle que se repite cada 50 milisegundos
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // mientras haya VIDAS
                if (miJuego.getVidas() < 1) {
                    delay(0);
                    timer.cancel();
                    timer.purge();
                }

                if(nivel==1&&miJuego.getPuntos() > 50) {
                        delay(2);
                        timer.cancel();
                        timer.purge();
                }
                if(nivel==2&&miJuego.getPuntos() > 75) {
                        delay(3);
                        timer.cancel();
                        timer.purge();
                }
                if(nivel==3&&miJuego.getPuntos() > 100) {
                        delay(0);
                        timer.cancel();
                        timer.purge();
                }


                miJuego.post(new Runnable() {
                    @Override
                    public void run() {
                        miJuego.invalidate();
                    }
                });
            }
        }, 0, 50);


    }

    //finaliza el juego y te manda al activity de inicio

    public void finalizarJuego(){

         Intent i = new Intent(this, inicio.class );
         startActivity(i);

    }

    //desbloquea el siguiente nivel, espera 3 segundos y te manda a finalizarJuego

public void delay(final int nivel){

    final Timer timer = new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {

            SharedPreferences.Editor editor = getSharedPreferences("nivel", MODE_PRIVATE).edit();
            SharedPreferences prefs = getSharedPreferences("bloqueoNivel"+nivel, MODE_PRIVATE);
            editor.putString("bloqueoNivel"+nivel, "NO");
            editor.apply();

                        finalizarJuego();
                }
            }, 3000);
}


}
