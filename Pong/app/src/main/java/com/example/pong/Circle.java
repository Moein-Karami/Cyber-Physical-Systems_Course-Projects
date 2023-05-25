package com.example.pong;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.util.Log;

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
//        Log.d("Y0 out", String.valueOf(aCircleUp));
        x += vx * timeFall;
        y += vy * timeFall;
        vy += (15) * timeFall;
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
        if(y < 0)
            return true;
        return false;
    }

    public void update_after_wall() {
        if(y < 0) {
            vy = -vy;
            y = 0;
        }
        else
            vx = -vx;
    }

    public void init() {
        x = 25;
        y = 5;
        vx = 0;
        vy = 0;
    }

    public void setVy(float v) {
        vy = v;
    }
}

