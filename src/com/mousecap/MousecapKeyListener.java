package com.mousecap;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class MousecapKeyListener implements NativeKeyListener{
	private Boolean shift_down=false;
	private Boolean ctrl_down=false;
	private Boolean running = false;
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent keyEvent) {
		if(running) return;
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_SHIFT) shift_down = true;
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_CONTROL) ctrl_down = true;
		if(ctrl_down && shift_down)
			Recorder.startRecording();
	}

	

	@Override
	public void nativeKeyReleased(NativeKeyEvent keyEvent) {
		
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_SHIFT) synchronized (shift_down) {
			Recorder.stopRecording();
			shift_down=false;
			
		}	
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_CONTROL) synchronized (ctrl_down) {
			Recorder.stopRecording();
			ctrl_down=false;
		}
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
