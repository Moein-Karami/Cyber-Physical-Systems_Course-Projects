package com.example.pong;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
        vy += 15 * timeFall;
    }

    public float getVy() {
        return vy;
    }
    public float getVx()
    {
        return vx;
    }

    public void update_after_collide(float theta)
    {
        float vx_new = (float)(vx * cos(2 * theta)) + (float)(vy * sin(2 * theta));
        float vy_new = (float)(- vx * sin(2 * theta)) + (float)(-vy * cos(2 * theta));
        vx = vx_new;
        vy = vy_new;
    }

    public boolean collide_wall()
    {
        if(x < 0 || x > 50)
        {
            return true;
        }
        return false;
    }
}

