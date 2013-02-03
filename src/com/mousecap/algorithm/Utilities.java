package com.mousecap.algorithm;

import java.awt.Point;
import java.util.Vector;


public class Utilities {
	public static final double ERROR_MARGIN = Math.PI/5;
	public static final int JUMP_POINTS = 20;
	public static final int IGNORE_ENDPOINTS = 3;
	public static final double MAX_DIST = 10;
	public static final double K = 10;
	
	public static double getDirection(Point point1, Point point2) {
		if(point1.x == point2.x) {
			if(point2.y>point1.y) return Math.PI/2;
			return 3*Math.PI/2;
		}
		double atan = Math.atan(((double) point2.y-point1.y)/(point2.x-point1.x));
		if(point2.x<point1.x) atan = Math.PI+atan;
		return atan>0?atan:2*Math.PI+atan;
	}
	public static double getDirection(Vector<Point> vect, int index) {
		return(getDirection(vect.get(index),vect.get(index+1)));
	}
	
	public static boolean compareDirections(double direction1, double direction2) {
		double diff = Math.abs(direction1-direction2);
		if(diff < ERROR_MARGIN || 2*Math.PI-diff < ERROR_MARGIN) return true;
		return false;
	}
	
	public static double directionDif(double direction1, double direction2) {
		return(Math.min(Math.abs(direction1-direction2), 2*Math.PI-Math.abs(direction1-direction2)));
	}
	
	public static boolean compareGestures(Vector<Point> vect1, Vector<Point> vect2) {
		Vector<Point> dirSeq1 = getDirSeq(vect1);
		Vector<Point> dirSeq2 = getDirSeq(vect2);
		return rateDirSeq(dirSeq1, dirSeq2) >0.65;
		/*if(vect1.size()<JUMP_POINTS || vect2.size()<JUMP_POINTS) 
			return false;
		double direction = getDirection(vect1,0,JUMP_POINTS);
		
		int i=0,j=0;
		while(i+JUMP_POINTS<vect1.size() && j+JUMP_POINTS<vect2.size()) {
			if(!compareDirections(getDirection(vect1, i,JUMP_POINTS), getDirection(vect2, j,JUMP_POINTS)) 
					&& i>=IGNORE_ENDPOINTS && j>=IGNORE_ENDPOINTS) {
				return false;
			}
			direction=getDirection(vect1, i,JUMP_POINTS);
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
			return false;
		}
		
		return true;*/
	}
	public static Vector<Point> getDirSeq(Vector<Point> points) {
		Vector<Point> dirSeq = new Vector<Point>();
		int beginning = 0,ending=0;
		Point begP = (Point) points.get(0).clone();
		double min=0,max=0,length=0;
		while(beginning<points.size()) {
			int i=beginning;
			while(i<points.size() && distance(begP,points.get(i))<=MAX_DIST)
				i++;
			if(i<points.size()) {
				max = getMaxAngle(begP,points.get(i));
				min = getMinAngle(begP,points.get(i));
				length = distance(begP,points.get(i));
			}
			while(i<points.size() && checkPointValidity(points.get(i),begP,min,max,length)) {
				max = max > getMaxAngle(begP, points.get(i)) ? getMaxAngle(points.get(beginning), points.get(i)) : max;
				min = min < getMinAngle(begP, points.get(i)) ? getMinAngle(points.get(beginning), points.get(i)) : min;
				length = distance(begP,points.get(i));
				i++;
			}
			ending = i-1;
			while(i<points.size() && isCloseToLine(points.get(i), begP, points.get(ending)) && distance(begP,points.get(i))>=distance(begP,(points.get(i-1))))
				i++;
			//Point lineV = new Point(points.get(ending).x-begP.x,points.get(ending).y-begP.y);
			//Point projV = new Point(points.get(i-1).x-begP.x,points.get(i-1).y-begP.y);
			//Point endP = new Point((int) (begP.x+lineV.x/length(lineV)*dot(lineV,projV)/length(lineV)),(int) (begP.y+lineV.y/length(lineV)*dot(lineV,projV)/length(lineV)));
			Point endP = points.get(ending);
			
			dirSeq.add(new Point(endP.x-begP.x,endP.y-begP.y));
			beginning = i;
			begP=endP;
			
			/*if(dirSeq.isEmpty() || i>=points.size() || distance(begP,endP)<=length(dirSeq.lastElement())){
				dirSeq.add(new Point(endP.x-begP.x,endP.y-begP.y));
				beginning = i;
				
				begP=endP;
				continue;
			}
			int invBeginning=i-1;
			i=invBeginning;
			while(i>=0 && distance(points.get(invBeginning),points.get(i))<=MAX_DIST)
				i--;
			max = getMaxAngle(points.get(invBeginning),points.get(i));
			min = getMinAngle(points.get(invBeginning),points.get(i));
			length = distance(points.get(invBeginning),points.get(i));
			
			while(i>=0 && checkPointValidity(points.get(i),points.get(invBeginning),min,max,length)) {
				max = getMaxAngle(points.get(invBeginning),points.get(i));
				min = getMinAngle(points.get(invBeginning),points.get(i));
				length = distance(points.get(invBeginning),points.get(i));
				i--;
			}
			Point p = (Point) dirSeq.lastElement().clone();
			Point lastBeginning = new Point(points.get(beginning-1).x-p.x, points.get(beginning-1).y-p.y);
			dirSeq.lastElement().setLocation(points.get(i-1).x-lastBeginning.x,points.get(i-1).y-lastBeginning.y);
			dirSeq.add(new Point(points.get(invBeginning).x-points.get(beginning).x,points.get(invBeginning).y-points.get(beginning).y));
			beginning = invBeginning + 1;
		*/
		}
		if(ending < points.size()-1) {
			dirSeq.add(new Point(points.lastElement().x-points.get(ending).x,points.lastElement().y-points.get(ending).y));
		}
		return dirSeq;
		
		
	} 
	
	public static boolean isCloseToLine(Point point, Point begP, Point endP) {
		return pointToLine(point,begP,endP)<MAX_DIST;
	}
	public static double pointToLine(Point point, Point begP, Point endP) {
		double a = endP.y-begP.y;
		double b = begP.x-endP.x;
		double c = endP.y*begP.x-endP.x*begP.y;
		return Math.abs(a*point.x+b*point.y-c)/Math.sqrt(a*a+b*b);
	}
	public static boolean checkPointValidity(Point point, Point origin,
			double min, double max, double length) {
		double x=point.x-origin.x;
		double y=point.y-origin.y;
		return ((y*Math.cos(max)-x*Math.sin(max))*(y*Math.cos(min)-x*Math.sin(min))) <= 0 && distance(point,origin)>=length;
	}
	public static double getMaxAngle(Point point1, Point point2) {
		double angle = getDirection(point1, point2)+Math.asin(MAX_DIST/distance(point1, point2));
		return angle<2*Math.PI ? angle : angle-2*Math.PI;
	}
	public static double getMinAngle(Point point1, Point point2) {
		double angle = getDirection(point1, point2)-Math.asin(MAX_DIST/distance(point1, point2));
		return angle>0 ? angle : angle+2*Math.PI;
	}
	public static double distance(Point p1, Point p2) {
		return length(new Point(p1.x-p2.x,p1.y-p2.y));
	}
	public static double rateDirSeq(Vector<Point> dirSeq1, Vector<Point> dirSeq2) {
		double result = 0;
		double l1 = length(dirSeq1);
		double l2 = length(dirSeq2);
		double dl1=0;
		for(Point p1 : dirSeq1) {
			double dl2=0;
			for(Point p2 : dirSeq2) {
				if(dot(p1,p2)<0){
					dl2+=length(p2);
					continue;
				}
				double beg = Math.max(dl1/l1,dl2/l2);
				double end = Math.min((dl1+length(p1))/l1, (dl2+length(p2))/l2);
				double comL = end>beg ? end-beg : 0;
				System.out.println(comL);
				result += dot(p1,p2)/length(p1)/length(p2)*comL;
				dl2+=length(p2);
			}
			dl1+=length(p1);
		}
		return result;
	}
	public static double dot(Point p1, Point p2) {
		return p1.x*p2.x+p1.y*p2.y;
	}
	public static double length(Vector<Point> dirSeq) {
		double sum = 0;
		for(Point p : dirSeq)
			sum+=length(p);
		return sum;
	}
	public static double length(Point p) {
		
		return Math.sqrt(p.x*p.x+p.y*p.y);
	}
	public static double getDirection(Vector<Point> vect, int index, int increment) {
		return(getDirection(vect.get(index),vect.get(index+increment)));
	}
}
