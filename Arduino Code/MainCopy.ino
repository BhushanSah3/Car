int in1A = 2; // R motor input 1  
int in2A = 4;
int enA =  3;//enable pin


int in3B = 5;
int in4B = 7;
int enB = 6;
char val;
void setup() {
      	pinMode(enA, OUTPUT);
	      pinMode(in1A, OUTPUT);
	      pinMode(in2A, OUTPUT);

        pinMode(enB, OUTPUT);
	      pinMode(in3B, OUTPUT);
	      pinMode(in4B, OUTPUT);

        digitalWrite(in1A, LOW);
	      digitalWrite(in2A, LOW);
        digitalWrite(in3B, LOW);
	      digitalWrite(in3B, LOW);
        Serial.begin(9600);

}

void loop() {

  if(Serial.available()){
    val= Serial.read();
    if(val=='F')
      forward();
    else if(val=='B')
      backward();
    else if(val=='L')
      left();
    else if(val=='R')
      right();
    else if(val=='S')
      stop();
  }

 /*  forward();
   delay(3000);
   backward();
   delay(3000);
   right();
   delay(2000);
   left();
   delay(2000);*/



}
void forward(){

  analogWrite(enA, 150);
  analogWrite(enB, 150);
  digitalWrite(in1A, HIGH);
	digitalWrite(in2A, LOW);
  digitalWrite(in3B, HIGH);
	digitalWrite(in4B, LOW);

}

void backward(){

  analogWrite(enA, 150);
  analogWrite(enB, 150);
  digitalWrite(in1A, LOW);
	digitalWrite(in2A, HIGH);
  digitalWrite(in3B, LOW);
	digitalWrite(in4B, HIGH);

}

void left(){
  analogWrite(enA, 150);
  analogWrite(enB, 150);
  digitalWrite(in1A, LOW);
	digitalWrite(in2A, HIGH);
  digitalWrite(in3B, HIGH);
	digitalWrite(in4B, LOW);
}

void right(){

  analogWrite(enA, 150);
  analogWrite(enB, 150);
  digitalWrite(in1A, HIGH);
	digitalWrite(in2A, LOW);
  digitalWrite(in3B, LOW);
	digitalWrite(in4B, HIGH);

}
void stop(){

  digitalWrite(in1A, LOW);
	digitalWrite(in2A, LOW);
  digitalWrite(in3B, LOW);
  digitalWrite(in4B, LOW);

}
