#GPIO CODE

from RPLCD.i2c import CharLCD
import RPi.GPIO as GPIO
import time


LED = 21

US_TRIG = 19
US_ECHO = 20

lcd = CharLCD('PCF8574', 0x27)
lcd.clear()

def get_distance():
    
    GPIO.output(US_TRIG, GPIO.HIGH)
    time.sleep(0.00001) #10us
    GPIO.output(US_TRIG, GPIO.LOW)
    
    while GPIO.input(US_ECHO) == GPIO.LOW:
        _ = 0
    pulse_begin=time.time()
    
    while GPIO.input(US_ECHO) == GPIO.HIGH:
        _ = 0
        
    pulse_end=time.time()
    distance = (pulse_end - pulse_begin) * 17000
    return distance
# GPIO INIT END

from flask import Flask, render_template, request

app = Flask(__name__)

@app.route("/")
def helloworld():
    return render_template("index.html")

@app.route("/lcd/<text>")
def lcd(text):
    
    lcd = CharLCD('PCF8574', 0x27)
    lcd.cursor_pos=(0,0)
    lcd.write_string(text)
    lcd.write_string("                ")
    return "TEXT {}".format(text)

@app.route("/led/<state>")
def led_on(state):
    if state == "on":
        GPIO.output(LED, GPIO.HIGH)
        return "ok"
    elif state == "off":
        GPIO.output(LED, GPIO.LOW)
        return "ok"
    
    
    return "error"

@app.route("/ultrasonic")
def ultrasonic():
    distance = get_distance()
    return "{{\"distance\":{}}}".format(distance)

    
if __name__ == "__main__":
    # Flask Server Start
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(LED, GPIO.OUT)
    GPIO.setup(US_TRIG, GPIO.OUT)
    GPIO.setup(US_ECHO, GPIO.IN)
    GPIO.output(US_TRIG, GPIO.LOW)
    
    try:
        app.run(host="0.0.0.0")
    finally:
        GPIO.cleanup()
    