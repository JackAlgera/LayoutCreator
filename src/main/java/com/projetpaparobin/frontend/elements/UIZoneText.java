package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.preferences.EPreferencesValues;
import com.projetpaparobin.documents.preferences.dao.DAOPreferencesImpl;
import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.objects.zones.Zone;
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

public class UIZoneText extends UIElement {

	private static DAOPreferencesImpl dao = DAOPreferencesImpl.getInstance();
	
	private static double DEFAULT_TEXT_HEIGHT = 0.03;
	private static double LINE_WIDTH = 1.5;
	private static double MIN_TEXT_SIZE = Double.parseDouble(dao.getKeyValue(EPreferencesValues.MIN_TEXT_SIZE));
	
	private Rectangle hitbox;
	private Zone zone;
	private UIConnection connection;
	private double textHeight;

	private WritableImage drawnImage;
		
	public UIZoneText(LayoutHandler layoutHandler, Zone zone, UIZone uiZone, ViewLayoutAgent viewLayoutAgent) {
		super(layoutHandler, (zone.getTextAreaPos() == null) ? zone.getShape().getArea().getBoundsInLocal().getCenterX() : zone.getTextAreaPos().getX(),
				(zone.getTextAreaPos() == null) ? zone.getShape().getArea().getBoundsInLocal().getMinY() : zone.getTextAreaPos().getY(),
				true, zone.getRimColor(), zone.getFillColor(), viewLayoutAgent);
		this.zone = zone;
		updateDefaultTextSize();
		if (zone.getTextAreaSize() == 0) {
			zone.setTextAreaSize(DEFAULT_TEXT_HEIGHT);
		}
		this.textHeight = zone.getTextAreaSize();
		this.connection = new UIConnection(layoutHandler, zone, this, getInitConnectionPos(zone, uiZone, this), zone.getRimColor(), viewLayoutAgent);
		prepareImage();
		initResizeCorner(new Point(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2));
	}
	
	@Override
	public void drawShape() {	
		connection.drawShape();
		canvasGC.drawImage(drawnImage, posX * viewLayoutAgent.getCanvasWidth() - (drawnImage.getWidth() / 2.0), posY * viewLayoutAgent.getCanvasHeight() - (drawnImage.getHeight() / 2.0));	
		super.drawShape();
	}
	
	public void update() {
		prepareImage();
	}
	
	private void prepareImage() {
		int scale = 5;
	
		StackPane sPane = new StackPane();
		Text txt = new Text(zone.getDisplayText());
		txt.setFont(UIElements.LAYOUT_FONT);
		txt.setFill(zone.getRimColor());

		sPane.getChildren().addAll(txt);
		Bounds bounds = sPane.getBoundsInLocal();

		Rectangle whiteRect = new Rectangle(bounds.getWidth() * 1.1, bounds.getHeight() * 1.1);
		whiteRect.setStroke(zone.getRimColor());
		whiteRect.setStrokeWidth(LINE_WIDTH);
		whiteRect.setFill(Color.WHITE);

		sPane.getChildren().setAll(whiteRect, txt);

		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));

		drawnImage = sPane.snapshot(params, null);

		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(viewLayoutAgent.getCanvasHeight() * textHeight * (bounds.getWidth() / bounds.getHeight()));
		view.setFitHeight(viewLayoutAgent.getCanvasHeight() * textHeight);
		drawnImage = view.snapshot(null, null);

		hitbox = new Rectangle(posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())),
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

	public Zone getZone() {
		return zone;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		zone.setTextAreaPos(new Point(newPosX, newPosY));
		hitbox = new Rectangle(posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())),
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(),
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		super.setIsSelected(isSelected);
		connection.setIsSelected(isSelected);
	}

	@Override
	public void removeSelf() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(double newPosY) {
		MIN_TEXT_SIZE = Double.parseDouble(dao.getKeyValue(EPreferencesValues.MIN_TEXT_SIZE));
		textHeight = Math.abs((posY - newPosY) * 2);
		if (textHeight < MIN_TEXT_SIZE) {
			textHeight = MIN_TEXT_SIZE;
		}
		update();
		super.translateResizeCorner(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2);
		zone.setTextAreaSize(textHeight);
		DEFAULT_TEXT_HEIGHT = textHeight;
	}
	
	private Point getInitConnectionPos(Zone zone, UIZone uiZone, UIZoneText uiText) {
		if(zone.getTextConnectionCenterPos() == null) {
			Point p = new Point(((uiZone.getPosX() + uiText.getPosX()) / 2.0), ((uiZone.getPosY() + uiText.getPosY()) / 2.0));
			zone.setTextConnectionCenterPos(p);
			return p;
		} else {
			return new Point(zone.getTextConnectionCenterPos().getX(), zone.getTextConnectionCenterPos().getY());
		}
	}

	public UIConnection getConnection() {
		return connection;
	}

	public UICorner getResizeCorner() {
		return resizeCorner;
	}

	public void moveWithZone(double deltaX, double deltaY) {
		translateShape(posX + deltaX, posY + deltaY);
		connection.translateShape(connection.getPosX() + deltaX, connection.getPosY() + deltaY);
	}

}
