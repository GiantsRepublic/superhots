# camera.py
# Author: Luke Duvall
# Timelapse
import time
time.sleep(20)
from picamera import PiCamera
import datetime
from google.cloud import storage
from firebase import firebase
from Threshold import Threshold
from CurrentTime import CurrentTime

fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

storage_client = storage.Client.from_service_account_json('/home/pi/superhots/GardenPi/Alpha_Code/Camera/test-76183593397d.json')

bucket = storage_client.get_bucket('test-5487a.appspot.com')

camera = PiCamera()
camera.resolution = (1296,972)
camera.rotation = 270

# Initatiate Levels and Thresholds
ce = CurrentTime()
th = Threshold()

while True:
    
    th.checkThreshold(fb)
    ce.checkCurrent()
    
    current_hour = datetime.datetime.now()
    
    timestamp = time.strftime('%m%d%Y_%H%M%S')
    
    if(ce.getHour() >= th.getTimeOn()+1 and ce.getHour() < th.getTimeOff()-1):
        camera.start_preview()
        time.sleep(2)
        camera.capture('/home/pi/Pictures/Reaper - ' + timestamp + '.jpg')
        time.sleep(2)
        camera.capture('/home/pi/Pictures/currentpicture.jpg')
        current_picture = bucket.blob('currentpicture.jpg')
        current_picture.upload_from_filename(filename='/home/pi/Pictures/currentpicture.jpg')
        time.sleep(30)
    else:
        time.sleep(30)