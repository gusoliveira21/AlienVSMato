package com.example.invasores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Inimigo {
    private Paint green;
    private float x, y, width, height;
    private boolean removed;

    public Inimigo(int i, int j, int columns, Context context) {
        green = new Paint();

        width = (GameView.screenW / columns) - ((GameView.screenW * 0.05f) + 2 * columns) / columns;
        height = GameView.screenH * 0.05f;

        this.x = ((GameView.screenW * 0.05f) + j * width + (j * 2)) + 20;
        this.y = (GameView.screenH * 0.05f) + i * height + (i * 2);

        removed = false;
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

    public boolean GetRemoved() {
        return removed;
    }

    public void SetRemoved(boolean value) {
        removed = value;
    }

    public void draw(Canvas canvas, Bitmap bitmapInimigo) {
        if (!removed) canvas.drawBitmap(bitmapInimigo, x, y, green);
    }

    public void update(float speedX)
    {
        this.x += speedX;
    }
}
