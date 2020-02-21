package com.example.invasores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class InimigoManager {
    private static InimigoManager instance;

    private int lines, columns;
    public List<Inimigo> enemies;
    public List<Bloqueio> bloqueio;
    Inimigo leftInimigo, rightInimigo, closestInimigo;
    float imgWidth, imgHeight;
    List<Bala> enemyBala;
    float speedX, timer;
    public Bitmap varInimigo;
    Context context;
    int contador = 0;

    private InimigoManager(Context context) {
        this.context = context;
        //Matrix
        lines = 5;
        columns = 7;

        speedX = 2;
        imgWidth = (GameView.screenW / columns) - ((GameView.screenW * 0.05f) + 2 * columns) / columns;
        imgHeight = GameView.screenH * 0.05f;

        varInimigo = BitmapFactory.decodeResource(context.getResources(), R.drawable.grassblock);
        varInimigo = Bitmap.createScaledBitmap(varInimigo, (int) imgWidth, (int) imgHeight, false);

        enemies = new ArrayList<>();
        enemyBala = new ArrayList<>();
        SetupEnemies();
    }

    public static InimigoManager getInstance(Context context) {
        if (instance == null) instance = new InimigoManager(context);

        return instance;
    }

    public void update(Player player) {
        GetClosestEnemyToPlayer(player);
        for (Inimigo e : enemies)
        {
            e.update(speedX);
        }

        timer += 0.15; // frequência de disparo do inimigo
        if (timer >= 5) {
            timer = 0;
            enemyBala.add(new Bala(closestInimigo.GetX() + (closestInimigo.GetWidth() / 2)+100, closestInimigo.GetY() + (closestInimigo.GetHeight() / 2) + 100, false, context));
            enemyBala.add(new Bala(closestInimigo.GetX() + (closestInimigo.GetWidth() / 2), closestInimigo.GetY() + (closestInimigo.GetHeight() / 2), false, context));
        }
        for (int i = 0; i < enemyBala.size(); i++) {
            if (!enemyBala.get(i).destroyed) enemyBala.get(i).update();
            else {
                contador += contador;
                enemyBala.remove(enemyBala.get(i));
            }
        }
        CheckCollisionWithScreen2();
    }

    private void CheckCollisionWithScreen2() {
        if (leftInimigo.GetX() < 0)
        {
            speedX = 2;
        } else if (rightInimigo.GetX() + rightInimigo.GetWidth() > GameView.screenW)
        {
            speedX = -2;
        }
    }

    public void SetupEnemies() {
        enemies.clear();

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {


                Inimigo inimigo = new Inimigo(i, j, columns, context);
                if (i == lines - 1 && j == 0) {
                    leftInimigo = inimigo;}

                else if (i == lines - 1 && j == columns - 1) {
                    rightInimigo = inimigo;}
                enemies.add(inimigo);
            }
        }
    }

    private void GetClosestEnemyToPlayer(Player player) {
        float[] closestDistance = new float[2];
        closestDistance[0] = 9999999;
        closestDistance[1] = 9999999;
        for (Inimigo e : enemies) {
            if (Math.abs(e.GetX() - player.GetX()) < closestDistance[0] && Math.abs(e.GetY() - player.GetY()) < closestDistance[1]) {
                closestInimigo = e;
                closestDistance[0] = Math.abs(e.GetX() - player.GetX());
                closestDistance[1] = Math.abs(e.GetY() - player.GetY());
            }
        }
    }
}