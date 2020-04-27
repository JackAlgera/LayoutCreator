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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;

public class UIExtinguisherText extends UIElement {

	private static double DEFAULT_TEXT_HEIGHT = 0.025;
	private static double POINT_RADIUS = 0.01;
	private static double Y_OFFSET = - 0.03;
	
	private Rectangle hitbox;
	private Extinguisher ex;
	private double textHeight;
	private UICorner resizeCorner;

	private WritableImage drawnImage;
		
	public UIExtinguisherText(Extinguisher ex, ViewLayoutAgent viewLayoutAgent) {
		super(	(ex.getTextAreaPos() == null) ? ex.getPos().getX() : ex.getTextAreaPos().getX(),
				(ex.getTextAreaPos() == null) ? ex.getPos().getY() + Y_OFFSET : ex.getTextAreaPos().getY(),
				Color.BLACK, null, viewLayoutAgent);
		this.ex = ex;
		if(ex.getTextAreaSize() == 0) {
			ex.setTextAreaSize(DEFAULT_TEXT_HEIGHT);
		}
		this.textHeight = ex.getTextAreaSize();
		prepareImage();
		this.resizeCorner = new UICorner(this, new Point(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2), POINT_RADIUS, Color.BLACK, UIColor.WHITE, viewLayoutAgent);
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX * viewLayoutAgent.getCanvasWidth() - (drawnImage.getWidth() / 2.0), posY * viewLayoutAgent.getCanvasHeight() - (drawnImage.getHeight() / 2.0));	
		if(isSelected) {
			resizeCorner.drawShape();
		}
	}
	
	public void update() {
		prepareImage();
	}
	
	private void prepareImage() {
		int scale = 5;
	
		StackPane sPane = new StackPane();		
		Text txt = new Text(ex.getDisplayText());
		txt.setFont(UIElements.EXTINGUISHER_TEXT_FONT);
			
		sPane.getChildren().addAll(txt);		
		Bounds bounds = sPane.getBoundsInLocal();
				
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(viewLayoutAgent.getCanvasHeight() * textHeight * (bounds.getWidth() / bounds.getHeight()));
		view.setFitHeight(viewLayoutAgent.getCanvasHeight() * textHeight);
		params.setTransform(Transform.scale(1, 1));
		drawnImage = view.snapshot(params, null);
		
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Extinguisher getExtinguisher() {
		return ex;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		double deltaX = newPosX - posX;
		double deltaY = newPosY - posY;
		super.translateShape(newPosX, newPosY);
		ex.setTextAreaPos(new Point(newPosX, newPosY));
		resizeCorner.translateShape(resizeCorner.getPosX() + deltaX, resizeCorner.getPosY() + deltaY);
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
		resizeCorner.setIsSelected(isSelected);
	}

	@Override
	public void removeSelf() {
		// TODO Auto-generated method stub
		
	}
	
	public UICorner getResizeCorner() {
		return resizeCorner;
	}
	
	public void resize(double newPosY) {
		textHeight = Math.abs((posY - newPosY) * 2);
		update();
		resizeCorner.translateShape(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2);
		ex.setTextAreaSize(textHeight);
		DEFAULT_TEXT_HEIGHT = textHeight;
	}
	
}
