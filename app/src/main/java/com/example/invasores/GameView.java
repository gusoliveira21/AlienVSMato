package com.example.invasores;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class GameView extends View implements Runnable {


    public static int screenW, screenH;
    public static boolean isDead, isPaused, isUpdating;

    private Handler handler;
    private Context context;
    private Paint vitoria;
    private Paint derrota;
    private Player player;


    Paint varCorMunicaoInimigo;
    Paint varCorMunicaoPlayer;
    Paint varBloqueioPaint;
    public static InimigoManager varInimigoManager;
    public static BloqueioManager varBloqueioManager;

   // private Player varPlayer;
    private static Pontos pontos;
    private static List<Bala> bala;
    private float timer;
    private SoundPool soundPool;
    //private MediaPlayer mPlayer;
    public static final int FUNDO_ID = 0;
    public static final int DESTRUIDO_ID = 1;
    public static final int WIN_ID = 2;
    public static final int LOSE_ID = 3;
    public static final int FERIDO_ID = 4;
    public static final int BLOQUEIO_ID = 5;
    private SparseIntArray soundMap; // maps IDs to SoundPool
    private int screenWidth;
    private int screenHeight;
    public Pontos ponts;


    public GameView(Context context) {
        super(context);
        Start(context);

        ponts = new Pontos();
        varCorMunicaoInimigo = new Paint();         //
        varCorMunicaoInimigo.setColor(Color.rgb(139, 69, 19)); //Definem as cores para as balas

        varCorMunicaoPlayer = new Paint();          //
        varCorMunicaoPlayer.setColor(Color.BLUE);   //

        varBloqueioPaint = new Paint();          //
        varBloqueioPaint.setColor(Color.BLUE);   //

        // configure audio attributes for game audio
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        // initialize SoundPool to play the app's three sound effects
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        builder.setAudioAttributes(attrBuilder.build());
        soundPool = builder.build();

        soundMap = new SparseIntArray(5); // create new SparseIntArray
        soundMap.put(FUNDO_ID,soundPool.load(context, R.raw.backmusic, 1));
        soundMap.put(DESTRUIDO_ID,soundPool.load(context, R.raw.explosion1, 1));
        soundMap.put(WIN_ID,soundPool.load(context, R.raw.win, 1));
        soundMap.put(LOSE_ID,soundPool.load(context, R.raw.lose, 1));
        soundMap.put(LOSE_ID,soundPool.load(context, R.raw.ferido, 1));
        soundMap.put(BLOQUEIO_ID,soundPool.load(context, R.raw.blocker_hit, 1));

    }
    public void playSound(int soundId) {
        soundPool.play(soundMap.get(soundId), 1, 1, 1, 0, 1f);
    }

    protected void Start(Context context) {
        this.context = context;
        setBackgroundColor(Color.GRAY);

        screenW = this.context.getResources().getDisplayMetrics().widthPixels;
        screenH = this.context.getResources().getDisplayMetrics().heightPixels;

        isDead = isPaused = false;
        isUpdating = true;

        vitoria = new Paint();
        vitoria.setColor(Color.GREEN); //Cor do texto de reinicio
        vitoria.setTextSize(70);

        derrota = new Paint();
        derrota.setColor(Color.RED); //Cor do texto de reinicio
        derrota.setTextSize(50);

        bala = new ArrayList<Bala>();
        player = Player.getInstance(this.context);
        varInimigoManager = InimigoManager.getInstance(this.context);
        varBloqueioManager = BloqueioManager.getInstance(this.context);
        pontos = new Pontos();

        handler = new Handler();
        handler.post(this);
    }
// get height of the game screen
public int getScreenHeight() {
    return screenHeight;
}

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w; // store CannonView's width
        screenHeight = h; // store CannonView's height

    }

    public boolean onTouchEvent(MotionEvent event){

       if (!isDead&&!isPaused)    player.preUpdate(event);
        else if (isDead)    newGame();
        else if (isPaused)  isPaused = false;
        return true;
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isDead && (ponts.Pontuacao()<350) ) {
            player.draw(canvas);
            pontos.draw(canvas);

            for (Inimigo inimigo : varInimigoManager.enemies) inimigo.draw(canvas, varInimigoManager.varInimigo);//Desenha inimigo
            for (Bala balaPlayer : bala) balaPlayer.draw(canvas,varCorMunicaoPlayer); //Desenha bala do player
            for (Bala balaInimigo : varInimigoManager.enemyBala) balaInimigo.draw(canvas, varCorMunicaoInimigo);//Desenha bala do inimig
            for (Bloqueio auxBloqueio : varBloqueioManager.bloqueios) auxBloqueio.draw(canvas,varBloqueioManager.varBloqueioBitmap); //Desenha bala do player
        }
        else if (isDead)
            canvas.drawText("Não foi dessa vez..."+"\n" + ponts.Pontuacao()+" pts", getScreenHeight()/7, getScreenHeight()/2, derrota);
        else if (isPaused)
        {
            soundPool.pause(1);
            canvas.drawText("Jogo Pausado!\nClique na tela para retomar!", getScreenHeight()/7, getScreenHeight()/2, vitoria);
        }
        else if(ponts.Pontuacao()>=350)
        {
 //         soundPool.play(1);
            canvas.drawText("Pontuação Alcançada:\n" + ponts.Pontuacao()+" pts", getScreenHeight()/7, getScreenHeight()/2, vitoria);
            isDead = true;
        }
    }

    private void Update() {
        if (!isDead && !isPaused)
        {
            timer += 0.1; // frequência de disparo do player
            if (timer >= 3)
            {
                bala.add(new Bala(player.GetX() + (player.GetWidth() / 2), player.GetY() + (player.GetHeight() / 2), true, context));
                timer = 0;
            }
            for (int i = 0; i < bala.size(); i++)
            {
                if (!bala.get(i).destroyed)
                    bala.get(i).update();
                else //inimigo alvo atingido
                {
                    bala.remove(bala.get(i));
                    playSound(DESTRUIDO_ID); //musica
                }
            }
            varInimigoManager.update(player);
            varBloqueioManager.update(player);
            player.update();
        }
    }

    private static void newGame() {
        pontos = new Pontos();
        varInimigoManager.SetupEnemies();
        varBloqueioManager.SetupEnemies();
        varInimigoManager.enemyBala.clear();
        bala.clear();
        isDead = false;
    }

    public void run() {
        if (isUpdating) {
            handler.postDelayed(this, 30);
            Update();
            invalidate();
        } else varInimigoManager.SetupEnemies();
    }
}

