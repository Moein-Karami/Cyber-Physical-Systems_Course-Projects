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

    int cnt = 0;

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
//        if(ax_last * ax < 0)
//        {
////            x += vx * 0.002;
//            vx /= 4;
//        }
        if(ax * vx < 0)
            cnt++;
        else
            cnt = 0;
        if(cnt >= 10)
        {
            cnt = 0;
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

    public void handle_collide(Circle circle, float timeFall, float aCircleUp)
    {
        float y0 = circle.getY() - circle.getVy() * timeFall;
        float y1 = circle.getY();
        float x0 = circle.getX() - circle.getVx() * timeFall;
        float x1 = circle.getX();

        float r = circle.getRadius();
        for(int i = 0; i <= 100; i++) {
            y1 = y0 + circle.getVy() * timeFall * (float)(i / 100);
            x1 = x0 + circle.getVx() * timeFall * (float)(i / 100);
            if ((insideLineFirst(x1, y1) && !insideLineFirst(x0, y0))
            || (insideLineFirst(x1, y1 + r) && !insideLineFirst(x0, y0 + r)) ||
            (insideLineFirst(x1, y1 - r) && !insideLineFirst(x0, y0 - r) ||
                    (insideLineFirst(x1 + r, y1) && !insideLineFirst(x0 + r, y0)) ||
                    (insideLineFirst(x1 - r, y1) && !insideLineFirst(x0 - r, y0)))) {
                circle.update_after_collide(getTheta() * (float) (3.14 / 180), aCircleUp);
                return;
            }
        }
        if(y1 <= 100) {
            Log.d("x", String.valueOf(x1));
            Log.d("y", String.valueOf(y1));
        }
    }


    private boolean insideLineFirst(float x0, float y0)
    {
        float x_new = rotatex(x0, x, y0, y, 180 - theta);
        float y_new = rotatey(x0, x, y0, y, 180 - theta);
        if(x_new > length / 2 + x)
            return false;
        if(x_new < x - length / 2)
            return false;
        if(y_new > y + 1)
            return false;
        if(y_new < y - 1)
            return false;
        return true;
    }
}
