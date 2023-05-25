package com.example.pong;

import android.util.Log;

import java.util.Vector;

public class Racket
{
    private float x;
    private float y;
    private float theta;
    private float length;
    private float vtheta;
    private float vx;
    private float ax;
    private float ax_last;

    int count;
    int lastCollide;

    public Racket(float inp_x, float inp_y, float inp_length)
    {
        x = inp_x;
        y = inp_y;
        length = inp_length;
        theta = 0;
        vtheta = 0;
        vx = 0;
        ax = 0;
        ax_last = 0;
        count = 0;
        lastCollide = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public float getVtheta() {
        return vtheta;
    }

    public void setVtheta(float vtheta) {
        this.vtheta = (float) (vtheta);
    }
    public void update(float timeSample)
    {
//        ax += 15 * Math.sin(theta);
        theta += vtheta * timeSample;
        x += (float)1/2 * ax * timeSample * timeSample + vx * timeSample;
        vx += timeSample * ax;
        if(ax_last * ax < 0)
        {
            vx = 0;
        }
        ax_last = ax;
        if(x <= 0)
        {
            x = 0;
            vx = 0;
            ax = 0;
        }
        if(x >= 50)
        {
            x = 50;
            vx = 0;
            ax = 0;
        }
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public boolean collide(Circle circle, float timeFall) {

        float y0 = circle.getY() - circle.getVy() * timeFall;
        float y1 = circle.getY();
        float x0 = circle.getX() - circle.getVx() * timeFall;
        float x1 = circle.getX();

        float r = circle.getRadius();

        count++;



        if(checkCollide(x1, y1, r) && !checkCollide(x0, y0, r) && (lastCollide < (count - 5))) {
            Log.d("Last Collide", String.valueOf(lastCollide));
            Log.d("Count", String.valueOf(count));
            lastCollide = count;

            return true;
        }
        return false;
//
//        float x0_racket = x - length / 2;
//        float x1_racket = x + length / 2;
//
//        Log.d("Y0 out", String.valueOf(y0));
//        Log.d("Y1 out", String.valueOf(y1));
//
//        if(y0 > y - 1)
//            return false;
//        if(y1 < y - 1)
//            return false;
//        if(x1 > x1_racket || x1 < x0_racket)
//            return false;
//        return true;
    }

    private boolean checkCollide(float x1, float y1, float r) {
        return (isPointInside(x1, y1 - r, 1) || isPointInside(x1, y1 + r, 1)
                || isPointInside(x1 + r, y1, 1) || isPointInside(x1 - r, y1, 1)
                || isPointInside(x1, y1, 1));
    }

    public void init() {
        x = 25;
        y = 90;
        vtheta = 0;
        theta = 0;
        vx = 0;
        ax = 0;
    }

    public float rotatex(float x0, float x1, float y0, float y1, float theta0)
    {
        return (float) (x1 + (x0 - x1) * Math.cos(theta0 * (3.14159686 / 180))
        - (y0 - y1) * Math.sin(theta0 * (3.14159686 / 180)));
    }

    public float rotatey(float x0, float x1, float y0, float y1, float theta0)
    {
        return (float) (y1 + (x0 - x1) * Math.sin(theta0 * (3.14159686 / 180))
        + (y0 - y1) * Math.cos(theta0 * (3.14159686 / 180)));
    }
    public boolean isPointInside(float x_touch, float y_touch, float width)
    {
        float x0 = rotatex(x_touch, x, y_touch, y, theta);
        float y0 = rotatey(x_touch, x, y_touch, y, theta);


        float xmax = x + length / 2;
        float xmin = x - length / 2;
        float ymin = y - width;
        float ymax = y + width;

//        Log.d("On Touch X", String.valueOf(x_touch));
//        Log.d("On Touch Y", String.valueOf(y_touch));
//        Log.d("On Touch X Rotated", String.valueOf(x0));
//        Log.d("On Touch Y Rotated", String.valueOf(y0));
//        Log.d("Theta", String.valueOf(theta));
//        Log.d("X min", String.valueOf(xmin));
//        Log.d("X max", String.valueOf(xmax));
//        Log.d("Y min", String.valueOf(ymin));
//        Log.d("Y max", String.valueOf(ymax));

        if(xmax < x0)
            return false;
        if(xmin > x0)
            return false;
        if(ymax < y0)
            return false;
        if(ymin > y0)
            return false;
        return true;
    }
}
