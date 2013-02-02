package com.mousecap;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Vector;


public class Gesture implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector<Point> points = new Vector<Point>();
	private String script = new String();
	private String name = new String();
	private Integer id;
	
	public Gesture() {
		
	}
	public Gesture(String cmd) {
		this.script = cmd;
	}
	public Vector<Point> getPoints() {
		return points;
	}

	public void setPoints(Vector<Point> points) {
		this.points = points;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	public void executeScript() {System.out.println("Running the script for "+id);
		if(!script.equals("")) {
			try {
				Process p = Runtime.getRuntime().exec(script);
				BufferedReader cin  = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader cerr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String input = null;
				while((input = cin.readLine())  != null) {
					System.out.println(input);
				}
				while((input = cerr.readLine()) != null) {
					System.err.println(input);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}