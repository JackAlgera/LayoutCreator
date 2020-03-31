package com.projetpaparobin.utils;

import java.util.Random;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UIElements {

	public static Random r = new Random();
	public static Border BLACK_BORDER = new Border(new BorderStroke(
			Color.BLACK, 
			BorderStrokeStyle.SOLID, 
			CornerRadii.EMPTY, 
			BorderWidths.DEFAULT));
	
	public static Color RED_TRANSPARENT = new Color(1, 0, 0, 0.5);
	
	public static Color getRandomColor() {
		return new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 0.5);
	}
	
}
