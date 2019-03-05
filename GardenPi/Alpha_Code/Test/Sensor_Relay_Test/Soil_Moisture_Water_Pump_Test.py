# Check Water Value and Water if needed.
# Author: Luke Duvall
import time

# Import SPI library (for hardware SPI) and MCP3008 library.
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# import GPIO library (for hardware GPIO)
import RPi.GPIO as GPIO

# Hardware SPI configuration:
SPI_PORT   = 0
SPI_DEVICE = 0
mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))

# Intro
print('Soil Mositure and Water Pump Test')
print('---------------------------------')
print('')
print('Starting Test...')
print('')

while True:
    
    value = mcp.read_adc(0)

    if value > 600:
        
        print('Reading MCP3008 Values')
        print('----------------------')
        print('Channel 1 - {0:>4}'.format(value))
        time.sleep(1)
        print('Needs Water...')
        time.sleep(3)
        print('Watering Now')
        print('')
        time.sleep(10)
        
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(23, GPIO.OUT) # Relay 4
        GPIO.output(23, True) # Turn off Relay
        
        GPIO.output(23, False)
        print('WATER PUMP ON')
        time.sleep(4.5) # 4 Seconds
        GPIO.output(23, True)
        print('WATER PUMP OFF')
        print('')
        print('Rechecking Post Watering...')
        print('')
        time.sleep(60)
        
        GPIO.cleanup()
        
    else:
        
        print('Reading MCP3008 Values')
        print('----------------------')
        print ('Channel 1 - {0:>4}'.format(value))
        print('')
        time.sleep(3600) # Check every 30 mins