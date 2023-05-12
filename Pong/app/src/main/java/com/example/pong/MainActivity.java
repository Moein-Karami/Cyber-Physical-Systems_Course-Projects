package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    private Circle circle;
    private Racket racket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circle = new Circle(25, 5,  (float)30);
        racket = new Racket(25, 90, 16);
        MainBoardCanvas boardCanvas = new MainBoardCanvas(this, circle, racket);
        setContentView(boardCanvas);
    }
}