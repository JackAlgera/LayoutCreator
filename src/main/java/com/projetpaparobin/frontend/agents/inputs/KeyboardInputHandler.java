package com.projetpaparobin.frontend.agents.inputs;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardInputHandler implements EventHandler<KeyEvent> {

	private static KeyboardInputHandler instance;
	
	private static MouseInputHandler mouseInputHandler = MouseInputHandler.getInstance();
	
	private KeyboardInputHandler() {
	}
	
	public static KeyboardInputHandler getInstance() {
		if(instance == null) {
			instance = new KeyboardInputHandler();
		}
		
		return instance;
	}
	
	@Override
	public void handle(KeyEvent event) {
		switch (event.getCode()) {
			case DELETE:
				mouseInputHandler.deleteSelectedElement();
				break;
			default:
				break;
		}
	}

}
