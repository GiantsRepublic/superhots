# GardenPi.py
# Author: Luke Duvall

# Import Date and Time libraries
import time
import datetime

# Import SPI library (for hardware SPI) and MCP3008 library.
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# Import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

# Import DHT11 library
import dht11

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

# Light Cycle Variables
light_on = 7
light_off = 18

# Intro Message
print('Welcome to Garden Pi')
print('--------------------')
print('')
print('Checking System...')
print('')

while True:
    
    # Soil Moisture Variable (MCP3008)
    currentLevel = mcp.read_adc(0)
    dryPercent = (currentLevel / 1000) * 100
    wetPercent = 100 - dryPercent
    
    # Temperature and Humidity Variables (DHT11)
    valid = 1
    while valid != 0:
        dht11_instance = dht11.DHT11(pin=17)
        dht_11 = dht11_instance.read()
        if dht_11.is_valid():
            valid = 0
            
    temperature = ((dht_11.temperature * (9/5)) + 32)
    humidity = dht_11.humidity
    
    # Current Date and Time (Pi Clock)
    current_datetime = datetime.datetime.now()

    print('Testing Plant')
    print('-------------')
    print ('Date: ' + current_datetime.strftime("%m/%d/%Y"))
    print ('Time: ' + current_datetime.strftime("%H:%M:%S"))
    print('Soil Moisture: {0:.1f} %'.format(wetPercent))
    print('Temperature: %d F' % temperature)
    print('Humidity: %d %%' % humidity)
    print('')
    print('System Check')
    print('------------')
    
    # Check Soil Moisture
    if wetPercent <= 30:    # <= 30%
        
        # Moisture state
        print('Moisture - LOW')
        
        # Turn on/off water pump
        GPIO.output(23, False)
        print('WATER PUMP - ON')
        time.sleep(2.5)    # 2 second water cycle 
        GPIO.output(23, True)
        print('WATER PUMP - OFF')
        
    else:
        
        # Moisture state
        print('Moisture - GOOD')
        print('WATER PUMP - OFF')
        
    # Check Temperature
    if temperature >= 85:    # >= 85 degrees F
        
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
    if humidity >= 60:    # >= 60%
        
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