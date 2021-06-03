
void IniciaPinosRele(int relPin1, int relPin2){
  pinMode(relPin1, OUTPUT);
  pinMode(relPin2, OUTPUT);
  digitalWrite(relPin1, HIGH);
  digitalWrite(relPin2, HIGH);
}

void AcionaCanal1(int relPin1){
  digitalWrite(relPin1, LOW);
}

void AcionaCanal2(int relPin2){
  digitalWrite(relPin2, LOW);
}

void DesligaCanal(int canal){
  digitalWrite(canal, HIGH);
}

