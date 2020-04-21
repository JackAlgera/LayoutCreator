package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UIExtinguisher extends UIElement {
	
	private Circle circle;		
	private WritableImage drawnImage;
	private Extinguisher ex;
	
	public UIExtinguisher(Extinguisher ex, Canvas canvas) {
		super(ex.getPos().getX(), ex.getPos().getY(), Color.BLACK, ex.getId().getColor(), canvas);
		this.ex = ex;
		prepareImage();
	}

	@Override
	public void drawShape() {
		canvasGC.drawImage(drawnImage, posX - circle.getRadius(), posY - circle.getRadius());	
	}

	private void prepareImage() {
		StackPane sPane = new StackPane();
		
		circle = new Circle(posX, posY, UIElements.EXTINGUISHER_FONT.getSize(), ex.getId().getColor().getColor());
		circle.setStroke(rimColor);
		circle.setStrokeWidth(0.8);		
		
		Text nbrText = new Text("" + ex.getId().getNumber());
		nbrText.setFont(UIElements.EXTINGUISHER_FONT);
		
		sPane.getChildren().addAll(circle, nbrText);
		
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return circle.contains(posX, posY);
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		circle = new Circle(posX, posY, circle.getRadius());
		ex.setPos(new Point(newPosX, newPosY));
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void removeSelf() {
		// TODO Auto-generated method stub
		
	}
	
}
