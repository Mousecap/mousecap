package com.mousecap;


import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main {

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
					.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
		}
		
		GlobalScreen.getInstance().addNativeKeyListener(MousecapKeyListener.getInstance());
		
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
		
		while(MousecapData.getInstance().getGestures().size()<3) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("aha!");
		
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.EXECUTE_GESTURES);
		
		
	}

	
}
