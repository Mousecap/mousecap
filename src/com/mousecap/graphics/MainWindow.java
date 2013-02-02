package com.mousecap.graphics;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Vector;

import com.mousecap.Gesture;
import com.mousecap.MousecapData;
import com.trolltech.qt.core.QSignalMapper;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QListView;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

public class MainWindow extends QMainWindow {
	
	private QMenu fileMenu;
	private QMenu bindsMenu;
	private QAction quitAct;
	private QAction newAct;
	//private Vector<QAction> bindings = new Vector<QAction>();
	private QListWidget list = new QListWidget();
	private QPushButton newGesture = new QPushButton("New Gesture");
	private QSignalMapper mapper = new QSignalMapper();
	private QWidget mainWidget;
	private QTextEdit txt = new QTextEdit();
	private QTextEdit script = new QTextEdit();
	private QGridLayout layout = new QGridLayout(mainWidget);
	private Lines lineGraphic;
	private int currentContext = -1;
	
	static MousecapData data = MousecapData.getInstance();
	
	public MainWindow() {
		mainWidget = new QWidget();
		/*
		Vector<Point> tmppts = new Vector<Point>();
		tmppts.add(new Point(-10,-10));
		tmppts.add(new Point(500,500));
		Lines l = new Lines(tmppts);
		*/
		layout.addWidget(newGesture);
		list.setFixedSize(250, 250);
		layout.addWidget(list);
		initContext(-1);
		createActions();
		createMenus();
		mapBindings();
		mapper.mappedInteger.connect(this, "editBind(int)");
	}
	public void initContext(int index) {
		if(index < 0) {
			resize(500, 300);
			txt.setFixedSize(250, 36);
			script.setFixedSize(250, 36);
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
			if(data.getGestures().size() > index 
				&& index >= 0 && data.find(index) != null) {
				
				System.out.println(data.find(index));
				txt.setText(data.find(index).getName());
				script.setText(data.find(index).getScript());
				drawGraphic(data.find(index).getPoints());
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
		if(currentContext >= 0 && currentContext < data.getGestures().size()) {
			MousecapData.getInstance().find(currentContext).setName(txt.toPlainText());
		}
		
	}
	public void scriptUpdate() {
		if(currentContext >= 0 && currentContext < data.getGestures().size()) {
			data.find(currentContext).setScript(txt.toPlainText());
		}
		
	}
	public void drawGraphic(Vector<Point> pts) {
		lineGraphic = new Lines(pts);
		lineGraphic.setFixedSize(200,200);
		layout.addWidget(lineGraphic);
	}
	@SuppressWarnings("unchecked")
	private void mapBindings() {

		for(Gesture i : data.getGestures()) {
			newBinding(i);
		}
	}
	private void createActions() {
		quitAct = new QAction(tr("E&xit"), this);
		quitAct.setShortcuts(QKeySequence.StandardKey.Close);
		quitAct.setStatusTip("Quits the program.");
		quitAct.triggered.connect(this, "exit()");
		
		/*newAct = new QAction(tr("&New Binding"), this);
		newAct.setShortcuts(QKeySequence.StandardKey.New);
		newAct.setStatusTip("Define a new binding.");
		newAct.triggered.connect(this, "newBinding()");*/
	}
	private void newBinding(Gesture gesture) {
		list.addItem(new QListWidgetItem(gesture.getName()));
		//list.clicked.connect(receiver, method);
		
	}
	/*
	private void newBinding(Gesture gesture) {
		if(gesture != null)
			bindings.add(new QAction("Bind &" + data.find(bindings.size()).getName(), this));
		else
			bindings.add(new QAction("Bind &" + bindings.size() , this));
		int index = bindings.size()-1;
		System.out.println("Created bind"+index);
		bindings.get(index).setStatusTip("Edit Bind" + (bindings.size()-1));
		bindings.get(index).triggered.connect(mapper, "map()");//.connect(this, "editBind()");
		mapper.setMapping(bindings.get(index), index);
		bindsMenu.addAction(bindings.get(bindings.size() - 1));
		if(gesture == null)
			data.add(new Gesture());
	}*/
	@SuppressWarnings("unused")
	private void editBind(int index) {
		System.out.println(index);
		initContext(index);
		
		//gestures.set(bind, new Gesture("echo \"hello, world!\""));
		//gestures.get(bind).executeScript();
		
	}
	@SuppressWarnings({ "static-access", "unused" })
	private void exit() {
		/*System.out.println("exiting normally.");
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
		*/
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
		m.show();
		app.exec();
	}
	
}
