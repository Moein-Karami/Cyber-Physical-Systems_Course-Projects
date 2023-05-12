package com.example.pong;

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
import android.view.SearchEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private Circle circle;
    private Racket racket;

    private Timer timer;

    private Handler handler;

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor accelerometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d("MainActivity", "HELOOOO");
        super.onCreate(savedInstanceState);
        circle = new Circle(25, 5,  (float)30);
        racket = new Racket(25, 90, 16);
        MainBoardCanvas boardCanvas = new MainBoardCanvas(this, circle, racket);
        setContentView(boardCanvas);

        timer = new Timer();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyroscope, sensorManager.SENSOR_DELAY_NORMAL);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                circle.update((float)0.001);
                racket.update((float)0.001);
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
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}