import datetime
import time

# Import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

light_on = datetime.time(hour=7)
light_off = datetime.time(hour=19)

# Hardware GPIO configuration
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()

while True:
    
    currTime = datetime.datetime.now()
    
    # Check ON cycle
    while(currTime.hour >= light_on.hour and currTime.hour <= light_off.hour):
        
        # Turn on water pump
        GPIO.output(22, False)
        print('LIGHT - ON')
    
    else:
        
        GPIO.output(22, True)
        print('LIGHT - OFF')