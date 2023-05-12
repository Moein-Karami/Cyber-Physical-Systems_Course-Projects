package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Circle circle;
    private Racket racket;

    private Timer timer;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circle = new Circle(25, 5,  (float)30);
        racket = new Racket(25, 90, 16);
        MainBoardCanvas boardCanvas = new MainBoardCanvas(this, circle, racket);
        setContentView(boardCanvas);

        timer = new Timer();

//        handler = new Handler()
//        {
//            @Override
//            public void handleMessage(Message message)
//            {
//                boardCanvas.invalidate();
//            }
//        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                circle.update((float)0.001);
                boardCanvas.invalidate();
            }
        }, 0, 1);
    }
}