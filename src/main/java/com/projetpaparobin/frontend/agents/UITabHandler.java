package com.projetpaparobin.frontend.agents;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.utils.UIElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class UITabHandler extends HBox implements ITabHandler, EventHandler<ActionEvent> {
	
	private static TabHandler tabHandler = TabHandler.getInstance();
	
	private ArrayList<Button> tabs;
	
	public UITabHandler() {
		super(8);
		this.setBorder(UIElements.BLACK_BORDER);
		tabs = new ArrayList<Button>();
		
		tabHandler.addListener(this);
	}
	
	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
		case REMOVED_TAB:
			this.getChildren().clear();
			tabs.clear();
			for (LayoutHandler layoutHandler : tabHandler.getLayoutHandlers()) {
				Button b = new Button(layoutHandler.getLayoutName());
				b.addEventHandler(ActionEvent.ACTION, this);
				tabs.add(b);
			}
			this.getChildren().addAll(tabs);
			break;
		case CHANGED_SELECTED_TAB:
			break;
		case FULL_RESET:
			break;
		}
	}

	@Override
	public void handle(ActionEvent event) {
		int layoutIndex = tabs.indexOf(event.getSource());
		if(layoutIndex >= 0) {
			tabHandler.selectLayoutHandler(layoutIndex);
		}
	}
	
}
