package com.mousecap.graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import com.mousecap.Gesture;
import com.trolltech.qt.core.QSignalMapper;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTextEdit;

public class MainWindow extends QMainWindow {
	
	private QMenu fileMenu;
	private QMenu bindsMenu;
	private QAction quitAct;
	private QAction newAct;
	private Vector<Gesture> gestures = new Vector<Gesture>();
	private Vector<QAction> bindings = new Vector<QAction>();
	private QSignalMapper mapper = new QSignalMapper();
	private QTextEdit txt;
	
	public MainWindow() {
		/*txt = new QTextEdit();
		txt.setFixedSize(200, 40);
		QTextEdit txt2 = new QTextEdit();
		QHBoxLayout layout = new QHBoxLayout();
		layout.addWidget(txt2);
		layout.addWidget(txt);
		txt.show();
		txt2.show();
		setLayout(layout);
		resize(350, 300);
		*/
		this.show();
		createActions();
		createMenus();
		mapBindings();
		mapper.mappedInteger.connect(this, "editBind(int)");
	}
	@SuppressWarnings("unchecked")
	private void mapBindings() {
		try {
			FileInputStream fis = new FileInputStream("gestures.ser");
			ObjectInputStream in = new ObjectInputStream(fis);
			Vector<Gesture> loadedGestures = (Vector<Gesture>)in.readObject();
			in.close();
			fis.close();
			for(Gesture i : loadedGestures) {
				newBinding(i);
			}
		} catch(IOException e) {
			System.out.println("bindings not found.");
			gestures = new Vector<Gesture>();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void createActions() {
		quitAct = new QAction(tr("E&xit"), this);
		quitAct.setShortcuts(QKeySequence.StandardKey.Close);
		quitAct.setStatusTip("Quits the program.");
		quitAct.triggered.connect(this, "exit()");
		
		newAct = new QAction(tr("&New Binding"), this);
		newAct.setShortcuts(QKeySequence.StandardKey.New);
		newAct.setStatusTip("Define a new binding.");
		newAct.triggered.connect(this, "newBinding()");
	}
	private void newBinding() {
		
		bindings.add(new QAction("Bind &" + bindings.size(), this));
		int index = bindings.size()-1;
		System.out.println("Created bind"+index);
		bindings.get(index).setStatusTip("Edit Bind" + (bindings.size()-1));
		bindings.get(index).triggered.connect(mapper, "map()");//.connect(this, "editBind()");
		mapper.setMapping(bindings.get(index), index);
		bindsMenu.addAction(bindings.get(bindings.size() - 1));
		gestures.add(null);
	}
	private void newBinding(Gesture gesture) {
		newBinding();
		gestures.set(gestures.size()-1, gesture);//gestures.add(gesture);
	}
	@SuppressWarnings("unused")
	private void editBind(int bind) {
		System.out.println(bind);
		
		//gestures.set(bind, new Gesture("echo \"hello, world!\""));
		//gestures.get(bind).executeScript();
		
	}
	@SuppressWarnings({ "static-access", "unused" })
	private void exit() {
		System.out.println("exiting normally.");
		FileOutputStream fs;
		try {
			fs = new FileOutputStream("gestures.ser");
			ObjectOutputStream out = new ObjectOutputStream(fs);
			out.writeObject(gestures);
			out.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		QApplication.instance().exit(0);
	}
	private void createMenus() {
		fileMenu = menuBar().addMenu(tr("&File"));
		fileMenu.addAction(newAct);
		fileMenu.addSeparator();
		fileMenu.addAction(quitAct);
		
		bindsMenu = menuBar().addMenu(tr("&Binds"));
	}
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		QApplication app = new QApplication("Mouse Cap", args);
		
		MainWindow m = new MainWindow();
		//m.setFixedSize(325,50);
		app.exec();
	}
	
}
