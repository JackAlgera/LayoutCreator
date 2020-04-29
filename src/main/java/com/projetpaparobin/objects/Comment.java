package com.projetpaparobin.objects;

import com.projetpaparobin.objects.zones.Point;
import com.projetpaparobin.utils.UIColor;

public class Comment {

	private Point pos;
	private String text;
	private UIColor color;
	private double textAreaSize;

	public Comment() {
		color = UIColor.BLACK;
	}

	public Comment(Point pos, String text, UIColor color) {
		this.pos = pos;
		this.text = text;
		this.color = color;
		this.textAreaSize = -1;
	}

	public double getTextAreaSize() {
		return textAreaSize;
	}

	public void setTextAreaSize(double textAreaSize) {
		this.textAreaSize = textAreaSize;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UIColor getColor() {
		return color;
	}

	public void setColor(UIColor color) {
		this.color = color;
	}

}
