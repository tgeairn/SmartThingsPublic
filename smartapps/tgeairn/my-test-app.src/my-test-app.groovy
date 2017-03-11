/**
 *  My Test App
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
    name: "My Test App",
    namespace: "tgeairn",
    author: "Tom Geairn",
    description: "Test App",
    category: "SmartThings Labs",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    oauth: true)


preferences {
	section("Turn on when motion detected:") {
    	input "themotion", "capability.motionSensor", required: true, title: "Where?"
	}
    section("Turn off when there's been no movements for:") {
    	input "minutes", "number", required: true, title: "Minutes"
    }
    section("Turn on/off this switch:") {
    	input "theswitch", "capability.switch", required: true
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
    subscribe(themotion, "motion.active", motionDetectedHandler)
    subscribe(themotion, "motion.inactive", motionStoppedHandler)
}

// TODO: implement event handlers
def motionDetectedHandler(evt) {
	log.debug "motionDetectedHandler called: $evt"
    theswitch.on()
}

def motionStoppedHandler(evt) {
	log.debug "motionStoppedHandler called: $evt"
    runIn(60 * minutes, checkMotion)
}

def checkMotion() {
	log.debug "In checkMotion scheduled method"
    
    // get the current state for motion detector
    def motionState = themotion.currentState("motion")
    
    if (motionState.value == "inactive") {
    	// get the time elapsed since the motion reported inactive
        def elapsed = now() - motionState.date.time
        
        // elapsed time is in milliseconds, so convert threshold to milliseconds too
        def threshold = (1000 * 60 * minutes) - 1000
        
        if (elapsed >= threshold) {
        	log.debug "Motion inactive log enough since last check ($elapsed ms): turning off"
            theswitch.off()
        }
        else {
        	log.debug "Motion not inactive log enough ($elapsed ms): do nothing"
        }
    }
    else {
    	//Motion active, log and do nothing
        log.debug "Motion active, do nothing and wait"
    }
}

