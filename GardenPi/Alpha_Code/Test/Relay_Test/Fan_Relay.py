import RPi.GPIO as GPIO
import time

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT) # Relay 1
GPIO.setup(27, GPIO.OUT) # Relay 2

times = 1
place = 0

while place < times:
    GPIO.output(18, False)
    print("FAN #1 ON")
    time.sleep(2) # Seconds
    GPIO.output(27, False)
    print("FAN #2 ON")
    time.sleep(5)
    GPIO.output(18, True)
    print("FAN #1 OFF")
    GPIO.output(27, True)
    print("FAN #2 OFF")
    time.sleep(3)
    place += 1
    
GPIO.cleanup()