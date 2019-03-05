import RPi.GPIO as GPIO
import time

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT) # Relay 1
GPIO.output(18, True)
time.sleep(0.5)
GPIO.setup(27, GPIO.OUT) # Relay 2
GPIO.output(27, True)
time.sleep(0.5)
GPIO.setup(22, GPIO.OUT) # Relay 3
GPIO.output(22, True)
time.sleep(0.5)
GPIO.setup(23, GPIO.OUT) # Relay 4
GPIO.output(23, True)

GPIO.cleanup()