package com.projetpaparobin.frontend.shapes.texts;

import java.awt.Rectangle;

import com.projetpaparobin.frontend.shapes.UICircle;
import com.projetpaparobin.frontend.shapes.UIRectangle;
import com.projetpaparobin.frontend.shapes.UIShape;
import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.Shape;
import com.projetpaparobin.zones.Zone;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class ZoneText extends UIShape {

	private static double Y_OFFSET = -5;
	private static double X_OFFSET = 5;
	
	private String displayText;
	private Rectangle hitbox;
	private Zone zone;

//	private UICircle clickCircle;
	private UIRectangle whiteBackground;
	
	public ZoneText(String displayText, double posX, double posY, Color rimColor, Color fillColor, Zone zone, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.displayText = displayText;
		this.zone = zone;
//		this.clickCircle = new UICircle(posX, posY, 10.0, rimColor, fillColor, canvas);
		this.hitbox = new Rectangle((int) posX, (int) posY, getTextWidth(), getTextHeight());
		this.whiteBackground = new UIRectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), rimColor, Color.WHITE, canvas);
	}
	
	public ZoneText(Zone zone, Canvas canvas) {
		super(zone.getShape().getArea().getBounds2D().getCenterX() + X_OFFSET, zone.getShape().getArea().getBounds2D().getMinY() + Y_OFFSET, zone.getRimColor(), zone.getFillColor(), canvas);
		this.displayText = zone.getDisplayText();
		this.zone = zone;
//		this.clickCircle = new UICircle(posX, posY, 4.0, 3.0, Color.BLACK, Color.WHITE, canvas);
		this.hitbox = new Rectangle((int) posX, (int) (posY - getTextHeight() * 0.8), getTextWidth(), getTextHeight());
		this.whiteBackground = new UIRectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), rimColor, Color.WHITE, canvas);
	}
	
	@Override
	public void drawShape() {	
		whiteBackground.drawShape();
//		clickCircle.drawShape();
		
		canvasGC.setFont(UIElements.LAYOUT_FONT);
		canvasGC.setFill(rimColor);
		canvasGC.fillText(displayText, posX + X_OFFSET, posY);		
	}
	
	private int getTextWidth() {
		return ((int) X_OFFSET * 3 + (displayText.length() * (int) (UIElements.LAYOUT_FONT.getSize() / 2.0)));
	}
	
	private int getTextHeight() {
		return ((int) UIElements.LAYOUT_FONT.getSize());
	}
	
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Zone getZone() {
		return zone;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
//		clickCircle.translateShape(newPosX, newPosY);
		hitbox.setLocation((int) newPosX, (int) (posY - getTextHeight() * 0.8));
		whiteBackground.translateShape(hitbox.getX(), hitbox.getY());
	}
}
