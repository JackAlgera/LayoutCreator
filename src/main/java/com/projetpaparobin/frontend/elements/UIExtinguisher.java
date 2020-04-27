package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class UIExtinguisher extends UIElement {
	
	private static double DEFAULT_EXTINGUISHER_RADIUS = 0.02;
	private static double SELECTED_EXTINGUISHER_RADIUS_INCREASE = 0.003;
	private static double POINT_RADIUS = 0.01;
	
	private Circle circle;		
	private WritableImage drawnImage;
	private Extinguisher ex;
	private UIExtinguisherText uiExText;
	private UICorner resizeCorner;
	
	public UIExtinguisher(Extinguisher ex, ViewLayoutAgent viewLayoutAgent) {
		super(ex.getPos().getX(), ex.getPos().getY(), Color.BLACK, ex.getId().getColor(), viewLayoutAgent);
		this.ex = ex;
		this.uiExText = null;
		if(ex.getRadius() <= 0) {
			ex.setRadius(DEFAULT_EXTINGUISHER_RADIUS);
		}
		prepareImage();
		this.resizeCorner = new UICorner(this, new Point(posX + circle.getRadius(), posY - circle.getRadius()), POINT_RADIUS, Color.BLACK, UIColor.WHITE, viewLayoutAgent);
	}

	@Override
	public void drawShape() {
		canvasGC.drawImage(drawnImage, (posX - circle.getRadius()) * viewLayoutAgent.getCanvasWidth(), posY * viewLayoutAgent.getCanvasHeight() - circle.getRadius() * viewLayoutAgent.getCanvasWidth());	
		if(isSelected) {
			resizeCorner.drawShape();
		}
	}

	private void prepareImage() {
		StackPane sPane = new StackPane();		
		Circle drawnCircle;
		
		if(isSelected) {
			circle = new Circle(posX, posY, ex.getRadius() + SELECTED_EXTINGUISHER_RADIUS_INCREASE, UIElements.EXTINGUISHER_SELECTED_COLOR.getColor());
			drawnCircle = new Circle(posX, posY, (ex.getRadius() + SELECTED_EXTINGUISHER_RADIUS_INCREASE) * viewLayoutAgent.getCanvasWidth(), UIElements.EXTINGUISHER_SELECTED_COLOR.getColor());
			drawnCircle.setStroke(Color.BLACK);
			drawnCircle.setStrokeWidth(1.3);	
		} else {
			circle = new Circle(posX, posY, ex.getRadius(), Color.CYAN);
			drawnCircle = new Circle(posX, posY, ex.getRadius() * viewLayoutAgent.getCanvasWidth(), ex.getId().getColor().getColor());
			drawnCircle.setStroke(rimColor);
			drawnCircle.setStrokeWidth(0.8);	
		}	
		
		Text nbrText = new Text("" + ex.getId().getNumber());
		nbrText.setFont(UIElements.EXTINGUISHER_FONT);
		
		sPane.getChildren().setAll(drawnCircle, nbrText);
		
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
		double deltaX = newPosX - posX;
		double deltaY = newPosY - posY;
		super.translateShape(newPosX, newPosY);
		circle = new Circle(posX, posY, circle.getRadius());
		ex.setPos(new Point(newPosX, newPosY));
		if(uiExText != null) {
			uiExText.translateShape(uiExText.getPosX() + deltaX, uiExText.getPosY() + deltaY);
		}
		resizeCorner.translateShape(resizeCorner.getPosX() + deltaX, resizeCorner.getPosY() + deltaY);
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
		resizeCorner.setIsSelected(isSelected);
		prepareImage();
	}

	public UICorner getResizeCorner() {
		return resizeCorner;
	}
	
	@Override
	public void removeSelf() {
		ex.getZone().removeExtinguisher(ex);
		layoutHandler.removeExtinguisher(ex);
	}
	
	public void setUiExText(UIExtinguisherText uiExText) {
		this.uiExText = uiExText;
	}
	
	public void resize(double newPosY) {
		ex.setRadius(Math.abs(posY - newPosY));
		prepareImage();
		resizeCorner.translateShape(posX + circle.getRadius(), posY - circle.getRadius());
		DEFAULT_EXTINGUISHER_RADIUS = ex.getRadius();
	}
	
}
