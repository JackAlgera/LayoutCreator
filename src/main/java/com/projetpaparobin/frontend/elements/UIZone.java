package com.projetpaparobin.frontend.elements;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.shapes.UIPolygon;
import com.projetpaparobin.objects.zones.Point;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIZone extends UIElement {

	private UIPolygon shape;
	
	public UIZone(ArrayList<Point> points, Color rimColor, Color fillColor, Canvas canvas) {
		super(points.get(0).getX(), points.get(0).getY(), rimColor, fillColor, canvas);
		shape = new UIPolygon(points, rimColor, fillColor, canvas);
	}

	@Override
	public void drawShape() {
		shape.drawShape();
	}
	
}
