#include <Arduino.h>
#include "Wire.h"
#include <LiquidCrystal.h>

// SHT25 I2C address is 0x40(64)
#define Addr 0x40
#define HUMIDITY_ADDR 0xF5
#define TEMP_ADDR 0xF3

#define CHANGE_PERCENT 5

char HUMIDITY_MARKER = 'H';
char  TEMP_MARKER = 'T';

int motorSpeed;

///
const int rs = 12, e=11, d4=5, d5=4, d6=3, d7=2;
LiquidCrystal lcd(rs,e,d4,d5,d6,d7);
///


void setup() {
  Serial.begin(9600);
  pinMode(10, OUTPUT);
  pinMode(9, OUTPUT);
  
  // ///
  // lcd.begin(30,10);
  // ///
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
  while (Serial.available() < 1){}
  readDataFromSerialPort();
  setMotorSignals();
}