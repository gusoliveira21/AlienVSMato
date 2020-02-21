package com.example.invasores;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


public class AlienvsMato extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Alien VS Mato");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(new GameView(this));
       // musica de fundo
       MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.backmusic);
       mediaPlayer.start();


    }

    protected void onPause() {
        super.onPause();
        GameView.isPaused = true;
        Toast.makeText(this, "Jogo Pausado", Toast.LENGTH_SHORT).show();
    }

    protected void onStop() {
        super.onStop();
        GameView.isPaused = true;
        Toast.makeText(this, "Jogo Parado", Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();
        GameView.isUpdating = false;
        Toast.makeText(this, "Jogo Destruído", Toast.LENGTH_SHORT).show();
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
