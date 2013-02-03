package com.mousecap.graphics;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JComponent;

import com.mousecap.Gesture;
import com.mousecap.algorithm.Utilities;


public class GestureGraphPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gesture gesture;
	public void paint(Graphics g){
		g.setColor(Color.black);
		if(gesture == null) return;
		
		Vector<Point> dirSeq = Utilities.getDirSeq(gesture.getPoints());
		Vector<Point> points = new Vector<Point>();
		points.add(new Point(0,0));
		for(Point p : dirSeq) {
			points.add(new Point(points.lastElement().x+p.x,points.lastElement().y+p.y));
		}
		if(points == null || points.size() < 1) return;
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
		double scale = Math.min(vscale, hscale);
		Point prev = points.firstElement();
		for(Point p : points) {
			g.drawLine((int)(bounds.x+0.1*bounds.width+(prev.x-minX)*scale), (int)(bounds.y+0.1*bounds.height+(prev.y-minY)*scale), (int)(bounds.x+0.1*bounds.width+(p.x-minX)*scale), (int)(bounds.y+0.1*bounds.height+(p.y-minY)*scale));
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
