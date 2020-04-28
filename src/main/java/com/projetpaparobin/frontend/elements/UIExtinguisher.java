package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;

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
		canvasGC.drawImage(drawnImage, posX * viewLayoutAgent.getCanvasWidth() - circle.getRadius() * viewLayoutAgent.getCanvasHeight(), (posY - circle.getRadius()) * viewLayoutAgent.getCanvasHeight());	
		if(isSelected) {
			resizeCorner.drawShape();
		}
	}

	private void prepareImage() {
		int scale = 5;
		
		StackPane sPane = new StackPane();		
		Text nbrText = new Text("" + ex.getId().getNumber());
		nbrText.setFont(UIElements.EXTINGUISHER_FONT);
		nbrText.setFill(UIColor.BLACK.getColor());	
		
		sPane.getChildren().addAll(nbrText);		
		Bounds bounds = sPane.getBoundsInLocal();

		Circle drawnCircle;
		drawnCircle = new Circle(bounds.getWidth());
		drawnCircle.setStroke(Color.BLACK);
		drawnCircle.setStrokeWidth(1.0);	
	
		if(isSelected) {
			drawnCircle.setFill(UIElements.EXTINGUISHER_SELECTED_COLOR.getColor());
		} else {
			drawnCircle.setFill(ex.getId().getColor().getColor());
		}
				
		sPane.getChildren().setAll(drawnCircle, nbrText);

		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);		
				
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(viewLayoutAgent.getCanvasHeight() * ex.getRadius() * 2);
		view.setFitHeight(viewLayoutAgent.getCanvasHeight() * ex.getRadius() * 2);
		params.setTransform(Transform.scale(1, 1));
		drawnImage = view.snapshot(params, null);
		circle = new Circle(posX, posY, ex.getRadius());			
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		System.out.println( viewLayoutAgent.getCanvasRatio());
		
		if(		posY <= (this.posY + circle.getRadius()) && 
				posY >= (this.posY - circle.getRadius()) &&
				posX <= (this.posX + circle.getRadius() * viewLayoutAgent.getCanvasRatio()) && 
				posX >= (this.posX - circle.getRadius() * viewLayoutAgent.getCanvasRatio())) {
			return true;
		}
		return false;
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
