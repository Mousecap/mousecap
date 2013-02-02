package com.mousecap.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JWindow;

public class Window extends JWindow{
	private static final long serialVersionUID = 1L;
	Vector<Point> points = new Vector<Point>();
	public Window(){
		super();

		this.setLocation(0,0);
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		this.setSize(screenRect.getSize());
		
		
		this.setBackground(new Color(255,255,255,64));
	}
	
	public void paint(Graphics g) {
		if(points.size()<2) return;
		for(int i=1;i<points.size();i++) {
			g.drawLine(points.get(i-1).x, points.get(i-1).y, points.get(i).x, points.get(i).y);
		}
	
	}
	
	public void newPoint(Point p) {
		points.add(p);
		repaint();
	}
	
}
