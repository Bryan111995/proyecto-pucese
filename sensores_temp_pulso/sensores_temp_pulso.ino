//LECTURA DESDE EL ARDUINO SENSOR TEMPERATURA Y PULSE HEART

#include <OneWire.h>                
#include <DallasTemperature.h>
 
OneWire ourWire(2);                //Se establece el pin 2  como bus OneWire
 
DallasTemperature sensors(&ourWire);
void setup() {
  // put your setup code here, to run once:
  delay(1000);
  Serial.begin(9600);
  sensor.begin();
  pinMode(10, INPUT);  // sensor corazon
  pinMode(11, INPUT);  //lectura sensor corazon

}

void loop() {
  // put your main code here, to run repeatedly:
 if((digitalRead(10)==1)||(digitalRead(11)== 1)){
  Serial.println('!');
  
 }else{
  Serial.println(analogRead(A0));
 }
 delay(1);
 sensors.requestTemperatures();   //Se envía el comando para leer la temperatura
float temp= sensors.getTempCByIndex(0); //Se obtiene la temperatura en ºC

 Serial.print("Temperatura= ");
 Serial.print(temp);
 Serial.println(" C");
 delay(100);           
}
