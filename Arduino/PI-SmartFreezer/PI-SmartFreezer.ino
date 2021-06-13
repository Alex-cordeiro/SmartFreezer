



//Definições de pinos relé
#define releCh1Pin 2
#define releCh2Pin 3
//Definições de valores para o monitor LCD + módulo I2C
#define endereco 0x27
#define colunas 16
#define linhas 2
//#define ONE_WIRE_BUS 3

// INCLUSÃO DE BIBLIOTECAS
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <SoftwareSerial.h>
#include <DS1307.h>
#include <OneWire.h>
#include <DallasTemperature.h>
// INSTANCIAMENTOS 
//LiquidCrystal_I2C lcd(endereco, colunas, linhas);
SoftwareSerial bluetooth(12,13); //RX e TX do arduino
//DS1307 rtc(A4, A5);
//OneWire oneWire(ONE_WIRE_BUS);
//Declarações de variáveis
String command = "";
String resposta = "";
String commandoRecebido = "";
float tempMin = 999;
float tempMax = 0;
//DallasTemperature sensors(&oneWire);
//DeviceAddress sensor1;


void setup() {
  Serial.begin(9600);
  //InicializaPainelLCD();
  IniciaPinosRele(releCh1Pin  , releCh2Pin );
  IniciaBluetooth();
  InicializaRelogio();
  
}

void loop() {
 commandoRecebido = retornaComandoRecebido();
 if(commandoRecebido == "ligar"){
   AcionaCanal1(releCh1Pin);
   AcionaCanal2(releCh2Pin);
   commandoRecebido = "";
 }
 if(commandoRecebido == "desligar"){
   DesligaCanal(1);
   DesligaCanal(2);
   commandoRecebido = "";
 }
  commandoRecebido = "";
}
