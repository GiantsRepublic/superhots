import RPi.GPIO as GPIO
import time

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.OUT) # Relay 4
GPIO.output(23, True)

times = 1
place = 0

time.sleep(2)

while place < times:
    GPIO.output(23, False)
    print("PUMP ON")
    time.sleep(1.5) # 2 Seconds
    GPIO.output(23, True)
    print("PUMP OFF")
    time.sleep(1)
    place += 1
    
GPIO.cleanup()