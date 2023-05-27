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

    private double lastTimeTouch;

    private Timer timer;

    private Handler handler;

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor linear_accelerometer;
    private Sensor accelerometer;

    private Sensor proxi_sensor;

    private float last_ax = 0;

    private  long s2n = 1000000000;
    private long last_linear_sample = 0;
    private long last_gravity_sample = 0;

    private float aCircleUp;

    private int mode_changed = 0;

    MainBoardCanvas boardCanvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lastTimeTouch = 0;
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
        linear_accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxi_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorManager.registerListener(this, gyroscope, sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, linear_accelerometer, sensorManager.SENSOR_DELAY_FASTEST);
//        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);
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
        int mode = boardCanvas.getState();
//        mode = 1;
        Sensor sensorListener = event.sensor;
//        if (last_linear_sample == 0)
//            last_linear_sample = event.timestamp;
//        if (last_gravity_sample == 0)
//            last_gravity_sample = event.timestamp;

        if(sensorListener.getType() == Sensor.TYPE_GYROSCOPE)
        {
            float vtheta = event.values[2] * (float)(360 / (2 * 3.14));
            racket.setVtheta(-vtheta);
            boardCanvas.invalidate();

        }
        if(sensorListener.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            aCircleUp = (float) (Math.abs(event.values[1]) * 500 * (50/20));
            if ((float)((float) event.timestamp - last_linear_sample) / s2n > 0.1)
                last_linear_sample = event.timestamp;

            float ax = event.values[0];
//            alast += (0.1) * (ax - alast);
            float tmp = ax;
            ax = 0.5f* ax + 0.5f * last_ax;
            last_ax = tmp;
            Log.d("got acc : ", Float.toString(ax));
            if (mode != 1)
            {
                racket.setAx((float) (ax * 132));
                racket.update_linear_x((float)((float) event.timestamp - last_linear_sample) / s2n);
            }
            last_linear_sample = event.timestamp;
            boardCanvas.invalidate();
        }
        if (sensorListener.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            if ((float)((float) event.timestamp - last_gravity_sample) / s2n > 0.1f)
                last_gravity_sample = event.timestamp;

            float ax = event.values[0];

//            float tmp = ax;
//            ax = 0.5f* ax + 0.5f * last_ax;
//            last_ax = tmp;
            racket.set_gravity_Ax((float) (-400 * ax));
            if (mode != 0)
                racket.update_gravity_x((float)((float) event.timestamp - last_gravity_sample) / s2n);
            last_gravity_sample = event.timestamp;
            boardCanvas.invalidate();
        }

//        mode_changed --;
//        if (mode_changed > 0)
//        {
//            racket.setX(25);
//        }
        boardCanvas.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        float x = boardCanvas.getWidth();
        float y = boardCanvas.getHeight();

        double time = event.getEventTime();

        float x_touch = (event.getX() / x) * 50;
        float y_touch = (event.getY() / y) * 100;


        if(racket.isPointInside(x_touch, y_touch, 5) && time - lastTimeTouch > 500)
        {
            lastTimeTouch = time;
            circle.init();
            racket.init();
            mode_changed = 10;
            boardCanvas.invalidate();
            boardCanvas.change_state();

            if(boardCanvas.getState() == 1)
            {
                sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);
//                sensorManager.unregisterListener(this, linear_accelerometer);
//                sensorManager.registerListener(this, linear_accelerometer, sensorManager.SENSOR_DELAY_GAME);
            }
            else if (boardCanvas.getState() == 0)
            {
                sensorManager.unregisterListener(this, accelerometer);
            }
//            else
//            {
////                sensorManager.unregisterListener(this, linear_accelerometer);
////                sensorManager.registerListener(this, linear_accelerometer, sensorManager.SENSOR_DELAY_FASTEST);
//            }
        }

        return true;
    }
}