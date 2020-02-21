package com.example.invasores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.List;

public class Player {
    private static Player instance;
    private List<Player> varFundo;
    private Paint blue;
    private float x, y, width,height;
    private float Fx, Fy, Fwidth, Fheight;
    private float speedX;
    private boolean isMoving, isMovingLeft;
    static Context context;
    Bitmap alien, fundo,andar1, andar2,andar3,andar4, auxiliar;;
    private int frameIndex;
    long delay = 999999;
    private Player[] animacao; 
    int animacaoIndex;
    
    private Player(Context context) {
        Player.context = context;

        blue = new Paint();

//Configurações d2 plano de fundo
        Fheight = GameView.screenW * 1.61f;
        Fwidth = GameView.screenW * 1f;
        fundo = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.fundo);
        fundo = Bitmap.createScaledBitmap(fundo, (int) Fwidth, (int) Fheight, false);
        x = (width / 2); // Define a posição X do meu player
        y = (height/2); // Define a posição Y do meu player

//Configurações do user
        width = GameView.screenW * 0.08f; //Define o tamanho horizontal do player
        height = GameView.screenW * 0.1f; //Define o tamanho vertical do player
     
        alien = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.alienblue);
        alien = Bitmap.createScaledBitmap(alien, (int) width, (int) height, false);
        x = (GameView.screenW / 2) - (width / 2); // Define a posição X do meu player
        y = GameView.screenH * 0.9f; // Define a posição Y do meu player

//############################################ DIREITA ################################
        andar1 = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.alienblue_walk1);
        andar1 = Bitmap.createScaledBitmap(andar1,(int)width ,(int)height,false);

        andar2 = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.alienblue_walk2);
        andar2= Bitmap.createScaledBitmap(andar2,(int)width,(int)height,false);
// ############################################ DIREITA ################################
        andar3 = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.alienblue_walk3);
        andar3 = Bitmap.createScaledBitmap(andar3,(int)width ,(int)height,false);

        andar4 = BitmapFactory.decodeResource(Player.context.getResources(), R.drawable.alienblue_walk4);
        andar4= Bitmap.createScaledBitmap(andar4,(int)width,(int)height,false);
//#####################################################################################
        
        speedX = 20; // velocidade de locomoção do player
        isMoving = isMovingLeft = false;    
        auxiliar = alien;
    }


    public static Player getInstance(Context context) {
        if (instance == null) instance = new Player(context);

        return instance;
    }

    public float GetX() {
        return x;
    }

    public float GetY() {
        return y;
    }

    public float GetWidth() {
        return width;
    }

    public float GetHeight() {
        return height;
    }

    public void draw(Canvas canvas) {
       canvas.drawBitmap(fundo, Fx, Fy, blue);
       canvas.drawBitmap(alien, x, y, blue);
   
   
   
    }

    public void update() {
        if (isMoving) {
            if (isMovingLeft) {
                x -= speedX;
            }
            else {
                x += speedX;
            }
        }
        CollisionWithScreen();
    }
    public void animacao()
    {
        for(int i = 0; i<=delay*95; i++) alien = andar2;
        for(int i = 0; i<=delay*95; i++) alien = andar1;
        for(int i = 0; i<=delay*95; i++) alien = andar2;
        for(int i = 0; i<=delay*95; i++) alien = andar1;
   
    }

    public void preUpdate(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {//Esquerda
            isMoving = true;
            isMovingLeft = x > event.getRawX();
            for(int i = 0; i<=delay; i++) alien = andar2;
            for(int i = 0; i<=delay*2; i++) alien = andar1;
            alien = andar2;
        } 
        else if (event.getAction() == MotionEvent.ACTION_UP){ // Direita
            isMoving = false;
            if( isMoving = false);
            for(int i = 0; i<=delay; i++) alien = andar1;
            for(int i = 0; i<=delay*2; i++) alien = andar2;
            alien = andar1;
        }
    }
    private void CollisionWithScreen() {
        if (x < 0) x += speedX;
        else if (x + width > GameView.screenW) x -= speedX;
    }
}
/*********************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and * Pearson Education, *
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this   *
 * book have used their * best efforts in preparing the book. These efforts      *
 * include the * development, research, and testing of the theories and programs *
 * * to determine their effectiveness. The authors and publisher make * no       *
 * warranty of any kind, expressed or implied, with regard to these * programs   *
 * or to the documentation contained in these books. The authors * and publisher *
 * shall not be liable in any event for incidental or * consequential damages in *
 * connection with, or arising out of, the * furnishing, performance, or use of  *
 * these programs.                                                               *
 *********************************************************************************/
