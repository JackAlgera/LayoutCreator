package com.projetpaparobin.utils;

import java.util.Random;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UIElements {

	public static String NUMBER_REGEX = "[0-9]*";
	public static String LETTER_REGEX = "[a-zA-Z]*";
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
	
	public static UnaryOperator<Change> getLetterFilter() {
		UnaryOperator<Change> integerFilter = change -> {
			String input = change.getText();
			if(input.matches(LETTER_REGEX)) {
				return change;
			}
			return null;
		};
		
		return integerFilter;		
	}
	
	public static UnaryOperator<Change> getNumberFilter() {
		UnaryOperator<Change> integerFilter = change -> {
			String input = change.getText();
			if(input.matches(NUMBER_REGEX)) {
				return change;
			}
			return null;
		};
		
		return integerFilter;		
	}
	
}
