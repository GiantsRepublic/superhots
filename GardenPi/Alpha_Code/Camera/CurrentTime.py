# Class: CurrentTime.py
# Luke Duvall
# This class get current values in the enviorment

# Import Datetime libraries
import datetime

class CurrentTime:
        
    def __init__(self):
            
        self.current_hour = 0
            
    def checkCurrent(self):
            
        # Current Date and Time (Pi Clock)
        current_datetime = datetime.datetime.now()
        
        # Current Hour
        self.current_hour = current_datetime.hour
    
    def getHour(self):
        
        return self.current_hour