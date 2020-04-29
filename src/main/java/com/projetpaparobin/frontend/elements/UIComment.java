package com.projetpaparobin.frontend.elements;

import com.projetpaparobin.frontend.agents.layout.ViewLayoutAgent;
import com.projetpaparobin.objects.Comment;
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

public class UIComment extends UIElement {

	private static double DEFAULT_TEXT_HEIGHT = 0.025;
	
	private Rectangle hitbox;
	private Comment comment;
	
	private WritableImage drawnImage;
		
	public UIComment(Comment comment, ViewLayoutAgent viewLayoutAgent) {
		super(comment.getPos().getX(), comment.getPos().getY(), true, comment.getColor().getColor(), null, viewLayoutAgent);
		this.comment = comment;
		if(comment.getTextAreaSize() <= 0) {
			comment.setTextAreaSize(DEFAULT_TEXT_HEIGHT);
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
		Text txt = new Text(comment.getText());
		txt.setFont(UIElements.COMMENT_TEXT_FONT);
			
		sPane.getChildren().addAll(txt);		
		Bounds bounds = sPane.getBoundsInLocal();
				
		SnapshotParameters params = new SnapshotParameters();
		params.setTransform(Transform.scale(scale, scale));
		params.setFill(Color.TRANSPARENT);
		
		drawnImage = sPane.snapshot(params, null);
		
		ImageView view = new ImageView(drawnImage);
		view.setFitWidth(viewLayoutAgent.getCanvasHeight() * comment.getTextAreaSize() * (bounds.getWidth() / bounds.getHeight()));
		view.setFitHeight(viewLayoutAgent.getCanvasHeight() * comment.getTextAreaSize());
		params.setTransform(Transform.scale(1, 1));
		params.setFill(Color.TRANSPARENT);
		drawnImage = view.snapshot(params, null);
		
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public boolean containsPoint(double posX, double posY) {
		return hitbox.contains(posX, posY);
	}
	
	public Comment getComment() {
		return comment;
	}
	
	@Override
	public void translateShape(double newPosX, double newPosY) {
		super.translateShape(newPosX, newPosY);
		comment.setPos(new Point(newPosX, newPosY));
		hitbox = new Rectangle(
				posX - (drawnImage.getWidth() / (2.0 * viewLayoutAgent.getCanvasWidth())), 
				posY - (drawnImage.getHeight() / (2.0 * viewLayoutAgent.getCanvasHeight())),
				drawnImage.getWidth() / viewLayoutAgent.getCanvasWidth(), 
				drawnImage.getHeight() / viewLayoutAgent.getCanvasHeight());
	}

	@Override
	public void removeSelf() {
		layoutHandler.removeComment(comment);
	}

	@Override
	public void resize(double newPosY) {
		comment.setTextAreaSize((posY - newPosY) * 2);
		update();
		super.translateResizeCorner(posX + hitbox.getWidth() / 2.0, posY - hitbox.getHeight() / 2);
		DEFAULT_TEXT_HEIGHT = comment.getTextAreaSize();
	}

}
