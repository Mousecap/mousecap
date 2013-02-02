import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.jnativehook.GlobalScreen;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;
import com.mousecap.MousecapKeyListener;


public class Test {
	
	public static void main(String[] args) {
		/*try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
					.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
		}*/
		
		GlobalScreen.getInstance().addNativeKeyListener(MousecapKeyListener.getInstance());
		
		final JFrame frame = new JFrame();
		frame.setSize(new Dimension(800,550));
		final JComboBox<Gesture> box1 = new JComboBox<Gesture>(new Vector<Gesture>(MousecapData.getInstance().getGestures()));
		
		box1.setRenderer(new ListCellRenderer<Gesture>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends Gesture> list, Gesture value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// TODO Auto-generated method stub
				return new JLabel(value==null? "" : value.getId().toString());
			}
			
		});
		box1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
				
			}
			
		});
		JPanel pic1 = new JPanel(){
			public void paint(Graphics g){
				g.setColor(Color.black);
				Gesture gesture = (Gesture) box1.getSelectedItem();
				
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
				double vscale = 320.0/(maxY-minY);
				double hscale = 320.0/(maxX-minX);
				Point prev = points.firstElement();
				for(Point p : points) {
					g.drawLine(40+(int)((prev.x-minX)*hscale), 40+(int)((prev.y-minY)*vscale), 40+(int)((p.x-minX)*hscale), 40+(int)((p.y-minY)*vscale));
					prev=p;
				}
				
			}
		};
		
		pic1.setSize(new Dimension(400,400));
		
		box1.setSize(new Dimension(50,20));
		frame.add(box1);
		frame.add(pic1);
		
		
final JComboBox<Gesture> box2 = new JComboBox<Gesture>(new Vector<Gesture>(MousecapData.getInstance().getGestures()));
		
		box2.setRenderer(new ListCellRenderer<Gesture>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends Gesture> list, Gesture value, int index,
					boolean isSelected, boolean cellHasFocus) {
				// TODO Auto-generated method stub
				return new JLabel(value==null? "" : value.getId().toString());
			}
			
		});
		box2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
				
			}
			
		});
		JPanel pic2 = new JPanel(){
			public void paint(Graphics g){
				g.setColor(Color.black);
				Gesture gesture = (Gesture) box2.getSelectedItem();
				
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
				double vscale = 320.0/(maxY-minY);
				double hscale = 320.0/(maxX-minX);
				Point prev = points.firstElement();
				for(Point p : points) {
					g.drawLine(40+(int)((prev.x-minX)*hscale), 40+(int)((prev.y-minY)*vscale), 40+(int)((p.x-minX)*hscale), 40+(int)((p.y-minY)*vscale));
					prev=p;
				}
				
			}
		};
		
		pic2.setSize(new Dimension(400,400));
		
		box2.setSize(new Dimension(20,10));
		frame.add(box2);
		frame.add(pic2);
		
		
		frame.setVisible(true);
	}

}
