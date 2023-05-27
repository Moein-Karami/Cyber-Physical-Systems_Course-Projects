package com.example.pong;

import static java.lang.Math.asin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SearchEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Circle circle;
    float alast = 0;
    private Racket racket;

    private Timer timer;

    private Handler handler;

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;

    private Sensor proxi_sensor;

    private float aCircleUp;

    MainBoardCanvas boardCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d("MainActivity", "HELOOOO");
        super.onCreate(savedInstanceState);
        circle = new Circle(25, 5,  (float)1.5);
        racket = new Racket(25, 90, 16);
        boardCanvas = new MainBoardCanvas(this, circle, racket);
        aCircleUp = 0;
        setContentView(boardCanvas);

        timer = new Timer();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        proxi_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorManager.registerListener(this, gyroscope, sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, proxi_sensor, sensorManager.SENSOR_DELAY_NORMAL);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                circle.update((float)0.001);
                racket.update((float)0.001);

                racket.handle_collide(circle, (float)0.001, aCircleUp);
                if(circle.collide_wall())
                {
                    circle.update_after_wall();
                }
                circle.updateGravity((float) 0.001);
                boardCanvas.invalidate();
            }
        }, 0, 1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensorListener = event.sensor;

        if(sensorListener.getType() == Sensor.TYPE_GYROSCOPE)
        {
            float vtheta = event.values[2] * (float)(360 / (2 * 3.14));
            racket.setVtheta(vtheta);
            boardCanvas.invalidate();

        }
        if(sensorListener.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            float ax = event.values[0];
            aCircleUp = (float) (event.values[2] * 100 * (50/20));
            alast += (0.1) * (ax - alast);
            racket.setAx((float) (-alast * 100));
            boardCanvas.invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        float x = boardCanvas.getWidth();
        float y = boardCanvas.getHeight();

        float x_touch = (event.getX() / x) * 50;
        float y_touch = (event.getY() / y) * 100;



        if(racket.isPointInside(x_touch, y_touch, 5))
        {
            circle.init();
            racket.init();
            boardCanvas.invalidate();
        }

        return true;
    }
}