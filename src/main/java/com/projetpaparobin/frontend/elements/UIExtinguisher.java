package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.elements.shapes.UICircle;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIElements;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class UIExtinguisher extends UIElement {

	private UICircle clickCircle;	
	private String displayText;
	private String displayNumber;
	
	public UIExtinguisher(Extinguisher ex, Canvas canvas) {
		super(ex.getPositionSquare().getX(), ex.getPositionSquare().getY(), Color.BLACK, ex.getId().getColor(), canvas);
		this.clickCircle = new UICircle(posX, posY, UIElements.LAYOUT_FONT.getSize() / 2.0, 3.0, Color.BLACK, fillColor, canvas);
		this.displayNumber = "" +  ex.getId().getNumber();
		this.displayText = ex.getDisplayText();
	}

	@Override
	public void drawShape() {
		clickCircle.drawShape();
		
		canvasGC.setFont(UIElements.LAYOUT_FONT);
		canvasGC.setFill(Color.BLACK);
		canvasGC.fillText(displayNumber, posX, posY);		
	}
	
	public boolean containsPoint(double posX, double posY) {
		return clickCircle.containsPoint(posX, posY);
	}
}
