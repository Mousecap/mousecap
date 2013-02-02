package com.mousecap.graphics;

import java.awt.Point;
import java.util.Vector;

import com.trolltech.qt.core.Qt.PenStyle;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QWidget;

public class Lines extends QWidget {
	private Vector<Point> pts = new Vector<Point>();
	
	public Lines(Vector<Point> pts) {
		this.pts = pts;
	}
	
	protected void paintEvent(QPaintEvent e) {
		QPen pen = new QPen();
		pen.setColor(QColor.black);
		pen.setWidth(2);
		pen.setStyle(PenStyle.SolidLine);
		QPainter qp = new QPainter(this);
		if(pts.size()>1) {
			for(int x = 0; x < pts.size()-1; x++) {
				Point tmp1 = pts.get(x);
				Point tmp2 = pts.get(x+1);
				qp.drawLine(tmp1.x, tmp1.y, tmp2.x, tmp2.y);
			}
		}
	}
}
