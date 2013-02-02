package com.mousecap.graphics;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;

public class SwingWindow implements MouseListener {
	static MousecapData data = MousecapData.getInstance();
	private JFrame frame;
	private JList<Gesture> list;
	
	@SuppressWarnings("unchecked")
	public SwingWindow(String name) {
		frame = new JFrame("MouseCap");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add the ubiquitous "Hello World" label.
		//data.getGestures()
		GridLayout layout = new GridLayout(0,2);
		data.add(new Gesture("test1"));
		data.add(new Gesture("data2"));
		data.add(new Gesture("data3"));
		data.add(new Gesture("data4"));
		data.add(new Gesture("data5"));
		data.add(new Gesture("data6"));
		
		list = new JList<Gesture>(new Vector<Gesture>(data.getGestures()));
		/*list.setCellRenderer(new ListCellRenderer() {
			public Component getListCellRendererComponent(JList arg0,
					Object o, int index, boolean arg3, boolean arg4) {
					
				
				JLabel jl = new JLabel(((Gesture)o).getName());
				return jl;
			}
			
		});*/
		list.addMouseListener(this);
		//JComboBox box = new JComboBox();
		//box.addActionListener(this);
		JButton addButton = new JButton("New Gesture");
		//addButton.addActionListener(l);
		//JLabel label = new JLabel("Hello World");
		JTextField gestureName = new JTextField();
		JTextField script = new JTextField();
		//JTextField label = new JLabel("Hello World");
		frame.getContentPane().setLayout(layout);
		frame.getContentPane().add(addButton);
		//frame.getContentPane().add(label);
		frame.getContentPane().add(gestureName);
		frame.getContentPane().add(new JScrollPane(list));
		frame.getContentPane().add(script);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		SwingWindow sw = new SwingWindow("MouseCap");
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("mouse 1");
			frame.repaint();
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
