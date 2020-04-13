package com.projetpaparobin.frontend.agents.layout;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

public interface IViewLayoutAgent {

	public Canvas getCanvas();
	public void cleanCanvas();
	public WritableImage getSnapshot(SnapshotParameters params, WritableImage image);
	
}
