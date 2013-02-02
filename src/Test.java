import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;
import com.mousecap.algorithm.Utilities;
import com.mousecap.graphics.GestureGraphPane;


public class Test {
	private static final JFrame frame = new JFrame();
	public static void main(String[] args) {
		frame.setLayout(new GridLayout(3,2));
		TestComboBox box = new TestComboBox(new Vector<Gesture>(MousecapData.getInstance().getGestures()));
		final GestureGraphPane pane = new GestureGraphPane();
		box.setPane(pane);
		pane.setGesture((Gesture) box.getSelectedItem());
		
		pane.setSize(new Dimension(400,400));
		pane.setBackground(Color.blue);
		
		
		TestComboBox box2 = new TestComboBox(new Vector<Gesture>(MousecapData.getInstance().getGestures()));
		final GestureGraphPane pane2 = new GestureGraphPane();
		box2.setPane(pane2);
		pane2.setGesture((Gesture) box2.getSelectedItem());
			
		pane2.setSize(new Dimension(400,400));
		pane2.setBackground(Color.blue);
		frame.add(box);
		frame.add(box2);
		frame.add(pane);
		frame.add(pane2);
		frame.add(new JLabel(){
			public void paint(Graphics g) {
				super.paint(g);
				setText(""+Utilities.compareGestures(pane.getGesture().getPoints(), pane2.getGesture().getPoints()));
			}
		});
		
		
		
		frame.setVisible(true);
	}
	
	static class TestComboBox extends JComboBox<Gesture> {
		private GestureGraphPane pane;
		TestComboBox() {
			super();
			setRenderer(new ListCellRenderer<Gesture>() {
				@Override
				public Component getListCellRendererComponent(
						JList<? extends Gesture> list, Gesture value, int index,
						boolean isSelected, boolean cellHasFocus) {
					// TODO Auto-generated method stub
					return new JLabel(value==null? "" : value.getId().toString());
				}
			});
			addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.repaint();
					
				}
			});
			
		}
		
		TestComboBox(Vector<Gesture> vect) {
			super(vect);
			setRenderer(new ListCellRenderer<Gesture>() {
				@Override
				public Component getListCellRendererComponent(
						JList<? extends Gesture> list, Gesture value, int index,
						boolean isSelected, boolean cellHasFocus) {
					// TODO Auto-generated method stub
					return new JLabel(value==null? "" : value.getId().toString());
				}
			});
			addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					pane.setGesture((Gesture) ((TestComboBox)e.getSource()).getSelectedItem());
					frame.repaint();
					
				}
			});
			
		}

		public GestureGraphPane getPane() {
			return pane;
		}

		public void setPane(GestureGraphPane pane) {
			this.pane = pane;
		}
	}

}
