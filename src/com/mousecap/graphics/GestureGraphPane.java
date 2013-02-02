package com.mousecap.graphics;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JComponent;

import com.mousecap.Gesture;


public class GestureGraphPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gesture gesture;
	public void paint(Graphics g){
		g.setColor(Color.black);
		if(gesture == null) return;
		Vector<Point> points = gesture.getPoints();
		int maxX=points.get(0).x,minX=points.get(0).x;
		int maxY=points.get(0).y,minY=points.get(0).y;
		for(Point point : points) {
			if(point.x > maxX)
				maxX=point.x;
			if(point.x < minX)
				minX=point.x;
			if(point.y > maxY)
				maxY=point.y;
			if(point.y < minY)
				minY=point.y;
			
		}
		Rectangle bounds = g.getClipBounds();
		double vscale = (double)bounds.height/1.25/(maxY-minY);
		double hscale = (double)bounds.width/1.25/(maxX-minX);
		Point prev = points.firstElement();
		for(Point p : points) {
			g.drawLine((int)(bounds.x+0.1*bounds.width+(prev.x-minX)*hscale), (int)(bounds.y+0.1*bounds.height+(prev.y-minY)*vscale), (int)(bounds.x+0.1*bounds.width+(p.x-minX)*hscale), (int)(bounds.y+0.1*bounds.height+(p.y-minY)*vscale));
			prev=p;
		}
		
	}
	public Gesture getGesture() {
		return gesture;
	}
	public void setGesture(Gesture gesture) {
		this.gesture = gesture;
	}
}
