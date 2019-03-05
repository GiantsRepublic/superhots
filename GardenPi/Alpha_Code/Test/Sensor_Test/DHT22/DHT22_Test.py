import RPi.GPIO as GPIO
import dht11
import time
import datetime

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()

while True:
    
    # read data using pin 17
    instance = dht11.DHT11(pin=17)
    
    result = instance.read()
    if result.is_valid():
        print("Last valid input: " + str(datetime.datetime.now()))
        print("Temperature: %d F" % ((result.temperature * (9/5)) + 32))
        print("Humidity: %d %%" % result.humidity)
        
    time.sleep(1)