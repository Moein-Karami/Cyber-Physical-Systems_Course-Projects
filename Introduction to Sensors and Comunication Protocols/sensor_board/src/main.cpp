
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

///  
const int rs = 12, e=11, d4=5, d5=4, d6=3, d7=2;
LiquidCrystal lcd(rs,e,d4,d5,d6,d7);
///

struct tempData{
  float temp;
  float humidity;
};

tempData currTemperatureData;
float lastReportedHumidity;
bool firstReport = true;


void setup() {
  Wire.begin();
  Serial.begin(9600);
  ///
  lcd.begin(20,4);
  ///
}

float readDataFromSensor(int addr, float scale, float offset){
  unsigned int data[2];
  Wire.beginTransmission(Addr);
  Wire.write(addr);
  Wire.endTransmission();

  // delay(500);
  Wire.requestFrom(Addr,2);


  while (Wire.available() < 2)
    delay(10);

  data[0] = Wire.read();
  data[1] = Wire.read();
  return (((data[0] * 256.0 + data[1]) * scale) / 65536.0) - offset;

}

bool humidityHasMajorChange()
{
  float propper_diff = CHANGE_PERCENT * lastReportedHumidity / 100;
  if(abs(lastReportedHumidity - currTemperatureData.humidity) > propper_diff)
  {
    return true;
  }
  return false;

}

void sendDataToMainBoard(){
  Serial.println(HUMIDITY_MARKER+String(currTemperatureData.humidity)+TEMP_MARKER+String(currTemperatureData.temp));
}

void updateLastReport(){
  lastReportedHumidity = currTemperatureData.humidity;
}

void loop() {
   currTemperatureData.humidity = readDataFromSensor(HUMIDITY_ADDR, 125.0, 6.0);
   currTemperatureData.temp = readDataFromSensor(TEMP_ADDR, 175.72, 46.85);
  //  lcd.clear();
  //  lcd.setCursor(0, 0);
  //  lcd.println(currTemperatureData.humidity);
   if(firstReport || humidityHasMajorChange()){
      sendDataToMainBoard();
      updateLastReport();
      firstReport = false;
   }
}