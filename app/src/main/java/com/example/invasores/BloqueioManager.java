package com.example.invasores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BloqueioManager {
    private static BloqueioManager instance;

    private int lines, columns;

    //esquerda, direita, mais perto
    Bloqueio leftInimigo, rightInimigo, closestInimigo;
    float imgWidth, imgHeight;

    List<Bloqueio> bloqueios;
    List<Bala> enemyBala;
    public Bala bala;
    float speedX, timer;
    public Bitmap varBloqueioBitmap;
    Context context;
    int contador = 0;

    private BloqueioManager(Context context) {
        this.context = context;
        //Matrix
        lines = 1;
        columns = 1;

        speedX = 10;
        imgWidth = (GameView.screenW / columns) - ((GameView.screenW * 0.05f) + 2 * columns) / columns;
        imgHeight = GameView.screenH * 0.05f;

        varBloqueioBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bloquer);
        varBloqueioBitmap = Bitmap.createScaledBitmap(varBloqueioBitmap,(int) 300, (int) 50, false);
        bloqueios = new ArrayList<>();
        enemyBala = new ArrayList<>();
        SetupEnemies();
    }

    public static BloqueioManager getInstance(Context context) {
        if (instance == null) instance = new BloqueioManager(context);

        return instance;
    }

    public void update(Player player) {
        GetClosestEnemyToPlayer(player);
        //#aqui
        for (Bloqueio e : bloqueios) {
            e.update(speedX);
        }

        //inverte a direção da bala deve ser posto aqui
        for (int i = 0; i < enemyBala.size(); i++)
        {
            if (!enemyBala.get(i).destroyed)
                enemyBala.get(i).update();
            else {
                contador += contador;
                enemyBala.remove(enemyBala.get(i));
            }
        }
        CheckCollisionWithScreen();
    }

    private void CheckCollisionWithScreen(){
       if (leftInimigo.GetX() < 0)
            speedX = 10;
       else if ((leftInimigo.GetX()) +375> GameView.screenW)
            speedX = -10;
    }

    public void SetupEnemies(){
        bloqueios.clear();
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {

                //#aqui Inimigo
                Bloqueio bloqueio = new Bloqueio(i, j, columns, context);
                if (i == lines - 1 && j == 0) {
                    leftInimigo = bloqueio;}

                else if (i == lines - 1 && j == columns - 1) {
                    rightInimigo = bloqueio;}
                bloqueios.add(bloqueio);
            }
        }
    }

    private void GetClosestEnemyToPlayer(Player player) {
        float[] closestDistance = new float[2];
        closestDistance[0] = 9999999;
        closestDistance[1] = 9999999;
        //#aqui
        for (Bloqueio e : bloqueios) {
            if (Math.abs(e.GetX() - player.GetX()) < closestDistance[0] && Math.abs(e.GetY() - player.GetY()) < closestDistance[1]) {
                closestInimigo = e;
                closestDistance[0] = Math.abs(e.GetX() - player.GetX());
                closestDistance[1] = Math.abs(e.GetY() - player.GetY());
            }
        }
    }
}
