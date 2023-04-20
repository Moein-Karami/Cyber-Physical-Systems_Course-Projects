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

}

unsigned int* readDataFromSensor(int addr){
  unsigned int data[2];
  Wire.beginTransmission(Addr);
  Wire.write(addr);
  Wire.endTransmission();
  Wire.requestFrom(Addr,2);
  if(Wire.available() == 2 )
  {
    data[0] = Wire.read();
    data[1] = Wire.read();
    // float humidity = (((data[0] * 256.0 + data[1]) * 125.0) / 65536.0) - 6;
    // currTemperatureData.humidity = humidity;
  }
  return data;

}

bool humidityHasMajorChange()
{
  float propper_diff = CHANGE_PERCENT * lastReportedHumidity / 100;
  if(abs(lastReportedHumidity - currTemperatureData.temp) > propper_diff)
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
  
   unsigned int* humidity_data = readDataFromSensor(HUMIDITY_ADDR);
   unsigned int* temp_data = readDataFromSensor(TEMP_ADDR);
   currTemperatureData.humidity = ((( humidity_data[0] * 256.0 +  humidity_data[1]) * 125.0) / 65536.0) - 6;
   currTemperatureData.temp = (((temp_data[0] * 256.0 + temp_data[1]) * 175.72) / 65536.0) - 46.85;
   if(firstReport || humidityHasMajorChange()){
      sendDataToMainBoard();
      updateLastReport();
      firstReport = false;
   }
}