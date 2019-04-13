# GardenPi.py
# Author: Luke Duvall
# Main program that runs the GardenBot

# Import Time library
import time

time.sleep(20)

# Import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

# Import Classes
from Threshold import Threshold
from CurrentEnvironment import CurrentEnvironment

# Import Firebase library
from firebase import firebase

#time.sleep(20)

# Firebase Database Reference
fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

# Hardware GPIO configuration
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()

# Relay Hardware Configuration
GPIO.setup(18, GPIO.OUT) # Relay 1
GPIO.output(18, True) # Reset Relay
GPIO.setup(27, GPIO.OUT) # Relay 2
GPIO.output(27, True) # Reset Relay
GPIO.setup(22, GPIO.OUT) # Relay 3
GPIO.output(22, True) # Reset Relay
GPIO.setup(23, GPIO.OUT) # Relay 4
GPIO.output(23, True) # Reset Relay

# Initatiate Levels and Thresholds
ce = CurrentEnvironment()
th = Threshold()

print('----------------------')

while True:
    
    #th.setThreshold(fb)
    ce.checkCurrent(fb)
                                                                     
    # Check Soil Moisture
    if ce.getMoist() <= th.getMoistThresh():
        # Moisture state
        print('Moisture - LOW')
        # Turn on/off water pump
        GPIO.output(23, False)
        print('WATER PUMP - ON')
        time.sleep(3)    # 3 second water cycle 
        GPIO.output(23, True)
        print('WATER PUMP - OFF')
        
    else:
        # Moisture state
        print('Moisture - GOOD')
        print('WATER PUMP - OFF')
        
    # Check Temperature
    if ce.getTemp() >= th.getTempThresh():
        # Temperature State
        print('Temperature - HOT')
        # Turn on fan #1
        GPIO.output(18, False)
        print('FAN #1 - ON')
        
    else:
        # Temperature State
        print('Temperature - GOOD')
        # Turn off fan #1
        GPIO.output(18, True)
        print('FAN #1 - OFF')
    
    # Check Humidity
    if ce.getHumid() >= th.getHumidThresh():
        # Humidity State
        print('Humidity - TOO HUMID')
        # Turn on fan #1
        GPIO.output(27, False)
        print('FAN #2 - ON')
        
    else:
        # Humidity State
        print('Humidity - GOOD')
        # Turn off fan #1
        GPIO.output(27, True)
        print('FAN #2 - OFF')
    
    # Check Light Cycle
    if(ce.getHour() >= th.getTimeOn() and ce.getHour() < th.getTimeOff()):
        # Light State
        print('LIGHT - ON') 
        # Turn on light
        GPIO.output(22, False)
    
    else:
        # Light State
        print('LIGHT - OFF')
        # Turn off light
        GPIO.output(22, True)
    
    print('----------------------')
    # Wait 30 seconds
    time.sleep(30)