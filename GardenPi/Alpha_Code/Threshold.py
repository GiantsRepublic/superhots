# Threshold.py
# Luke Duvall
# Initiate Thresholds and Check/Set Threshold Changes

class Threshold:
    
    def __init__(self):
        
        self.temp_threshold = 85
        self.humid_threshold = 60
        self.moist_threshold = 25
        self.time_on = 7
        self.time_off = 19
        
    def setThreshold(self,fb):
        
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/temp', 'threshold') is not None:
            self.temp_threshold = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/temp', 'threshold')
        else:
            temp_thresh_fb = fb.patch('user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/temp', {'threshold':self.temp_threshold})
        
            
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/humid', 'threshold') is not None:
            self.humid_threshold = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/humid', 'threshold')
        else:
            humid_thresh_fb = fb.patch('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/humid', {'threshold':self.humid_threshold})
            
            
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/moisture', 'threshold') is not None:
            self.moist_threshold = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/moisture', 'threshold')
        else:
            moist_thresh_fb = fb.patch('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/moisture', {'threshold':self.moist_threshold})
            
            
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_on') is not None:
            self.time_on = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_on')
        else:
            time_on_fb = fb.patch('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', {'light_on':self.time_on})
            
            
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_off') is not None:
            self.time_off = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_off')
        else:
            time_off_fb = fb.patch('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', {'light_off':self.time_off})
        
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