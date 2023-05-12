package com.example.pong;

public class Racket
{
    private float x;
    private float y;
    private float theta;
    private float length;
    private float vtheta;

    public Racket(float inp_x, float inp_y, float inp_length)
    {
        x = inp_x;
        y = inp_y;
        length = inp_length;
        theta = 0;
        vtheta = 0;
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
    }
}
