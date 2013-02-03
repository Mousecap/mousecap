package com.mousecap.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;
import com.mousecap.MousecapKeyListener;

public class SwingWindow implements MouseListener {
	static MousecapData data = MousecapData.getInstance();
	private JFrame frame;
	private JList<Gesture> list;
	private JTextField script;
	private JScrollPane scrollPane;
	
	@SuppressWarnings("unchecked")
	public SwingWindow(String name) {
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
		frame = new JFrame("MouseCap");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		JPanel frameTop = new JPanel();
		JPanel frameBottom = new JPanel();
		list = new JList<Gesture>(new Vector<Gesture>(data.getGestures()));
		DefaultListModel<Gesture> model = new DefaultListModel<Gesture>();
		list.setModel(model);
		for(Gesture g : data.getGestures()) {
			model.addElement(g);
		}
		list.addMouseListener(this);
		//JComboBox box = new JComboBox();
		//box.addActionListener(this);
		JButton addButton = new JButton("New Gesture");
		JButton removeButton = new JButton("Remove Gesture");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if(arg0.getID() == AWTEvent.MOUSE_EVENT_MASK) {
				System.out.println("button click");
				//}
				Gesture gesture = new Gesture("New Gesture");
				data.add(gesture);
				((DefaultListModel)list.getModel()).addElement(gesture);
				list.repaint();
				//list.setVisible(true);
			}
			
		}); {
			
		}
		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() >= 0) {
					((DefaultListModel)list.getModel()).remove(list.getSelectedIndex());
					list.repaint();
					MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
				}
			}
			
		});
		//addButton.addActionListener(l);
		//JLabel label = new JLabel("Hello World");
		JTextField gestureName = new JTextField();
		script = new JTextField();
		JLabel scriptLabel = new JLabel("cmd:");
		JLabel nameLabel = new JLabel("name:");
		gestureName.setPreferredSize(new Dimension(120, 36));
		script.setPreferredSize(new Dimension(250, 36));
		//JTextField label = new JLabel("Hello World");
		//frame.getContentPane().setLayout(layout2);
		frame.setPreferredSize(new Dimension(500,350));
		frameTop.add(addButton);
		frameTop.add(removeButton);
		//frame.getContentPane().add(label);
		frameTop.setLayout(layout);
		frameTop.add(nameLabel);
		frameTop.add(gestureName);
		gestureName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("Enter is hit");
					if(list.getSelectedValue()!= null) {
						list.getSelectedValue().setName(((JTextField) (e.getComponent())).getText());
						System.out.println("set the name");
						frame.repaint();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(400,200));
		frameTop.add(scriptLabel);
		frameTop.add(script);
		frameTop.add(scrollPane);
		frame.add(frameTop);
		
		//frame.add(frameBottom);
		
		script.addKeyListener(new KeyListener () {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println(list.getSelectedValue());
					if(list.getSelectedValue()!= null)
						list.getSelectedValue().setScript(((JTextField) (arg0.getComponent())).getText());
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {

			}
			
		});
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
			MousecapKeyListener.getInstance().setOutput(list.getSelectedValue().getId());
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
