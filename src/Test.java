import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Vector;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import com.mousecap.algorithm.Utilities;
import com.mousecap.graphics.Window;


public class Test implements NativeKeyListener{
	
	private Boolean shift_down=false;
	private Boolean ctrl_down=false;
	private Boolean running = false;
	private Vector<Point> temp = new Vector<Point>();
	private Vector<Point> original = null;
	//private static Window window = new Window();
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

		GlobalScreen.getInstance().addNativeKeyListener(new Test());
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent keyEvent) {
		if(running) return;
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_SHIFT) shift_down = true;
		if(keyEvent.getKeyCode() == NativeKeyEvent.VK_CONTROL) ctrl_down = true;
		if(ctrl_down && shift_down)
			record();
	}

	private synchronized void record() {
		new Thread(new Runnable(){
			public void run(){
				Window window = new Window();
				window.setLocation(0,0);
				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				window.setSize(screenRect.getSize());
				
				
				window.setBackground(new Color(255,255,255,64));
				window.setVisible(true);
				
				while(ctrl_down && shift_down){
					Point cur = MouseInfo.getPointerInfo().getLocation();
					if(temp.isEmpty()) {
						temp.add(cur);
						window.newPoint(cur);
						continue;
					}
					Point last = temp.lastElement();
					
					if(Math.abs(last.x-cur.x) + Math.abs(last.y-cur.y) < 10) continue;
					window.newPoint(cur);
					temp.add(cur);
				}
				window.setVisible(false);
				getData(temp);
				temp=new Vector<Point>();
				running = false;
			}
		}).start();
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent keyEvent) {
		
		if(keyEvent.getKeyCode() == KeyEvent.VK_SHIFT) synchronized (shift_down) {
			shift_down=false;
		}	
		if(keyEvent.getKeyCode() == KeyEvent.VK_CONTROL) synchronized (ctrl_down) {
			ctrl_down=false;
		}
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void getData(Vector<Point> vect) {
		if(original == null || original.size()==0) {
			original = vect;
			System.out.println(original);
			return;
		}
		System.out.println(vect);
		System.out.println(original);
		Utilities.compareGestures(vect, original);
	}

}
