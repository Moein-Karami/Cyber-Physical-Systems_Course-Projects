#include <Arduino.h>
#include "Wire.h"

int motorSpeed;

void setup() {
  Serial.begin(9600);
  pinMode(10, OUTPUT);
  pinMode(9, OUTPUT);
}

void  setMotorSignals()
{
  analogWrite(10, motorSpeed);
  analogWrite(9, 0);
}

void readDataFromSerialPort()
{
  motorSpeed = Serial.parseInt();
}


void loop() {
  while (Serial.available() < 1)
  {
    delay(100);
  }
  
  readDataFromSerialPort();
  setMotorSignals();
}