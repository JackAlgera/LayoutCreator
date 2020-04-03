package com.projetpaparobin.frontend.agents.texts;

import java.util.ArrayList;

import com.projetpaparobin.frontend.shapes.UIShape;

public class UITextHandler {

	private static UITextHandler instance;
	private ArrayList<UIShape> shapeTexts;
		
	private UITextHandler() {	
		shapeTexts = new ArrayList<UIShape>();
	}
	
	public static UITextHandler getInstance() {
		if(instance == null) {
			instance = new UITextHandler();
		}
		
		return instance;
	}
	
	public ArrayList<UIShape> getTexts() {
		return shapeTexts;
	}
	
	public void addText(Text text) {
		shapeTexts.add(text);
	}
	
}
