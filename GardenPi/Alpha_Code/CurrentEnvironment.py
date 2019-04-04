# Class: CurrentEnvironment.py
# Luke Duvall
# This class get current values in the enviorment

# Import Firebase library 
from firebase import firebase

# Math library
import math

# Import Datetime libraries
import datetime

# Import SPI library (for hardware SPI) and MCP3008 library
import Adafruit_GPIO.SPI as SPI
import Adafruit_MCP3008

# Import DHT11 library
import dht11

# Hardware SPI configuration:
SPI_PORT   = 0
SPI_DEVICE = 0
mcp = Adafruit_MCP3008.MCP3008(spi=SPI.SpiDev(SPI_PORT, SPI_DEVICE))

# Firebase Database Reference
fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

class CurrentEnvironment:
        
    def __init__(self):
            
        self.temp = 0
        self.humid = 0
        self.moist = 0
        self.sat_vapor = 0
        self.act_vapor = 0
        self.dew_f = 0
        self.current_hour = 0
            
    def checkCurrent(self):
            
        # Temperature and Humidity (DHT11)
        valid = 1
        while valid != 0:
            dht11_instance = dht11.DHT11(pin=17)
            dht_11 = dht11_instance.read()
            if dht_11.is_valid():
                valid = 0
        
        # Soil Moisture (MCP3008)
        currentLevel = mcp.read_adc(0)
        dryPercent = (currentLevel / 1000) * 100
        self.moist = 100 - int(dryPercent)
        
        # Enviormental Calculations
        self.temp = int((dht_11.temperature * (9/5)) + 32)
        self.humid = int(dht_11.humidity)
        self.sat_vapor = int(6.11 * 10.0**((7.5 * dht_11.temperature)/(237.7 +dht_11.temperature)))
        self.act_vapor = int((self.humid * self.sat_vapor)/100)
        dew_c = ((-430.22 + 237.7 * math.log(self.act_vapor))/((-1 * math.log(self.act_vapor))+19.08))
        self.dew_f = int((9.0/5.0) * dew_c + 32)
            
        # Current Date and Time (Pi Clock)
        current_datetime = datetime.datetime.now()
        
        # Current Hour
        self.current_hour = current_datetime.hour
            
        temp_fb = fb.patch('user/key/plants/reaper/temp/', {'current':self.temp})
        humid_fb = fb.patch('user/key/plants/reaper/humid/', {'current':self.humid})
        sat_vap_fb = fb.patch('user/key/plants/reaper/saturatedvapor/', {'current':self.sat_vapor})
        act_vap_fb = fb.patch('user/key/plants/reaper/actualvapor/', {'current':self.act_vapor})
        dew_fb = fb.patch('user/key/plants/reaper/dewpoint/', {'current':self.dew_f})
        moist_fb = fb.patch('user/key/plants/reaper/moisture/', {'current':self.moist})
        date_fb = fb.patch('user/key/plants/reaper/date/', {'current':current_datetime.strftime('%m/%d/%Y')})
        time_fb = fb.patch('user/key/plants/reaper/time/', {'current':current_datetime.strftime('%H:%M:%S')})
        
    def getTemp(self):
        
        return self.temp
            
    def getHumid(self):
        
        return self.humid
            
    def getMoist(self):
        
        return self.moist
            
    def getActVapor(self):
        
        return self.act_vapor
            
    def getSatVapor(self):
        
        return self.sat_vapor
            
    def getDewpoint(self):
        
        return self.dew_f
    
    def getHour(self):
        
        return self.current_hour