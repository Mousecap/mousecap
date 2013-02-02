package com.mousecap.algorithm;

import java.awt.Point;
import java.util.Vector;

public class Utilities {
	public static final double ERROR_MARGIN = Math.PI/5;
	public static final int JUMP_POINTS = 20;
	public static final int IGNORE_ENDPOINTS = 3;
	public static final double MAX_DIST = 30;
	
	public static double getDirection(Point point1, Point point2) {
		if(point1.x == point2.x) {
			if(point2.y>point1.y) return Math.PI/2;
			return -Math.PI/2;
		}
		double atan = Math.atan(((double) point2.y-point1.y)/(point2.x-point1.x));
		if(point2.x<point1.x) return Math.PI+atan;
		return atan;
	}
	public static double getDirection(Vector<Point> vect, int index) {
		return(getDirection(vect.get(index),vect.get(index+1)));
	}
	
	public static boolean compareDirections(double direction1, double direction2) {
		double diff = Math.abs(direction1-direction2);
		if(diff < ERROR_MARGIN || 2*Math.PI-diff < ERROR_MARGIN) return true;
		return false;
	}
	
	public static boolean compareGestures(Vector<Point> vect1, Vector<Point> vect2) {
		if(vect1.size()<JUMP_POINTS || vect2.size()<JUMP_POINTS) 
			return false;
		double direction = getDirection(vect1,0,JUMP_POINTS);
		
		int i=0,j=0;
		while(i+JUMP_POINTS<vect1.size() && j+JUMP_POINTS<vect2.size()) {
			if(!compareDirections(getDirection(vect1, i,JUMP_POINTS), getDirection(vect2, j,JUMP_POINTS)) 
					&& i>=IGNORE_ENDPOINTS && j>=IGNORE_ENDPOINTS) {
				System.out.println("Singature authentication failed for");
				System.out.println(vect1.get(i)+" "+vect1.get(i+JUMP_POINTS));
				System.out.println(vect2.get(j)+" "+vect2.get(j+JUMP_POINTS));
				return false;
			}
			direction=getDirection(vect1, i,JUMP_POINTS);
			System.out.println(direction);
			while(i+JUMP_POINTS<vect1.size() && compareDirections(direction, getDirection(vect1, i,JUMP_POINTS)))
				i++;
			while(j+JUMP_POINTS<vect2.size() && compareDirections(direction, getDirection(vect2, j,JUMP_POINTS)))
				j++;
			while(i+JUMP_POINTS<vect1.size() && Math.abs(vect1.get(i).x-vect1.get(i+JUMP_POINTS).x)+Math.abs(vect1.get(i).y-vect1.get(i+JUMP_POINTS).y) <= (JUMP_POINTS+1)*4/5)
				i++;
			while(j+JUMP_POINTS<vect2.size() && Math.abs(vect2.get(j).x-vect2.get(j+JUMP_POINTS).x)+Math.abs(vect2.get(j).y-vect2.get(j+JUMP_POINTS).y) <= (JUMP_POINTS+1)*4/5)
				j++;
			
		}
		if(i+JUMP_POINTS+IGNORE_ENDPOINTS<vect1.size() || j+JUMP_POINTS+IGNORE_ENDPOINTS<vect2.size()) {
			System.out.println("A signature finished before the other one");
			return false;
		}
		
		return true;
	}
	public static Vector<Point> getDirSeq(Vector<Point> points) {
		int beginning = 0;
		int i=beginning;
		return points;
		
		
	}
	
	public static double rateDirSeq(Vector<Point> dirSeq1, Vector<Point> dirSeq2) {
		double result = 0;
		double l1 = length(dirSeq1);
		double l2 = length(dirSeq2);
		double dl1=0;
		for(Point p1 : dirSeq1) {
			double dl2=0;
			for(Point p2 : dirSeq2) {
				result += Math.abs(dot(p1,p2))*(1-Math.abs(dl1/l1-dl2/l2))*(1-Math.abs((dl1+length(p1)/l1+dl2+length(p2))));
			}
		}
		return result;
	}
	private static double dot(Point p1, Point p2) {
		return p1.x*p2.x+p1.y*p2.y;
	}
	private static double length(Vector<Point> dirSeq) {
		double sum = 0;
		for(Point p : dirSeq)
			sum+=length(p);
		return sum;
	}
	private static double length(Point p) {
		
		return Math.sqrt(p.x*p.x+p.y*p.y);
	}
	public static double getDirection(Vector<Point> vect, int index, int increment) {
		return(getDirection(vect.get(index),vect.get(index+increment)));
	}
}
