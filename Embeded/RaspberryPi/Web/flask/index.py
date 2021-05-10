# 모듈 로딩 -------------------------------
from flask import Flask, request
from flask import render_template
#import RPi.GPIO as GPIO

# Flask Server 객체 생성 -----------------
app = Flask(__name__)

# 라우팅 처리 ----------------------------
@app.route("/")
def home():
    return render_template("index.html")

@app.route("/led/on")
def led_on():
    #GPIO.output(8, GPIO.HIGH)
    return "ok"
    # try:
    #     GPIO.output(8, GPIO.HIGH)
    #     return "ok"
    # except expression as identifier:
    #     return "fail"

@app.route("/led/off")
def led_off():
    #GPIO.output(8, GPIO.LOW)
    return "ok"
    # try:
    #     GPIO.output(8, GPIO.LOW)
    #     return "ok"
    # except expression as identifier:
    #     return "fail"

if __name__ == "__main__":
    app.run(host="0.0.0.0")