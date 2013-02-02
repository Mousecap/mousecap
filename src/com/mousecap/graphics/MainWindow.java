package com.mousecap.graphics;
import java.awt.Point;
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
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

public class MainWindow extends QMainWindow {
	
	private QMenu fileMenu;
	private QMenu bindsMenu;
	private QAction quitAct;
	private QAction newAct;
	private Vector<Gesture> gestures = new Vector<Gesture>();
	private Vector<QAction> bindings = new Vector<QAction>();
	private QSignalMapper mapper = new QSignalMapper();
	private QWidget mainWidget;
	private QTextEdit txt = new QTextEdit();
	private QTextEdit script = new QTextEdit();
	private QHBoxLayout layout = new QHBoxLayout(mainWidget);
	private Lines lineGraphic;
	private int currentContext = -1;
	
	
	public MainWindow() {
		mainWidget = new QWidget();
		/*
		Vector<Point> tmppts = new Vector<Point>();
		tmppts.add(new Point(-10,-10));
		tmppts.add(new Point(500,500));
		Lines l = new Lines(tmppts);
		*/
		initContext(-1);
		createActions();
		createMenus();
		mapBindings();
		mapper.mappedInteger.connect(this, "editBind(int)");
	}
	public void initContext(int index) {
		if(index < 0) {
			resize(500, 300);
			txt.setFixedSize(250, 50);
			script.setFixedSize(250, 50);
			layout.addWidget(txt);
			layout.addWidget(script);
			drawGraphic(null);
			mainWidget.setLayout(layout);
			this.setCentralWidget(mainWidget);
		}
		else {
			layout.removeWidget(txt);
			layout.removeWidget(lineGraphic);
			layout.removeWidget(script);
			txt = new QTextEdit();
			txt.setFixedSize(250, 50);
			script = new QTextEdit();
			script.setFixedSize(250, 50);
			layout.addWidget(txt);
			layout.addWidget(script);
			//txt.textChanged.connect(this, "textUpdate()");
			if(gestures.size() > index && index >= 0 && gestures.get(index) != null) {
				System.out.println(gestures.get(index));
				txt.setText(gestures.get(index).getName());
				script.setText(gestures.get(index).getScript());
				drawGraphic(gestures.get(index).getPoints());
			}
			else {
				drawGraphic(null);
			}
			txt.textChanged.connect(this, "textUpdate()");
			script.textChanged.connect(this, "scriptUpdate()");
			mainWidget.setLayout(layout);
			this.setCentralWidget(mainWidget);
		}
		currentContext = index;
	}
	public void textUpdate() {
		if(currentContext >= 0 && currentContext < gestures.size()) {
			gestures.get(currentContext).setName(txt.toPlainText());
		}
		
	}
	public void scriptUpdate() {
		if(currentContext >= 0 && currentContext < gestures.size()) {
			gestures.get(currentContext).setScript(txt.toPlainText());
		}
		
	}
	public void drawGraphic(Vector<Point> pts) {
		lineGraphic = new Lines(pts);
		lineGraphic.setFixedSize(200,200);
		layout.addWidget(lineGraphic);
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
		gestures.add(new Gesture());
	}
	private void newBinding(Gesture gesture) {
		newBinding();
		gestures.set(gestures.size()-1, gesture);//gestures.add(gesture);
	}
	@SuppressWarnings("unused")
	private void editBind(int index) {
		System.out.println(index);
		initContext(index);
		
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
		//QHBoxLayout layout = new QHBoxLayout();
		//m.setLayout(layout);
		m.show();
		//m.setFixedSize(325,50);
		app.exec();
	}
	
}
