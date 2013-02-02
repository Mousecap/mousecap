package com.mousecap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class MousecapData implements Serializable{
	private Map<Integer,Gesture> gestures;
	private int curId;
	private static MousecapData instance;
	
	static {
      try
      {
         FileInputStream fileIn =
              new FileInputStream("gestures.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         instance = (MousecapData) in.readObject();
         in.close();
         fileIn.close();
      }catch(Exception i)
      {
         //i.printStackTrace();
      }
      if(instance == null)
    	  instance = new MousecapData();
	}
	private MousecapData() {
		curId = 0;
		gestures = new TreeMap<Integer,Gesture>();
	}
	
	public Collection<Gesture> getGestures() {
		return gestures.values();
	}
	
	public void add(Gesture gesture) {
		if(gesture.getId() == null) {
			gesture.setId(curId);
			gestures.put(curId, gesture);
			curId++;
		}
		else {
			gestures.put(gesture.getId(), gesture);
		}
	}
	
	public void remove(Gesture gesture) {
		gestures.remove(gesture.getId());
	}
	
	public void set(int id, Gesture gesture) {
		gesture.setId(id);
		gestures.put(id, gesture);
	}
	
	public Gesture find(int id) {
		return gestures.get(id);
	}
	
	public void saveGestures() {
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("gestures.ser");
	         ObjectOutputStream out =
	        		 new ObjectOutputStream(fileOut);
	         out.writeObject(instance);
	         out.close();
	          fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static MousecapData getInstance() {
		return instance;
	}
}
