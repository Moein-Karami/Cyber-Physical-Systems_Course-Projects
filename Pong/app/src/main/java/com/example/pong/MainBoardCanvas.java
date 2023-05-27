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

    private int state;
    public MainBoardCanvas(Context context, Circle inp_circle, Racket inp_racket)
    {
        super(context);
        circle = inp_circle;
        racket = inp_racket;
        state = 0;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int inp_state)
    {
        state = inp_state;
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        float x = getWidth();
        float y = getHeight();
        drawBall(x, y, canvas);
        drawRacket(x, y, canvas);
        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(40);
        canvas.drawText(String.valueOf(racket.getAx()), 100, 100, paintText);
        canvas.drawText(String.valueOf(racket.getVx()), 100, 150, paintText);
        if(state == 0)
        {
            canvas.drawText("Linear Mode", 800, 150, paintText);
        }
        else if(state == 1)
        {
            canvas.drawText("Gravity Mode", 800, 150, paintText);
        }
        else
            canvas.drawText("Both Mode", 800, 150, paintText);
    }

    private void drawRacket(float x, float y, Canvas canvas)
    {
        Paint paintRacket = new Paint();
        paintRacket.setStyle(Paint.Style.FILL);
        paintRacket.setColor(Color.GREEN);
        float xmin = max(racket.getX() - racket.getLength() / 2, 0);
        float ymin = racket.getY() - 1;
        float ymax = racket.getY() + 1;
        float xmax = min(50, racket.getX() + racket.getLength() / 2);
        canvas.save();
        canvas.rotate(racket.getTheta(), (racket.getX() / 50)* x, (racket.getY() / 100) * y);
        canvas.drawRect((xmin / 50) * x, (ymax / 100) * y, (xmax / 50) * x,
                (ymin / 100) * y, paintRacket);
        canvas.restore();
    }

    private void drawBall(float x, float y, Canvas canvas)
    {
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.BLUE);
        canvas.drawCircle((circle.getX() / 50) * x, (circle.getY() / 100) * y
                , (circle.getRadius() / 50) * x, paintCircle);
    }
}
