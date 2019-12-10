import Adafruit_DHT
from pulsesensor import Pulsesensor
import time
import requests
import math
import random

TOKEN = "BBFF-F6tkIfkRVFGSUpY6nod4lMbSuD8FH2"  # Put your TOKEN here
DEVICE_LABEL = "RaspberryJCDA"  # Put your device label here 
VARIABLE_LABEL_1 = "temperatura"  # Put your first variable label here
VARIABLE_LABEL_2 = "humedad"  # Put your second variable label here
VARIABLE_LABEL_3 = "posicion"  # Put your second variable label here
VARIABLE_LABEL_4= "pulso_sensor"
p = Pulsesensor()
p.startAsyncBPM()


def build_payload(variable_1, variable_2, variable_3,variable_4):
    # Creates two random values for sending data
    
	sensor = Adafruit_DHT.DHT11
	pin=4
  	humedad, temperatura = Adafruit_DHT.read_retry(sensor, pin)
	value_1 = temperatura 
	value_2 = humedad

	#Create a ramdom pulse sensor
    	try:
            while True:
                bpm = p.BPM
                if bpm > 0:
                    print("BPM: %d" % bpm)
                else:
                    print("No Heartbeat found")
                time.sleep(1)
        except:
            p.stopAsyncBPM()

    # Creates a random gps coordinates
	lat = -0.3804
    	lng = -78.49258
    	payload = {variable_1: value_1,
               variable_2: value_2,
               variable_3: {"value": 1, "context": {"lat": lat, "lng": lng}}variable_4:bpm}

    	

	return payload


def post_request(payload):
    # Creates the headers for the HTTP requests
    url = "http://industrial.api.ubidots.com"
    url = "{}/api/v1.6/devices/{}".format(url, DEVICE_LABEL)
    headers = {"X-Auth-Token": TOKEN, "Content-Type": "application/json"}

    # Makes the HTTP requests
    status = 400
    attempts = 0
    while status >= 400 and attempts <= 5:
        req = requests.post(url=url, headers=headers, json=payload)
        status = req.status_code
        attempts += 1
        time.sleep(1)

    # Processes results
    if status >= 400:
        print("[ERROR] Could not send data after 5 attempts, please check \
            your token credentials and internet connection")
        return False

    print("[INFO] request made properly, your device is updated")
    return True


def main():
    payload = build_payload(
        VARIABLE_LABEL_1, VARIABLE_LABEL_2, VARIABLE_LABEL_3,VARIABLE_LABEL_4)

    print("[INFO] Attemping to send data")
    post_request(payload)
    print("[INFO] finished")


if __name__ == '__main__':
    while (True):
        main()
        time.sleep(1)
