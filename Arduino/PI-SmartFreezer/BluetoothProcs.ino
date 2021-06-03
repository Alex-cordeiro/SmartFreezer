
void IniciaBluetooth(){
   bluetooth.begin(9600);
}

String retornaComandoRecebido(){
  command = "";
  if(bluetooth.available()> 0){
    command = bluetooth.readString();  
  }
  delay(2000);
  if(command == "ligar" || command == "desligar"){
    Serial.println(command);
  }
  return command;
}