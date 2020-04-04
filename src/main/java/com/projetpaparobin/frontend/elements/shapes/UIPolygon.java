package com.projetpaparobin.frontend.elements.shapes;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIElement;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIPolygon extends UIElement {

	double[] pointsX, pointsY;
	
	public UIPolygon(ArrayList<Point> points, Color rimColor, Color fillColor, Canvas canvas) {
		super(points.get(0).getX(), points.get(0).getY(), rimColor, fillColor, canvas);
		pointsX = new double[points.size()];
		pointsY = new double[points.size()];
		for (int i = 0; i < points.size(); i++) {
			pointsX[i] = points.get(i).getX();
			pointsY[i] = points.get(i).getY();
		}
	}

	@Override
	public void drawShape() {				
		canvasGC.setStroke(rimColor);
		canvasGC.setLineWidth(4.0);
		canvasGC.strokePolygon(pointsX, pointsY, pointsX.length);
		
		canvasGC.setFill(fillColor);
		canvasGC.fillPolygon(pointsX, pointsY, pointsX.length);
		
		for (int i = 0; i < pointsX.length; i++) {
			UICircle c = new UICircle(pointsX[i], pointsY[i], 3.0, Color.BLACK, Color.WHITE, canvasGC.getCanvas());
			c.drawShape();
		}
	}
	
}
