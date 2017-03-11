/**
 *  Multiple Devices and Levels
 *
 *  Copyright 2016 Tom Geairn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Multiple Devices and Levels",
    namespace: "tgeairn",
    author: "Tom Geairn",
    description: "Select multiple devices and set up to 4 groups of levels",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Trigger Devices") {
        input "theswitch", "capability.switch", required: false, multiple: true, title: "Switch?"
        input "themotion", "capability.motionSensor", required: false, multiple: true, title: "Motion?"
    }
    section("Device Settings") {
    	input "theOnDevices", "capability.switch", required: false, multiple:true, title: "Devices to turn ON"
    
    	input "theOffDevices", "capability.switch", required: false, multiple:true, title: "Devices to turn OFF"
    	
        input "thedimmers1", "capability.switchLevel", required: false, multiple:true, title: "Dimmer group 1"
        input "thelevels1", "number", required: false, title: "Percent"
    	
        input "thedimmers2", "capability.switchLevel", required: false, multiple:true, title: "Dimmer group 2"
        input "thelevels2", "number", required: false, title: "Percent"

        input "thedimmers3", "capability.switchLevel", required: false, multiple:true, title: "Dimmer group 3"
        input "thelevels3", "number", required: false, title: "Percent"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    subscribe(app, switchOnHandler)
    subscribe(theswitch, "switch.on", switchOnHandler)
    subscribe(theswitch, "switch.off", switchOffHandler)
    subscribe(themotion, "motionSensor.active", switchOnHandler)
}

// event handlers
def switchOnHandler(evt) {
	//TODO: implement!
    if (theOnDevices!=null) {theOnDevices.on()}
    if (theOffDevices!=null) {theOffDevices.off()}
    if (thedimmers1!=null) {thedimmers1.setLevel(thelevels1)}
    if (thedimmers2!=null) {thedimmers2.setLevel(thelevels2)}
    if (thedimmers3!=null) {thedimmers3.setLevel(thelevels3)}
}

def switchOffHandler(evt) {
    if (theOnDevices!=null) {theOnDevices.off()}
    if (thedimmers1!=null) {thedimmers1.off()}
    if (thedimmers2!=null) {thedimmers2.off()}
    if (thedimmers3!=null) {thedimmers3.off()}
}