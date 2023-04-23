#include <Arduino.h>
#include <Wire.h>
#include <LiquidCrystal.h>

char HUMIDITY_MARKER = 'H';
char  TEMP_MARKER = 'T';


#define DUTYCYCLE_25 64
#define DUTYCYCLE_20 51
#define DUTYCYCLE_10 25
#define DUTYSYCLE_0 0

#define PROPPER_HUMIDITY_LEVEL 30
#define MODERATE_HUMIDITY_LEVEL 20
#define MIN_HUMIDITY_LEVEL 10

#define PROPPER_TEMPERATURE_LEVEL 25

const int rs = 12, e=11, d4=5, d5=4, d6=3, d7=2;
LiquidCrystal lcd(rs,e,d4,d5,d6,d7);

struct tempData{
  float temp;
  float humidity;
};

tempData temperatureData;
int motorDutyCycle;
int motorDutyCyclePercent;

void setup() {
  Serial.begin(9600);
  // pinMode(9,OUTPUT);
  // pinMode(8,OUTPUT);
  lcd.begin(20,4);
}

void readDataFromSerialPort(){
  char first_marker = Serial.read();
  if(first_marker == HUMIDITY_MARKER){
        temperatureData.humidity = Serial.parseFloat();
  }
  char second_marker = Serial.read();
  if( second_marker == TEMP_MARKER)
  {
        temperatureData.temp = Serial.parseFloat();
  }
}

void writeDataToSerialPort(String data)
{
  Serial.println(data);
}


void sendActuatorSignals(){
  
    if(temperatureData.humidity > PROPPER_HUMIDITY_LEVEL)
    {
      motorDutyCycle = DUTYSYCLE_0;
      motorDutyCyclePercent = 0;
    }
    else if( temperatureData.humidity <= PROPPER_HUMIDITY_LEVEL && temperatureData.humidity > MODERATE_HUMIDITY_LEVEL)
    {
      if(temperatureData.temp < PROPPER_TEMPERATURE_LEVEL){
        motorDutyCycle = DUTYSYCLE_0;
        motorDutyCyclePercent = 0;
      }
      else{
          motorDutyCycle = DUTYCYCLE_10;
          motorDutyCyclePercent = 10;
      }
    }
    else if( temperatureData.humidity > MIN_HUMIDITY_LEVEL && temperatureData.humidity <= MODERATE_HUMIDITY_LEVEL)
    {
       motorDutyCycle = DUTYCYCLE_20;
       motorDutyCyclePercent = 20;
    }
    else{
      motorDutyCycle = DUTYCYCLE_25;
      motorDutyCyclePercent = 25;
    }
}


void writeDataToLCDPort(){
  lcd.clear();
  // lcd.setCursor(0,0);
  // lcd.print("Data received from sensor:");
  lcd.setCursor(0,0);
  lcd.print("T: " + String(temperatureData.temp) + "C");
  lcd.setCursor(0, 1); 
  lcd.print("H: " + String(temperatureData.humidity) + "%");
  lcd.setCursor(-4, 2);  
  lcd.print("MDC: " + String(motorDutyCycle) + " = " + String(motorDutyCyclePercent) + "%");

  // lcd.setCursor(13, 1); 
  // lcd.print(temperatureData.temp);
  // lcd.write(223);  lcd.print("C");
  // lcd.setCursor(13, 2); lcd.print(temperatureData.humidity);
  // lcd.print("%"); 
  // lcd.setCursor(13, 3);
  // lcd.print(data);

}



void loop() {
  if(Serial.available() > 4)
  {
    readDataFromSerialPort();
    sendActuatorSignals();
    writeDataToSerialPort(String(motorDutyCycle)); 
    writeDataToLCDPort();

  }
}