package com.example.pong;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainBoardCanvas boardCanvas = new MainBoardCanvas(this);
        boardCanvas.setBackgroundColor(Color.RED);
        setContentView(boardCanvas);
    }
}