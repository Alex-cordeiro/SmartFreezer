#include <LiquidCrystal_I2C.h>

//Definições de pinos relé
#define releCh1Pin 2
#define releCh2Pin 3
//Definições de valores para o monitor LCD + módulo I2C
#define endereco 0x27
#define colunas 16
#define linhas 2


// INCLUSÃO DE BIBLIOTECAS
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <SoftwareSerial.h>

// INSTANCIAMENTOS 
LiquidCrystal_I2C lcd(endereco, colunas, linhas);
SoftwareSerial bluetooth(11,12); //RX e TX do arduino

//Declarações de variáveis
String command = "";
String resposta = "";
String commandoRecebido = "";

void setup() {
  Serial.begin(9600);
  InicializaPainelLCD();
  IniciaPinosRele(releCh1Pin  , releCh2Pin );
  IniciaBluetooth();

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
