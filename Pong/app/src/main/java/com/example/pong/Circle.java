package com.example.pong;

import static java.lang.Math.abs;
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

    public void update_after_collide(float theta, float aCircleUp)
    {
        float vx_new = (float)(vx * cos(2 * theta)) + (float)(vy * sin(2 * theta));
        float vy_new = (float)(- vx * sin(2 * theta)) + (float)(-vy * cos(2 * theta));
        vx = (float) (vx_new);
        if(aCircleUp > 0) {
            float mult = 1;
            if(vy_new < 0)
                mult = -1;
            vy = (float) (vy_new +  mult * aCircleUp * 0.003);
            mult = 1;
            if(vx_new < 0)
                mult = -1;
            vx = (float) (vx_new + mult * aCircleUp * 0.003);
        }

        else {
            vy = vy_new;
            vx = vx_new;
        }
    }

    public boolean collide_wall()
    {
        if(x < 0 || x > 50)
        {
            return true;
        }
        if(y < 0)
            return true;
        if(y > 100)
            return true;
        return false;
    }

    public void update_after_wall() {
        if(y < 0) {
            vy = -vy;
            y = 0;
        }
        if(y > 100)
        {
            y = 100;
            vy = -vy;
        }
        else {
            vx = -vx;
        }
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

    public void updateGravity(float timeFall) {
        vy += (15) * timeFall;
    }
}

