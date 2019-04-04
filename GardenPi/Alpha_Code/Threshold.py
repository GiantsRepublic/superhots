# Threshold.py
# Luke Duvall
# Initiate Thresholds and Check/Set Threshold Changes

# Import Firebase library
from firebase import firebase

# Firebase Database Reference
fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

class Threshold:
    
    def __init__(self):
        
        self.temp_threshold = 85
        self.humid_threshold = 40
        self.moist_threshold = 25
        self.time_on = 7
        self.time_off = 19
        
    #def checkThreshold(self):
        
        #self.temp_threshold = fb.get('user/key/plants/reaper/temp/', 'threshold')
        #self.humid_threshold = fb.get('user/key/plants/reaper/humid/', 'threshold')
        #self.moist_threshold = fb.get('user/key/plants/reaper/moist/', 'threshold')
        #self.light_on = fb.get('user/key/plants/reaper/timer/', 'light_on' )
        #self.light_off = fb.get('user/key/plants/reaper/timer/', 'light_off')
        
    def getTempThresh(self):
        
        return self.temp_threshold
        
    def getHumidThresh(self):
        
        return self.humid_threshold
        
    def getMoistThresh(self):
        
        return self.moist_threshold
        
    def getTimeOn(self):
        
        return self.time_on
        
    def getTimeOff(self):
        
        return self.time_off