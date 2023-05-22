package com.example.pong;

import android.util.Log;

public class Racket
{
    private float x;
    private float y;
    private float theta;
    private float length;
    private float vtheta;
    private float vx;
    private float ax;

    public Racket(float inp_x, float inp_y, float inp_length)
    {
        x = inp_x;
        y = inp_y;
        length = inp_length;
        theta = 0;
        vtheta = 0;
        vx = 0;
        ax = 0;
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
        this.vtheta = vtheta;
    }
    public void update(float timeSample)
    {
        theta += vtheta * timeSample;
        x += (float)1/2 * ax * timeSample * timeSample + vx * timeSample;
//        vx += timeSample * ax;
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

        float x0_racket = x - length / 2;
        float x1_racket = x + length / 2;

        Log.d("Y0 out", String.valueOf(y0));
        Log.d("Y1 out", String.valueOf(y1));

        if(y0 > y - 1)
            return false;
        if(y1 < y - 1)
            return false;
        if(x1 > x1_racket || x1 < x0_racket)
            return false;
//        Log.d("Y1 out", String.valueOf(y1));
        return true;
    }
}
