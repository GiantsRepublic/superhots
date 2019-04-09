# Threshold.py
# Luke Duvall
# Checks for Timer Changes

class Timer:
    
    def __init__(self):
        
        self.time_on = 7
        self.time_off = 19
        
    def setTimer(self,fb):
        
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_on' ) is not None:
            self.time_on = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_on' )
        else:
            time_on_fb = fb.patch('user/xD67kpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', {'light_on':self.time_on})
        
        if fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_off') is not None:
            self.time_off = fb.get('/user/xD67KpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', 'light_off')
        else:
            time_off_fb = fb.patch('user/xD67kpdT7YgZ9qEVv8BrmIAccZ53/plants/reaper/timer', {'light_off':self.time_off})
        
    def getTimeOn(self):
        
        self.time_on = self.time_on + 1
        return self.time_on
        
    def getTimeOff(self):
        
        self.time_off = self.time_off - 1
        return self.time_off