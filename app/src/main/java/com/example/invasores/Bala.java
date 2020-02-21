package com.example.invasores;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import androidx.appcompat.app.AppCompatActivity;

public class Bala extends AppCompatActivity {
    private Paint corMunicao;
    public float x, y, radius, speedY;
    private Player player;
    public Bloqueio blocker;
    private InimigoManager varInimigoManager;
    public boolean destroyed;
    public boolean playerBullet;

    public Bala(float xPos, float yPos, boolean bulletType, Context context) {
        corMunicao = new Paint();
        corMunicao.setColor(Color.GREEN); //Muda a cor de ambas as balas para a mesma cor

        playerBullet = bulletType;
        x = xPos;
        y = yPos;
        radius = GameView.screenW * 0.01f;
        speedY = -12f;
        destroyed = false;

        player = Player.getInstance(context);
        varInimigoManager = InimigoManager.getInstance(context);
    }

    public void draw(Canvas canvas, Paint cor) {
        if (!destroyed)
            canvas.drawCircle(x, y, radius, cor); //Posição de onde sairá o tiro
    }

    public void update() {
        if (playerBullet) {
            y += speedY; // velocidade da bala do player
        } else {
            y -= speedY; // velocidade da bala do inimigo
        }
        Collision();
        CollisionWithScreen();
    }



    private void CollisionWithScreen() {
        if (y + radius > GameView.screenH) destroyed = true;
        else if (x + radius > GameView.screenW) destroyed = true;
    }
/*
public  void ColisionBloqueador(){
    if (blocker.GetY() == y && blocker.GetX() == y)
    {
        while()speedY +=-5;
    }
}*/

    private void Collision() {
        if (playerBullet) {
            for (Inimigo e : varInimigoManager.enemies) {
                if (!e.GetRemoved() && x - radius < e.GetX() + e.GetWidth() && x + radius > e.GetX() && y - radius < e.GetY() + e.GetHeight() && y + radius > e.GetY())
                {
                    speedY *= -1;
                    e.SetRemoved(true);
                    Pontos.score += 10;

                    destroyed = true;
                    break;
                }
            }
            } else {
            if (x - radius < player.GetX() + player.GetWidth() && x + radius > player.GetX() &&
                y - radius < player.GetY() + player.GetHeight() && y + radius > player.GetY()) {
                GameView.isDead = true;
            }
        }
    }

}
