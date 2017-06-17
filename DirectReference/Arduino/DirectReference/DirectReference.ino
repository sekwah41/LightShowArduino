// For reference
// https://learn.adafruit.com/adafruit-neopixel-uberguide/arduino-library


#include <Adafruit_NeoPixel.h>

#define PIN      6
#define N_LEDS 240

// Cant go to 257 so one color code is stolen for a reset command.
uint16_t RESET_CODE = 1;

uint16_t ledNumber = 0;

int mode = 0;

uint16_t red = 0;
uint16_t green = 0;
//uint16_t blue = 0;

Adafruit_NeoPixel strip = Adafruit_NeoPixel(N_LEDS, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  Serial.begin(250000);
  strip.begin();
  strip.setPixelColor(0 , strip.Color(255,255,255));
  strip.show();
}

void loop() {
  //Serial.println("Loop");
  if (Serial.available() > 0) {
    uint16_t serIn = Serial.read();
    //Serial.println(serIn);
    //Serial.println("In");
    if ( serIn == RESET_CODE) {
      //Serial.println("Show");
      Serial.println("Start");
      mode = 0;
      ledNumber = 0;
      strip.show();
      Serial.println("Done");
    }
    else if (mode == 0) {
      red = serIn;
      mode = 1;
    }
    else if (mode == 1) {
      mode = 2;
      green = serIn;
    }
    else if (mode == 2) {
      mode = 0;
      //Serial.println("Test");
      strip.setPixelColor(ledNumber , strip.Color(red, green, serIn));
      ledNumber = ledNumber + 1;
      if(ledNumber >= N_LEDS){
        ledNumber = 0;
      }
    }
  }
  //chase(strip.Color(255, 0, 0)); // Red
  //chase(strip.Color(0, 255, 0)); // Green
  //chase(strip.Color(0, 0, 255)); // Blue
}

void reset() {
  ledNumber = 0;
  strip.show();
}

