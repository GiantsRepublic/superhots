# camera.py
# Author: Luke Duvall
# Timelapse

from picamera import PiCamera
from time import sleep
import datetime

# Timelapse Cycle Variables (7,18)
cam_on = 3
cam_off = 18

camera = PiCamera()
camera.resolution = (1296,972)
camera.rotation = 270

def wait():
    
    current_datetime = datetime.datetime.now()
    
    # Check Time
    if(current_datetime.hour >= cam_on and current_datetime.hour < cam_off):
        sleep(300)
        
    else:
        sleep(300)
        wait()
        
# Start of program

current_hour = datetime.datetime.now()

if(current_hour.hour >= cam_on and current_hour.hour < cam_off):
    for filename in camera.capture_continuous('/home/pi/Desktop/Camera/Pictures/Reaper - {timestamp:%m%d%Y_%H%M%S}.jpg'):
        wait()
else:
    wait()