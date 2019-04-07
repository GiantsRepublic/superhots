# Threshold.py
# Luke Duvall
# Initiate Thresholds and Check/Set Threshold Changes

class Threshold:
    
    def __init__(self):
        
        self.time_on = 7
        self.time_off = 19
        
    def checkThreshold(self,fb):
        
        self.time_on = fb.get('/user/key/plants/reaper/timer', 'light_on' )
        self.time_off = fb.get('/user/key/plants/reaper/timer', 'light_off')
        
    def getTimeOn(self):
        
        return self.time_on
        
    def getTimeOff(self):
        
        return self.time_off