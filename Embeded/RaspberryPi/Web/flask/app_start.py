from flask import Flask

from RPLCD.i2c import CharLCD
import RPi.GPIO as GPIO
import time

LED = 21

lcd = CharLCD('PCF8574', 0x27)
lcd.clear()

app = Flask(__name__)

@app.route("/")
def helloworld():
    return "Hello World!"

@app.route("/lcd/<text>")
def lcd(text):
    
    lcd = CharLCD('PCF8574', 0x27)
    lcd.cursor_pos=(0,0)
    lcd.write_string(text)
    lcd.write_string("                ")
    return "TEXT {}".format(text)

@app.route("/led/on")
def led_on():
    GPIO.output(LED, GPIO.HIGH)
    
    return "LED ON"

@app.route("/led/off")
def led_off():
    GPIO.output(LED, GPIO.LOW)
    return "LED OFF"
    
if __name__ == "__main__":
    # Flask Server Start
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(LED, GPIO.OUT)
    
    try:
        app.run(host="0.0.0.0")
    finally:
        GPIO.cleanup()
    