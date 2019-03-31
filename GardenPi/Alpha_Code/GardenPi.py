# GardenPi.py
# Author: Luke Duvall

# Math library
import math

# Import Date and Time libraries
import time
import datetime

# Import SPI library (for hardware SPI) and MCP3008 library
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# Import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

# Import DHT11 library
import dht11

# Import Firebase library
from firebase import firebase

# Hardware SPI configuration:
SPI_PORT   = 0
SPI_DEVICE = 0
mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))

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

# Firebase Database Reference
fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

# Firebase tracker
#count = 1

# Intro Message
print('Welcome to Garden Pi')
print('--------------------')
print('')
print('Checking System...')
print('')

while True:
    
    # Set Enivormental Thresholds
    #temp_threshold = fb.get('user/key/plants/reaper/temp_threshold/', 'set_threshold')
    #humid_threshold = fb.get('user/key/plants/reaper/humid_threshold/', 'set_threshold')
    #moist_threshold = fb.get('user/key/plants/reaper/moist_threshold/', 'set_threshold')
    #light_on = fb.get('user/key/plants/reaper/timer/', 'light_on' )
    #light_off = fb.get('user/key/plants/reaper/timer/', 'light_off')
    
    # Pre Set Thresholds
    temp_threshold = 85
    humid_threshold = 60
    moist_threshold = 30
    light_on = 8
    light_off = 19
    
    # Show Threshold Values
    print('Thresholds Set')
    print('--------------------')
    print('FAN #1 (Temperature): ' + str(temp_threshold))
    print('FAN #2 (Humidity): ' + str(humid_threshold))
    print('Light On: ' + str(light_on))
    print('Light Off: ' + str(light_off))
    print('WATER PUMP (Moisture): ' + str(moist_threshold))
    print('')
    
    # Soil Moisture (MCP3008)
    currentLevel = mcp.read_adc(0)
    dryPercent = (currentLevel / 1000) * 100
    wetPercent = 100 - int(dryPercent)
    
    # Temperature and Humidity (DHT11)
    valid = 1
    while valid != 0:
        dht11_instance = dht11.DHT11(pin=17)
        dht_11 = dht11_instance.read()
        if dht_11.is_valid():
            valid = 0
    
    # Enviormental Variables
    temperature = int((dht_11.temperature * (9/5)) + 32)
    humidity = int(dht_11.humidity)
    sat_vapor = int(6.11 * 10.0**((7.5 * dht_11.temperature)/(237.7 +dht_11.temperature)))
    act_vapor = int((humidity * sat_vapor)/100)
    dew_c = ((-430.22 + 237.7 * math.log(act_vapor))/((-1 * math.log(act_vapor))+19.08))
    dew_f = int((9.0/5.0) * dew_c + 32)
    
    # Current Date and Time (Pi Clock)
    current_datetime = datetime.datetime.now()

    # Test Plant Values
    print('Testing Plant')
    print('-------------')
    print ('Date: ' + current_datetime.strftime("%m/%d/%Y"))
    print ('Time: ' + current_datetime.strftime("%H:%M:%S"))
    print('Soil Moisture: {0:.1f} %'.format(wetPercent))
    print('Temperature: %d F' % temperature)
    print('Humidity: %d %%' % humidity)
    print('Satuarated Vapor: %d mb' % sat_vapor)
    print('Actual Vapor: %d mb' % act_vapor)
    print('Dew Point: %d F' % dew_f)
    print('')
    print('System Check')
    print('------------')
    
    temp_fb = fb.patch('user/key/plants/reaper/temp/', {'current':temperature})
    humid_fb = fb.patch('user/key/plants/reaper/humid/', {'current':humidity})
    sat_vap_fb = fb.patch('user/key/plants/reaper/saturatedvapor/', {'current':sat_vapor})
    act_vap_fb = fb.patch('user/key/plants/reaper/actualvapor/', {'current':act_vapor})
    dew_fb = fb.patch('user/key/plants/reaper/dewpoint/', {'current':dew_f})
    moist_fb = fb.patch('user/key/plants/reaper/moisture/', {'current':wetPercent})
    date_fb = fb.patch('user/key/plants/reaper/date/', {'current':current_datetime.strftime('%m/%d/%Y')})
    time_fb = fb.patch('user/key/plants/reaper/time/', {'current':current_datetime.strftime('%H:%M:%S')})
                                                                     
    # Check Soil Moisture
    if wetPercent <= moist_threshold:    # <= 40%
        
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
    if temperature >= temp_threshold:    # >= 85 degrees F
        
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
    if humidity >= humid_threshold:    # >= 60%
        
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
    if(current_datetime.hour >= light_on and current_datetime.hour < light_off):
        
        # Light State
        print('LIGHT - ON')
        
        # Turn on light
        GPIO.output(22, False)
        
        # End of test
        print('---------------------')
        print('')
    
    else:
        
        # Light State
        print('LIGHT - OFF')
        
        # Turn off light
        GPIO.output(22, True)
        
        # End of test
        print('---------------------')
        print('')
    
    # Wait 5 mins
    time.sleep(300)