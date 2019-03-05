# Check Water Value and Water if needed.
# Author: Luke Duvall
import time
import datetime

# import DHT11 library
import dht11

# import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

# Intro
print('Temperature and Fan #1 Test')
print('---------------------------')
print('')
print('Starting Test...')
print('')
    
# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()
    


while True:
    
    # read data using pin 17
    instance = dht11.DHT11(pin=17)
    
    result = instance.read()
    temp = ((result.temperature * (9/5)) + 32)
    
    if temp > 80:
            
        print("Temperature: %d F" % ((result.temperature * (9/5)) + 32))
        print('')
        print('Too Hot...')
        time.sleep(1)
        print('Turning On Fan...')
        print('')
        time.sleep(0.5)
        
        GPIO.setup(18, GPIO.OUT) # Relay 1
        GPIO.output(18, True) # Turn off Relay
    
        GPIO.output(18, False)
        print('FAN #1 ON')
        time.sleep(60) # 1 minute
        GPIO.output(18, True)
        print('FAN #1 OFF')
        print('')
        print('Rechecking Heat Level...')
        print('')
        time.sleep(1)
            
    else:
        print("Temperature: %d F" % ((result.temperature * (9/5)) + 32))
        time.sleep(2)
        
    time.sleep(0.5)