package com.mousecap;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Vector;

import com.mousecap.graphics.Window;

public class Recorder {
	static Vector<Point> vect;
	static Boolean flag;
	static int ignoreSize = 1;
	public static synchronized void startRecording() {
		
		vect=new Vector<Point>();
		new Thread(new Runnable(){
			public void run(){
				Window window = new Window();
				window.setVisible(true);
				flag=true;
				while(flag){
					Point cur = MouseInfo.getPointerInfo().getLocation();
					if(vect.isEmpty()) {
						vect.add(cur);
						window.newPoint(cur);
						continue;
					}
					Point last = vect.lastElement();
					
					if(Math.abs(last.x-cur.x) + Math.abs(last.y-cur.y) < ignoreSize) continue;
					window.newPoint(cur);
					vect.add(cur);
				}
				window.setVisible(false);
			}
		}).start();
	}
	
	public static synchronized void stopRecording() {
		flag=false;
	}
	
	public static Vector<Point> getPoints() {
		return vect;
	}
}
