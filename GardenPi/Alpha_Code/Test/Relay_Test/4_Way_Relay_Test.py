import RPi.GPIO as GPIO
import time

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT)
GPIO.setup(27, GPIO.OUT)
GPIO.setup(22, GPIO.OUT)
GPIO.setup(23, GPIO.OUT)

times = 2
place = 0

while place < times:
    GPIO.output(18, False)
    time.sleep(1)
    GPIO.output(27, False)
    time.sleep(1)
    GPIO.output(22, False)
    time.sleep(1)
    GPIO.output(23, False)
    print("LED ON")
    time.sleep(3)
    GPIO.output(18, True)
    GPIO.output(27, True)
    GPIO.output(22, True)
    GPIO.output(23, True)
    print("LED OFF")
    time.sleep(2)
    place += 1
    
GPIO.cleanup()