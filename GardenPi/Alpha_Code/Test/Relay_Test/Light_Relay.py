import RPi.GPIO as GPIO
import time

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(22, GPIO.OUT) # Relay 3

times = 1
place = 0

while place < times:
    GPIO.output(22, False)
    print("LIGHT ON")
    time.sleep(3) # Seconds
    GPIO.output(22, True)
    print("LIGHT OFF")
    time.sleep(2)
    place += 1
    
GPIO.cleanup()