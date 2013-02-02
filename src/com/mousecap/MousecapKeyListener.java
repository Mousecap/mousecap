package com.mousecap;

import java.awt.Point;
import java.util.Vector;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.mousecap.algorithm.Utilities;

public class MousecapKeyListener implements NativeKeyListener{
	private Boolean shift_down=false;
	private Boolean ctrl_down=false;
	private int output = EXECUTE_GESTURES;
	
	public static final int EXECUTE_GESTURES = -1;
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent keyEvent) {
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_SHIFT) shift_down = true;
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_CONTROL) ctrl_down = true;
		if(ctrl_down && shift_down)
			Recorder.startRecording();
	}

	

	@Override
	public void nativeKeyReleased(NativeKeyEvent keyEvent) {
		
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_SHIFT) synchronized (shift_down) {
			Recorder.stopRecording();
			ship(Recorder.getPoints());
			shift_down=false;
			
			
		}	
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_CONTROL) synchronized (ctrl_down) {
			Recorder.stopRecording();
			ctrl_down=false;
		}
		
	}

	private void ship(Vector<Point> points) {
		MousecapData data = MousecapData.getInstance();
		Vector<Gesture> gestures = data.getGestures();
		if(output == EXECUTE_GESTURES) {
			for(int i=0;i<gestures.size();i++) {
				if(gestures.get(i) == null) continue;
				if(Utilities.compareGestures(points, gestures.get(i).getPoints())) {
					gestures.get(i).executeScript();
					break;
				}
			}
		}
		else if(output < data.getSize()) {
			gestures.get(output).setPoints(points);
		}
		
	}



	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setOutput(int output) {
		this.output = output;
	}
	

}
