package com.mousecap;

import java.util.Vector;

public class MousecapData {
	static private Vector<Gesture> gestures = new Vector<Gesture>();
	
	static public Vector<Gesture> getGestures() {
		return gestures;
	}
	
	static public void addGesture(Gesture gesture) {
		gestures.add(gesture);
	}
	
	static public void removeGesture(Gesture gesture) {
		gestures.
	}
}
