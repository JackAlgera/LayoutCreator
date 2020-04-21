package com.projetpaparobin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.UnaryOperator;

import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIElements {

	public static final long MIN_FABRICATION_YEAR = 1970;
	public static final long MAX_FABRICATION_YEAR = 2050;
	public static final String DEFAULT_BRAND = "DE";
	
	public static final String SAVE_BUTTON_TEXT = "Enregistrer";
	public static final String CANCEL_BUTTON_TEXT = "Annuler";
	public static final String DONE_BUTTON_TEXT = "OK";

	public static String SAVE_FILE_TYPE = "jck";
	
	public static String NUMBER_REGEX = "[0-9]*";
	public static String LETTER_REGEX = "[a-zA-Z]*";
	public static Random r = new Random();
	
	public static Font LAYOUT_FONT = new Font("Arial", 18);
	public static Font EXTINGUISHER_FONT = new Font("Monaco", 10);
	public static Font EXTINGUISHER_TEXT_FONT = new Font("Monaco", 13);
	public static Font COMMENT_TEXT_FONT = new Font("Monaco", 13);
	
	public static Border BLACK_BORDER = new Border(new BorderStroke(
			Color.BLACK, 
			BorderStrokeStyle.SOLID, 
			CornerRadii.EMPTY, 
			BorderWidths.DEFAULT));
		
	public static UIColor getRandomColor() {
		UIColor randColor = DEFAULT_ZONE_COLORS.get(colorIndex);
		colorIndex = (colorIndex + 1) % DEFAULT_ZONE_COLORS.size();
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
	public static ArrayList<UIColor> DEFAULT_ZONE_COLORS = new ArrayList<UIColor>(Arrays.asList(
//			Color.TURQUOISE,
//			Color.BLUE,
//			Color.RED,
//			Color.ORANGE,
//			Color.PURPLE,
//			Color.GREEN,
//			Color.BROWN
			UIColor.GREEN,
			UIColor.BLUE,
			UIColor.RED,
			UIColor.YELLOW,
			UIColor.BROWN,
			UIColor.ORANGE
	));
	
	public static ArrayList<UIColor> DEFAULT_EXTINGUISHER_COLORS = new ArrayList<UIColor>(Arrays.asList(
			UIColor.LIGHTBLUE,
			UIColor.LIGHTGREEN,
			UIColor.LIGHTRED,
			UIColor.LIGHTYELLOW
	));

	public static void setDefaultButtonStyle(Button b) {
		b.setPrefSize(150, 30);
		b.setFont(new Font("Arial", 13));
	}
	
}
