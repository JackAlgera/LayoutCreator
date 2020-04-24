package com.projetpaparobin.documents.applicationstate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.projetpaparobin.objects.Comment;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;

import javafx.collections.ObservableList;

@JsonRootName(value = "ApplicationState")
public class ApplicationStatePOJO {

	private ArrayList<Zone> zones;
	private ArrayList<Extinguisher> extinguishers;
	private ArrayList<Comment> comments;
	
	private String base64Image;

	public ApplicationStatePOJO() {
		this.zones = new ArrayList<Zone>();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.comments = new ArrayList<Comment>();
		this.base64Image = null;
	}
	
	public ApplicationStatePOJO(ObservableList<Zone> zones, ObservableList<Comment> comments, BufferedImage bufImage) {
		this.zones = new ArrayList<Zone>();
		this.extinguishers = new ArrayList<Extinguisher>();
		for (Zone zone : zones) {
			this.zones.add(zone);
			for (Extinguisher ex : zone.getExtinguishers()) {
				extinguishers.add(ex);
			}
		}
		
		this.comments = new ArrayList<Comment>();
		for (Comment comment : comments) {
			this.comments.add(comment);
		}
		
		if(bufImage != null) {
			base64Image = ApplicationStatePersister.encoretoString(bufImage);
		}
	}
	
	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
		for (Zone zone : zones) {
			for (Extinguisher ex : zone.getExtinguishers()) {
				extinguishers.add(ex);
			}
		}
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	@JsonIgnore
	public ArrayList<Extinguisher> getExtinguishers() {
		return extinguishers;
	}	
	
}
