package com.projetpaparobin.frontend.agents.texts;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.projetpaparobin.frontend.shapes.UICircle;
import com.projetpaparobin.frontend.shapes.UIRectangle;
import com.projetpaparobin.frontend.shapes.UIShape;
import com.projetpaparobin.utils.UIElements;
import com.projetpaparobin.zones.Shape;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Text extends UIShape {

	private static double Y_OFFSET = -5;
	private static double X_OFFSET = 5;
	
	private String displayText;
	private Rectangle hitbox;

	private UICircle clickCircle;
	private UIRectangle whiteBackground;
	
	public Text(String displayText, double posX, double posY, Color rimColor, Color fillColor, Canvas canvas) {
		super(posX, posY, rimColor, fillColor, canvas);
		this.displayText = displayText;
		this.clickCircle = new UICircle(posX, posY, 10.0, rimColor, fillColor, canvas);
		this.hitbox = new Rectangle((int) posX, (int) posY, getTextWidth(), getTextHeight());
		this.whiteBackground = new UIRectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), rimColor, Color.WHITE, canvas);
	}
	
	public Text(String displayText, Shape shape, Color rimColor, Color fillColor, Canvas canvas) {
		super(shape.getArea().getBounds2D().getCenterX() + X_OFFSET, shape.getArea().getBounds2D().getMinY() + Y_OFFSET, rimColor, fillColor, canvas);
		this.displayText = displayText;
		this.clickCircle = new UICircle(posX, posY, 4.0, 3.0, Color.BLACK, Color.WHITE, canvas);
		this.hitbox = new Rectangle((int) posX, (int) (posY - getTextHeight() * 0.8), getTextWidth(), getTextHeight());
		this.whiteBackground = new UIRectangle(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight(), rimColor, Color.WHITE, canvas);
	}
	
	@Override
	public void drawShape() {	
		whiteBackground.drawShape();
		clickCircle.drawShape();
		
		canvasGC.setFont(UIElements.LAYOUT_FONT);
		canvasGC.setFill(rimColor);
		canvasGC.fillText(displayText, posX, posY);		
	}
	
	private int getTextWidth() {
		return (displayText.length() * (int) (UIElements.LAYOUT_FONT.getSize() / 2.0));
	}
	
	private int getTextHeight() {
		return ((int) UIElements.LAYOUT_FONT.getSize());
	}
}
