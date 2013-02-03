package com.mousecap.graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;
import com.mousecap.MousecapKeyListener;

public class SwingWindow implements WindowListener{
	static MousecapData data = MousecapData.getInstance();
	private DefaultListModel<Gesture> model;
	private JFrame frame;
	private JList<Gesture> list;
	private JTextField script;
	private JScrollPane scrollPane;
	private GestureGraphPane gp;
	
	@SuppressWarnings("unchecked")
	public SwingWindow(String name)  {
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
		frame = new JFrame("MouseCap");
		frame.addWindowListener(this);
		MousecapKeyListener.getInstance().setNotificator(new Notificator());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		JPanel frameTop = new JPanel();
		model = new DefaultListModel<Gesture>();
		list = new JList<Gesture>();
		list.setModel(model);
		for(Gesture g : data.getGestures()) {
			model.addElement(g);
		}
		list.addListSelectionListener(new ListSelectionListener() {
			//frame.repaint();x
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedValue() != null) {
					MousecapKeyListener.getInstance().setOutput(list.getSelectedValue().getId());
					gp.setGesture(list.getSelectedValue());
					gp.repaint();
				}
			}
		});
		JButton addButton = new JButton("New Gesture");
		JButton removeButton = new JButton("Remove Gesture");
		JButton runButton = new JButton("Test bash");
		addButton.addActionListener(new ActionListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Gesture gesture = new Gesture("New Gesture");
				data.add(gesture);
				((DefaultListModel)list.getModel()).addElement(gesture);
				list.repaint();
				data.saveGestures();
			}
			
		}); {
			
		}
		removeButton.addActionListener(new ActionListener() {

			@SuppressWarnings("rawtypes")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedIndex() >= 0) {
					
					data.remove(list.getSelectedValue());
					((DefaultListModel)list.getModel()).remove(list.getSelectedIndex());
					list.repaint();
					MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
					gp.setGesture(null);
					data.saveGestures();
				}
			}
			
		});
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedValue() != null)
					list.getSelectedValue().executeScript();
				list.repaint();
				data.saveGestures();
			}
		});
		JTextField gestureName = new JTextField();
		script = new JTextField();
		JLabel scriptLabel = new JLabel("cmd:");
		JLabel nameLabel = new JLabel("name:");
		gestureName.setPreferredSize(new Dimension(120, 36));
		script.setPreferredSize(new Dimension(250, 36));
		frame.setPreferredSize(new Dimension(500,650));
		frameTop.add(addButton);
		frameTop.add(removeButton);
		frameTop.setLayout(layout);
		frameTop.add(nameLabel);
		frameTop.add(gestureName);
		gestureName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(list.getSelectedValue() != null) {
						list.getSelectedValue().setName(((JTextField) (e.getComponent())).getText());
						frame.repaint();
						data.saveGestures();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(400,200));
		frameTop.add(runButton);
		frameTop.add(scriptLabel);
		frameTop.add(script);
		frameTop.add(scrollPane);
		gp = new GestureGraphPane();
		gp.setPreferredSize(new Dimension(400,300));
		frameTop.add(gp);
		frame.add(frameTop);
		script.addKeyListener(new KeyListener () {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if(list.getSelectedValue() != null)
						list.getSelectedValue().setScript(((JTextField) (arg0.getComponent())).getText());
					frame.repaint();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {

			}
			
		});
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new SwingWindow("MouseCap");
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err
					.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
		}
		
		GlobalScreen.getInstance().addNativeKeyListener(MousecapKeyListener.getInstance());
		
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.ADD_NEW_GESTURES);
		
	}
	public class Notificator {
		public void update() {
			model = new DefaultListModel<Gesture>();
			list.setModel(model);
			for(Gesture g : data.getGestures()) {
				model.addElement(g);
			}
			frame.repaint();
			data.saveGestures();
		}
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		MousecapKeyListener.getInstance().setOutput(MousecapKeyListener.EXECUTE_GESTURES);
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
