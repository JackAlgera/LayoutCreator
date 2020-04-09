package com.projetpaparobin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIElements {

	public static String NUMBER_REGEX = "[0-9]*";
	public static String LETTER_REGEX = "[a-zA-Z]*";
	public static Random r = new Random();
	
	public static Font LAYOUT_FONT = new Font("Arial", 18);
	public static Font EXTINGUISHER_FONT = new Font("Monaco", 10);
	public static Font EXTINGUISHER_TEXT_FONT = new Font("Monaco", 13);
	
	public static Border BLACK_BORDER = new Border(new BorderStroke(
			Color.BLACK, 
			BorderStrokeStyle.SOLID, 
			CornerRadii.EMPTY, 
			BorderWidths.DEFAULT));
		
	public static Color getRandomColor() {
		Color randColor = DEFAULT_COLORS.get(colorIndex);
		randColor = new Color(randColor.getRed(), randColor.getGreen(), randColor.getBlue(), 0.5);
		colorIndex = (colorIndex + 1) % DEFAULT_COLORS.size();
		return randColor;
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
	
	public static int colorIndex = 0;	
	private static ArrayList<Color> DEFAULT_COLORS = new ArrayList<Color>(Arrays.asList(
			Color.TURQUOISE,
			Color.BLUE,
			Color.RED,
			Color.ORANGE,
			Color.PURPLE,
			Color.GREEN,
			Color.BROWN
	));
	
}
