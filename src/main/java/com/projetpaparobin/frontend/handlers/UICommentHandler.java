package com.projetpaparobin.frontend.handlers;

import java.util.ArrayList;

import com.projetpaparobin.frontend.elements.UIComment;

public class UICommentHandler {

	private static UICommentHandler instance;
	private ArrayList<UIComment> comments;
		
	private UICommentHandler() {	
		comments = new ArrayList<UIComment>();
	}
	
	public static UICommentHandler getInstance() {
		if(instance == null) {
			instance = new UICommentHandler();
		}
		
		return instance;
	}
	
	public UIComment getComment(double posX, double posY) {
		for (UIComment comment : comments) {
			if(comment.containsPoint(posX, posY)) {
				return comment;
			}
		}
		return null;
	}
	
	public ArrayList<UIComment> getComments() {
		return comments;
	}
	
	public void addComment(UIComment comment) {
		comments.add(comment);
	}
	
	public void reset() {
		comments.clear();
	}
	
}
