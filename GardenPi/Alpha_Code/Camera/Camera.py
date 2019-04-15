# Camera.py
# Author: Luke Duvall
# Main program that runs the GardenBot

# Import Time library
import time

# Import Pi Camera library
from picamera import PiCamera

# Import Firebase libraries
from google.cloud import storage
from firebase import firebase

# Import Classes
from Timer import Timer
from CurrentTime import CurrentTime

# Instance of Firebase
fb = firebase.FirebaseApplication('https://test-5487a.firebaseio.com/', None)

# Instance of Storage
storage_client = storage.Client.from_service_account_json('/home/pi/superhots/GardenPi/Alpha_Code/Camera/test-76183593397d.json')
bucket = storage_client.get_bucket('test-5487a.appspot.com')

# Pi Camera Setup
camera = PiCamera()
camera.resolution = (1296,972)
camera.rotation = 270

# Instance of Levels and Thresholds
ce = CurrentTime()
th = Timer()

while True:
    
    # Get Timer Changes and Current Time
    th.setTimer(fb)
    ce.checkCurrent()
    
    # Create timestamp string for timelapse picture
    timestamp = time.strftime('%m%d%Y_%H%M%S')
    
    # Check Timer
    if(ce.getHour() >= th.getTimeOn() and ce.getHour() < th.getTimeOff()):
        # Start Camera
        camera.start_preview()
        time.sleep(2)
        # Capture Timelapse
        camera.capture('/home/pi/Pictures/Reaper - ' + timestamp + '.jpg')
        time.sleep(2)
        # Capture Current Snapshot
        camera.capture('/home/pi/Pictures/currentpicture.jpg')
        
        # Upload to Firebase
        current_picture = bucket.blob('currentpicture.jpg')
        current_picture.upload_from_filename(filename='/home/pi/Pictures/currentpicture.jpg')
        
        time.sleep(30)    # Wait 30 Seconds
    else:
        time.sleep(30)    # Wait 30 Seconds