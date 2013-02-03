package com.mousecap;

import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JFrame;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.mousecap.algorithm.Utilities;
import com.mousecap.graphics.SwingWindow;

public class MousecapKeyListener implements NativeKeyListener{
	private Boolean shift_down=false;
	private Boolean ctrl_down=false;
	private int output = EXECUTE_GESTURES;
	private SwingWindow.Notificator notificator = null;
	public static final int EXECUTE_GESTURES = -1;
	public static final int ADD_NEW_GESTURES = -2;
	
	private static MousecapKeyListener instance = new MousecapKeyListener();
	
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
		Collection<Gesture> gestures = data.getGestures();
		if(output == EXECUTE_GESTURES) {
			for(Gesture gesture : gestures) {
				if(gesture == null) continue;
				if(Utilities.compareGestures(points, gesture.getPoints())) {
					gesture.executeScript();
					break;
				}
			}
		}
		else if(output == ADD_NEW_GESTURES) {
			Gesture gesture = new Gesture("New Gesture");
			gesture.setPoints(points);
			data.add(gesture);
			if(notificator != null)
				notificator.update();
		}
		else {
		Gesture gesture = data.find(output);
			if( gesture !=null) {
				gesture.setPoints(points);
			}
			else {
				gesture = new Gesture();
				gesture.setPoints(points);
				data.set(output,gesture);
				
			}
			if(notificator != null)
				notificator.update();
		}
		
	}



	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
	}
	
	public void setOutput(int output) {
		this.output = output;
	}
	public static MousecapKeyListener getInstance() {
		return instance;
	}

	public SwingWindow.Notificator getNotificator() {
		return notificator;
	}



	public void setNotificator(SwingWindow.Notificator notificator) {
		this.notificator = notificator;
	}
	

}
