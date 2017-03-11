/**
 *  Lighting Change on Motion
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
    name: "Lighting Change on Motion",
    namespace: "tgeairn",
    author: "Tom Geairn",
    description: "Make a change in lighting on motion, optionally returning to previous state after motion stops",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Triggers") {
    	input "triggerSwitch", "capability.switch", required: false, multiple: true, title: "Switch?"
        input "triggerMotion", "capability.motionSensor", required: false, multiple: true, title: "Motion?"
        input "triggerContact", "capability.contactSensor", required: false, multiple: true, title: "Contact?"
	}
    section("Results") {
    	input "theDimmers", "capability.switchLevel", required: false, multiple: true, title: "Dimmers"
        input "theDimmersLevel", type: "number", range: "0..100", required: true, title: "Level"
    }
    section("Behavior") {
    	input "boolReturn", type: "bool", required: true, title: "Return to previous level?"
        input "numReturnTime", type: "number", required: false, title: "Minutes Later"
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
    if (triggerSwitch!=null) {subscribe(triggerSwitch, "switch.on", doTriggerOn())}
    if (triggerSwitch!=null) {subscribe(triggerSwitch, "switch.on", doTriggerOff())}
    if (triggerMotion!=null) {subscribe(triggerMotion, "motion.active", doTriggerOn())}
    if (triggerMotion!=null) {subscribe(triggerMotion, "motion.inactive", doTriggerOff())}
    if (triggerContact!=null) {subscribe(triggerContact, "contact.open", doTriggerOn())}
    if (triggerContact!=null) {subscribe(triggerContact, "contact.closed", doTriggerOff())}
}

// TODO: implement event handlers
def doTriggerOn(evt) {
	// If triggered newly, retain the current level and set the new one
    
}

def doTriggerOff(evt) {
    
}