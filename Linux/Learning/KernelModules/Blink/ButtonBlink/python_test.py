import RPi.GPIO as GPIO
import time

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(17, GPIO.OUT)
GPIO.setup(27, GPIO.IN, GPIO.PUD_DOWN)

state = True
def toggle():
    global state
    state = not state
    GPIO.output(17, GPIO.HIGH if state else GPIO.LOW)
toggle()

while True:
    if GPIO.input(27):
        toggle()
        time.sleep(0.2)
        while(GPIO.input(27)):
            pass
