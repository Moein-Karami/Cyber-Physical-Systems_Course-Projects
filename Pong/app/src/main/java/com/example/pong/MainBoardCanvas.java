package com.example.pong;

import static java.lang.Math.max;
import static java.lang.Math.min;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MainBoardCanvas extends View
{
    private Circle circle;
    private Racket racket;
    public MainBoardCanvas(Context context, Circle inp_circle, Racket inp_racket)
    {
        super(context);
        circle = inp_circle;
        racket = inp_racket;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        float x = getWidth();
        float y = getHeight();
        drawBall(x, y, canvas);
        drawRacket(x, y, canvas);
    }

    private void drawRacket(float x, float y, Canvas canvas)
    {
        Paint paintRacket = new Paint();
        paintRacket.setStyle(Paint.Style.FILL);
        paintRacket.setColor(Color.GRAY);
        float xmin = max(racket.getX() - racket.getLength() / 2, 0);
        float ymin = racket.getY() - 2;
        float ymax = racket.getY() + 2;
        float xmax = min(50, racket.getX() + racket.getLength() / 2);
        canvas.drawRect((xmin / 50) * x, (ymax / 100) * y, (xmax / 50) * x,
                (ymin / 100) * y, paintRacket);
    }

    private void drawBall(float x, float y, Canvas canvas)
    {
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.WHITE);
        canvas.drawCircle((circle.getX() / 50) * x, (circle.getY() / 100) * y
                , circle.getRadius(), paintCircle);
    }
}
