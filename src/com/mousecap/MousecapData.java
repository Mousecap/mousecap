package com.mousecap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class MousecapData {
	private Vector<Gesture> gestures;
	private int size;
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
      }catch(IOException i)
      {
         i.printStackTrace();
      }catch(ClassNotFoundException c)
      {
         c.printStackTrace();
      }
      if(instance == null)
    	  instance = new MousecapData();
	}
	private MousecapData() {}
	
	public Vector<Gesture> getGestures() {
		return gestures;
	}
	
	public void addGesture(Gesture gesture) {
		gestures.set(size, gesture);
		size++;
	}
	
	public void removeGesture(int index) {
		gestures.set(index, null);	
	}
	
	public void changeGesture(int index, Gesture gesture) {
		gestures.set(index, gesture);
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

	public int getSize() {
		return size;
	}
}
