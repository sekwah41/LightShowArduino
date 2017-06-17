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
    //Serial.println("Dat");
    //Serial.println("In");
    //double distance = serIn / 255.0 * N_LEDS;
    for(uint16_t i = 0; i < serIn; i = i + 1){
      strip.setPixelColor(i , 229,83,0);
    }
    for(uint16_t i = serIn; i < N_LEDS; i = i + 1){
      strip.setPixelColor(i , 0,0,0);
    }
    strip.show();
  }
}

