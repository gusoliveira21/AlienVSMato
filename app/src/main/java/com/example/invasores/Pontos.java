package com.example.invasores;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Pontos {
    public static int score;
    private Paint corScore;

    public Pontos() {
        score = 0;

        corScore = new Paint();
        corScore.setColor(Color.rgb(139, 69, 19)); //Definem as cores para as balas
        corScore.setTextSize(50);
    }
    public int Pontuacao()
    {
        return score;
    }
    public void draw(Canvas canvas) {
        canvas.drawText(("Pontuação: " + score), GameView.screenW * 0.35f, GameView.screenH * 0.03f, corScore);
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
