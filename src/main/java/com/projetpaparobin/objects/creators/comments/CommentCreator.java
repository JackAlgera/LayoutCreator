package com.projetpaparobin.objects.creators.comments;

import java.util.ArrayList;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.objects.zones.Point;

public class CommentCreator {

	private LayoutHandler layoutHandler;

	private ECommentCreationState state;
	private ArrayList<ICommentCreatorListener> listeners;
	private Comment currentComment;

	public CommentCreator(LayoutHandler layoutHandler) {
		this.layoutHandler = layoutHandler;
		state = ECommentCreationState.FINISHED;
		listeners = new ArrayList<ICommentCreatorListener>();
	}
	
	public Comment getCurrentComment() {
		return currentComment;
	}

	public void newComment() {
		currentComment = new Comment();
		state = ECommentCreationState.NEW_COMMENT;
		sendEvent(ECommentEvent.CREATING_NEW_COMMENT);
	}

	public void setPosition(Point p) {
		if (currentComment != null && state == ECommentCreationState.NEW_COMMENT) {
			currentComment.setPos(p);
			state = ECommentCreationState.SETTING_TEXT;
			sendEvent(ECommentEvent.SETTING_TEXT);
		}
	}

	public void setCommentText(String text) {
		if (currentComment != null && (state == ECommentCreationState.SETTING_TEXT)) {
			currentComment.setText(text);
			doneCreatingComment();
		}
	}

	private void doneCreatingComment() {
		if (state == ECommentCreationState.SETTING_TEXT) {
			state = ECommentCreationState.FINISHED;
			layoutHandler.addComment(currentComment);
			sendEvent(ECommentEvent.FINISHED_CREATING_COMMENT);
		}
	}

	public void reset() {
	}
	
	public void canceled() {
		state = ECommentCreationState.FINISHED;
		sendEvent(ECommentEvent.CANCELED);
	}

	public void addListener(ICommentCreatorListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ICommentCreatorListener listener) {
		listeners.remove(listener);
	}

	private void sendEvent(ECommentEvent event) {
		for (ICommentCreatorListener listener : listeners) {
			listener.handleCommentCreatorEvent(event);
		}
	}

}
