package com.example.pong;

public class Racket
{
    private int x;
    private int y;
    private int length;

    public Racket(int inp_x, int inp_y, int inp_length)
    {
        x = inp_x;
        y = inp_y;
        length = inp_length;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
