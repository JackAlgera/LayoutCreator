package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Point;
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

	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private static double DEFAULT_TEXT_HEIGHT = 0.025;
	private static double Y_OFFSET = - 0.03;
	private static double MIN_TEXT_SIZE = Double.parseDouble(dao.getKeyValue(EPreferencesValues.MIN_TEXT_SIZE));
	
	private Rectangle hitbox;
	private Extinguisher ex;

	private WritableImage drawnImage;
		
	public UIExtinguisherText(Extinguisher ex, ViewLayoutAgent viewLayoutAgent) {
		super(	(ex.getTextAreaPos() == null) ? ex.getPos().getX() : ex.getTextAreaPos().getX(),
				(ex.getTextAreaPos() == null) ? ex.getPos().getY() + Y_OFFSET : ex.getTextAreaPos().getY(),
				true, Color.BLACK, null, viewLayoutAgent);
		updateDefaultTextSize();
		this.ex = ex;
		if(ex.getTextAreaSize() <= 0) {
			ex.setTextAreaSize(DEFAULT_TEXT_HEIGHT);
		}
		prepareImage();
		initResizeCorner(new Point(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2));
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX * viewLayoutAgent.getCanvasWidth() - (drawnImage.getWidth() / 2.0), posY * viewLayoutAgent.getCanvasHeight() - (drawnImage.getHeight() / 2.0));	
		super.drawShape();
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
		view.setFitWidth(viewLayoutAgent.getCanvasHeight() * ex.getTextAreaSize() * (bounds.getWidth() / bounds.getHeight()));
		view.setFitHeight(viewLayoutAgent.getCanvasHeight() * ex.getTextAreaSize());
		params.setTransform(Transform.scale(1, 1));
		drawnImage = view.snapshot(params, null);
		
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	private void updateDefaultTextSize() {
		double temp = Double.parseDouble(dao.getKeyValue(EPreferencesValues.MIN_TEXT_SIZE));
		if(DEFAULT_TEXT_HEIGHT < temp) {
			DEFAULT_TEXT_HEIGHT = temp;
		}
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
		super.translateShape(newPosX, newPosY);
		ex.setTextAreaPos(new Point(newPosX, newPosY));
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public void removeSelf() {
	}
	
	public void resize(double newPosY) {
		MIN_TEXT_SIZE = Double.parseDouble(dao.getKeyValue(EPreferencesValues.MIN_TEXT_SIZE));
		double textHeight = Math.abs((posY - newPosY) * 2);
		if (textHeight < MIN_TEXT_SIZE) {
			textHeight = MIN_TEXT_SIZE;
		}
		ex.setTextAreaSize(textHeight);
		update();
		super.translateResizeCorner(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2);
		DEFAULT_TEXT_HEIGHT = ex.getTextAreaSize();
	}
	
}
