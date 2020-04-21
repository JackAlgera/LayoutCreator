package com.projetpaparobin.documents.applicationstate;

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

	public ApplicationStatePOJO() {
		this.zones = new ArrayList<Zone>();
		this.extinguishers = new ArrayList<Extinguisher>();
		this.comments = new ArrayList<Comment>();
	}
	
	public ApplicationStatePOJO(ObservableList<Zone> zones, ObservableList<Comment> comments) {
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
