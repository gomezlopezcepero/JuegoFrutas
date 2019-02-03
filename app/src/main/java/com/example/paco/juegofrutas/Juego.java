package com.example.paco.juegofrutas;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

class Juego extends View {

    //variables

    Bitmap fondo, cestaLlena, cestaMedia, cestaVacia,latano, manzana, pera, uvas, cereza;

    int cestaX = 100; int cestaY = 300;
    int manzanaX = 0; int manzanaY = -50;
    int latanoX = 80; int latanoY = -50;
    int peraX = 160; int peraY = -50;
    int uvasX = 240; int uvasY = -50;
    int cerezaX = 320; int cerezaY = -50;
    int frutaY =0; int frutaX =0;

    int vidas=3, puntos, nivel=3;
    Random aleatorio = new Random();

    boolean colision=false;
    boolean arrastrar=false;
    int frutaAleatoria;
    int[] arrayPuntos = {1,1,1,2,2};

    float x,y,x2,y2 =0;
    MediaPlayer mediaPlayer,slap,fail,pick,win;
    ArrayList<Integer> miLista = null;


    //getters

    public int getVidas () {
        return vidas;
    }

    public int getPuntos () {
        return puntos;
    }


    //constructor

    public Juego(Context context) {
        super(context);

        //imagenes del juego

        fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        cestaVacia = BitmapFactory.decodeResource(getResources(), R.drawable.cesta_vacia);
        cestaMedia = BitmapFactory.decodeResource(getResources(), R.drawable.cesta_media);
        cestaLlena = BitmapFactory.decodeResource(getResources(), R.drawable.cesta_llena);

        manzana = BitmapFactory.decodeResource(getResources(), R.drawable.manzana);
        pera = BitmapFactory.decodeResource(getResources(), R.drawable.pera);
        uvas = BitmapFactory.decodeResource(getResources(), R.drawable.uvas);
        cereza = BitmapFactory.decodeResource(getResources(), R.drawable.cereza);
        latano = BitmapFactory.decodeResource(getResources(), R.drawable.latano);

        //sonidos

        mediaPlayer = MediaPlayer.create(context,R.raw.music);
        mediaPlayer.start();

        slap = MediaPlayer.create(context,R.raw.slap);
        fail = MediaPlayer.create(context,R.raw.fail);
        pick = MediaPlayer.create(context,R.raw.pick);
        win = MediaPlayer.create(context,R.raw.win);

        //recoge el nivel seleccionado

        SharedPreferences prefs = context.getSharedPreferences("nivel", MODE_PRIVATE);
        String Snivel = prefs.getString("nivel", "No name defined");//"No name defined" is the default value.
        nivel =Integer.parseInt(Snivel);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pintaFondo(canvas);
        pintaCesta(canvas);
        pintaFruta(canvas);
        detectaColision(canvas);
        pintaMarcador(canvas);

    }


    //el metodo permite arrastrar la cesta de frutas por el escenario

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x =  event.getRawX();
            y =  event.getRawY();
            if(Math.round(x)>cestaX && Math.round(x)<cestaX+150&&Math.round(y)>cestaY+150 && Math.round(y)<cestaY+300){
                arrastrar=true;
            }
            x = cestaX - event.getRawX();
            y = cestaY - event.getRawY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE&&arrastrar) {

            x2 = event.getRawX();
            y2 = event.getRawY();

            cestaX = (Math.round(x))+(Math.round(x2));
            cestaY = (Math.round(y))+(Math.round(y2));
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            arrastrar=false;
        }

        return true;

    }



    public void pintaFondo(Canvas canvas) {
        // fondo "escalado" a la pantalla
        Bitmap fondoEscalado = Bitmap.createScaledBitmap(fondo, getWidth(), getHeight(), true);
        canvas.drawBitmap(fondoEscalado, 0, 0, null);
    }


    //la cesta se va llenando a medida que recoge frutas

    public void pintaCesta(Canvas canvas) {

        if(puntos<20) {
            canvas.drawBitmap(cestaVacia, cestaX, cestaY, null);
        }
        else if(puntos>=20&&puntos<45){
            canvas.drawBitmap(cestaMedia, cestaX, cestaY, null);
        }
        else{
            canvas.drawBitmap(cestaLlena, cestaX, cestaY, null);
        }
    }


    //caen frutas aleatoriamente, la velocidad de caída dependerá del nivel seleccionado

    public void pintaFruta(Canvas canvas) {

        if(frutaAleatoria==0){
            canvas.drawBitmap(manzana, manzanaX, manzanaY, null);
            if (manzanaY < getHeight() - 140) {
                if(nivel==1){
                    manzanaY = manzanaY + 9;
                }
                else if(nivel==2){
                    manzanaY = manzanaY + 15;
                }
                else{
                    manzanaY = manzanaY + 23;
                }
                frutaY = manzanaY; frutaX = manzanaX;
            }

        }else if(frutaAleatoria==1){
            canvas.drawBitmap(cereza, cerezaX, cerezaY, null);
            if (cerezaY < getHeight() - 140) {

                if(nivel==1){
                    cerezaY = cerezaY + 12;
                }
                else if(nivel==2){
                    cerezaY = cerezaY + 18;
                }
                else{
                    cerezaY = cerezaY + 27;
                }

                frutaY = cerezaY; frutaX = cerezaX;
            }
        }
        else if(frutaAleatoria==2){
            canvas.drawBitmap(latano, latanoX, latanoY, null);
            if (latanoY < getHeight() - 140) {
                if(nivel==1){
                    latanoY = latanoY + 15;
                }
                else if(nivel==2){
                    latanoY = latanoY + 22;
                }
                else{
                    latanoY = latanoY + 30;
                }
                frutaY = latanoY; frutaX = latanoX;
            }
        }
        else if(frutaAleatoria==3){
            canvas.drawBitmap(uvas, uvasX, uvasY, null);
            if (uvasY < getHeight() - 140) {

                if(nivel==1){
                    uvasY = uvasY + 18;
                }
                else if(nivel==2){
                    uvasY = uvasY + 25;
                }
                else{
                    uvasY = uvasY + 35;
                }
                frutaY = uvasY; frutaX = uvasX;
            }

        }
        else if(frutaAleatoria==4){
            canvas.drawBitmap(pera, peraX, peraY, null);
            if (peraY < getHeight() - 140) {

                if(nivel==1){
                    peraY = peraY + 21;
                }
                else if(nivel==2){
                    peraY = peraY + 28;
                }
                else{
                    peraY = peraY + 40;
                }

                frutaY = peraY;  frutaX = peraX;
            }
        }


    }

//colisiones

    public void detectaColision(Canvas canvas) {
        Rect rectanguloFruta = new Rect(frutaX, frutaY-20, frutaX+100, frutaY+50);
        Rect rectanguloCesta = new Rect(cestaX, cestaY+30, cestaX + 145,cestaY);

       Paint paint = new Paint();
        paint.setColor(Color.GRAY);

        //si la fruta toca la parte superior de la cesta, añade puntos

        if (Rect.intersects(rectanguloFruta,rectanguloCesta)) {

            pick.start();

            for(int i=0;i<arrayPuntos.length;i++){
                if(frutaAleatoria==i){
                    puntos = puntos + arrayPuntos[i];
                }
            }

            frutaY = -50; // fruta nueva: reiniciar coordenadas
            colision=true;

            //si la fruta se sale de la pantalla, resta una vida

        } else if (frutaY>getHeight()-150) { // la fruta se ha salido por abajo

            slap.start();

            vidas--; // una vida menos

            frutaY = -50; // fruta nueva: reiniciar coordenadas
            colision=true;
        }

        //genera una nueva fruta aleatoria

        if(colision) {
            Random rn = new Random();
            frutaX = rn.nextInt(getWidth() - 100);

            frutaAleatoria = aleatorio.nextInt(5);

            if (frutaAleatoria == 0) {
                manzanaY = frutaY;
                manzanaX = frutaX;
            } else if (frutaAleatoria == 1) {
                cerezaY = frutaY;
                cerezaX = frutaX;
            } else if (frutaAleatoria == 2) {
                latanoY = frutaY;
                latanoX = frutaX;
            } else if (frutaAleatoria == 3) {
                uvasY = frutaY;
                uvasX = frutaX;
            } else if (frutaAleatoria == 4) {
                peraY = frutaY;
                peraX = frutaX;
            }
            colision= false;
        }

    }

    //pinta los textos

    void pintaMarcador(Canvas canvas) {
        Paint pincel = new Paint();
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(32);
        pincel.setTypeface(Typeface.DEFAULT_BOLD);
        pincel.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Nivel " + nivel, getWidth()-70, 50, pincel);
        canvas.drawText("Puntuación : " + puntos, 120, getHeight()-25, pincel);
        canvas.drawText("Vidas : " + vidas, getWidth()-70, getHeight()-25, pincel);
        if (vidas==0) {
            fail.start();
            mediaPlayer.stop();
            canvas.drawText("GAME OVER", getWidth()/2, getHeight()/2, pincel);
        }
        if(nivel==1 && puntos > 50){
                win.start();
                mediaPlayer.stop();
                canvas.drawText("ENHORABUENA", getWidth()/2, getHeight()/2, pincel);
                canvas.drawText("NIVEL 1 COMPLETADO", getWidth()/2, getHeight()/2+50, pincel);
        }
        else if(nivel==2 && puntos > 75){
            win.start();
            mediaPlayer.stop();
            canvas.drawText("ENHORABUENA", getWidth()/2, getHeight()/2, pincel);
            canvas.drawText("NIVEL 2 COMPLETADO", getWidth()/2, getHeight()/2+50, pincel);
        }
        else if(nivel==3 && puntos > 100){
            win.start();
            mediaPlayer.stop();
            canvas.drawText("ENHORABUENA", getWidth()/2, getHeight()/2, pincel);
            canvas.drawText("NIVEL 3 COMPLETADO", getWidth()/2, getHeight()/2+50, pincel);


        }
    }


}