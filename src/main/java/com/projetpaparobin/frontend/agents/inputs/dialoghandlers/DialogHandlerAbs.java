package com.projetpaparobin.frontend.agents.inputs.dialoghandlers;

import javafx.stage.Window;

public abstract class DialogHandlerAbs {

	protected Window primaryStage;
	
	public DialogHandlerAbs(Window primaryStage) {
		this.primaryStage = primaryStage;
	}
	
}
