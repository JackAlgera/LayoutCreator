package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.utils.UIElements;

import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;

public class UIComment extends UIElement {

	private Rectangle hitbox;
	private Comment comment;
	
	private WritableImage drawnImage;
	private Bounds bounds;
		
	public UIComment(Comment comment, Canvas canvas) {
		super(comment.getPos().getX(), comment.getPos().getY(), comment.getColor().getColor(), null, canvas);
		this.comment = comment;
		prepareImage();
	}
	
	@Override
	public void drawShape() {	
		canvasGC.drawImage(drawnImage, posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0));	
	}
	
	public void update() {
		prepareImage();
	}
	
	private void prepareImage() {
		int scale = 5;
	
		StackPane sPane = new StackPane();		
		Text txt = new Text(comment.getText());
		txt.setFont(UIElements.COMMENT_TEXT_FONT);
			
		sPane.getChildren().addAll(txt);		
		bounds = sPane.getBoundsInLocal();
				
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(bounds.getWidth());
		view.setFitHeight(bounds.getHeight());
		params.setTransform(Transform.scale(1, 1));
		drawnImage = view.snapshot(params, null);
		
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Comment getomment() {
		return comment;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		hitbox = new Rectangle(posX - (bounds.getWidth() / 2.0), posY - (bounds.getHeight() / 2.0), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void removeSelf() {
		layoutHandler.removeComment(comment);
	}

}
