package com.example.pong;

public class Circle
{
    private float x;
    private float y;


    private float vx;
    private float vy;
    private float radius;

    public Circle(float inp_x, float inp_y, float inp_radius)
    {
        x = inp_x;
        y = inp_y;
        vx = 0;
        vy = 0;
        radius = inp_radius;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float new_x)
    {
        x = new_x;
    }

    public void setY(float new_y)
    {
        y = new_y;
    }

    public float getRadius() {
        return radius;
    }

    public void update(float timeFall)
    {
        x += vx * timeFall;
        y += vy * timeFall;
        vy += 10 * timeFall;
    }

}

