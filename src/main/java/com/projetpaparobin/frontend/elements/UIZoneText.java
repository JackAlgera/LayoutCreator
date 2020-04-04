package com.projetpaparobin.frontend.elements;

import java.awt.Rectangle;

import com.projetpaparobin.frontend.elements.shapes.UIRectangle;
import com.projetpaparobin.objects.zones.Shape;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIZoneText extends UIElement {

	private static double Y_OFFSET = -5;
	private static double X_OFFSET = 5;
	
	private String displayText;
	private Rectangle hitbox;
	private Zone zone;

	private UIRectangle whiteBackground;
		
	public UIZoneText(Zone zone, Canvas canvas) {
		super(zone.getShape().getArea().getBounds2D().getCenterX() + X_OFFSET, zone.getShape().getArea().getBounds2D().getMinY() + Y_OFFSET, zone.getRimColor(), zone.getFillColor(), canvas);
		this.displayText = zone.getDisplayText();
		this.zone = zone;
		this.hitbox = new Rectangle((int) posX, (int) (posY - getTextHeight() * 0.8), getTextWidth(), getTextHeight());
		this.whiteBackground = new UIRectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), rimColor, Color.WHITE, canvas);
	}
	
	@Override
	public void drawShape() {	
		whiteBackground.drawShape();
		
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
		hitbox.setLocation((int) newPosX, (int) (posY - getTextHeight() * 0.8));
		whiteBackground.translateShape(hitbox.getX(), hitbox.getY());
	}
}
