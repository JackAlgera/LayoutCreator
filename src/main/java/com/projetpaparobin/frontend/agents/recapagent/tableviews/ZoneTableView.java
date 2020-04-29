package com.projetpaparobin.frontend.agents.recapagent.tableviews;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.recapagent.converters.ActivityTypeConverter;
import com.projetpaparobin.frontend.agents.recapagent.converters.AreaTypeConverter;
import com.projetpaparobin.frontend.agents.recapagent.converters.UIColorConverter;
import com.projetpaparobin.objects.zones.EActivityType;
import com.projetpaparobin.objects.zones.EAreaType;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ZoneTableView extends UITableViewAbs<Zone> {

	private TableColumn<Zone, String> areaNameColumn;
	private TableColumn<Zone, EActivityType> activityTypeColumn;
	private TableColumn<Zone, EAreaType> areaTypeColumn;
	private TableColumn<Zone, UIColor> colorColumn;
	private TableColumn<Zone, Integer> areaNumberColumn, areaSizeColumn;
	
	@SuppressWarnings("unchecked")
	public ZoneTableView(PresentationLayoutAgent presLayout, double width) {
		super(presLayout, width);
		
		width = width * 0.97;
		double nbrColumns = 6;
		
		setAreaNameColumn(width / nbrColumns);
		setAreaTypeColumn(width / nbrColumns);
		setNumberColumn(width / nbrColumns);
		setActivityTypeColumn(width / nbrColumns);
		setAreaSizeColumn(width / nbrColumns);
		setColorColumn(width / nbrColumns);
		
		this.getColumns().addAll(areaNameColumn, areaTypeColumn, areaNumberColumn, activityTypeColumn, areaSizeColumn, colorColumn); 
		this.setItems(LayoutHandler.getInstance().getZones());
	}
	
	public void resizePanel(double width, double height) {
		this.setMaxWidth(width);
		this.setMinWidth(width);
		width = width * 0.97;
		double nbrColumns = 6;
		areaNameColumn.setMaxWidth(width / nbrColumns);
		areaNameColumn.setMinWidth(width / nbrColumns);
		activityTypeColumn.setMaxWidth(width / nbrColumns);
		activityTypeColumn.setMinWidth(width / nbrColumns);
		areaTypeColumn.setMaxWidth(width / nbrColumns);
		areaTypeColumn.setMinWidth(width / nbrColumns);
		colorColumn.setMaxWidth(width / nbrColumns);
		colorColumn.setMinWidth(width / nbrColumns);
		areaNumberColumn.setMaxWidth(width / nbrColumns);
		areaNumberColumn.setMinWidth(width / nbrColumns);
		areaSizeColumn.setMaxWidth(width / nbrColumns);
		areaSizeColumn.setMinWidth(width / nbrColumns);
	}
	
	private void setAreaNameColumn(double maxWidth) {
		areaNameColumn = createColumn("Nom de la zone", "areaName", maxWidth);
		areaNameColumn.setCellFactory(EditableCellTextField.<Zone, String>forTableColumn(new DefaultStringConverter(), null));
		
		areaNameColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaName(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setNumberColumn(double maxWidth) {		
		areaNumberColumn = createColumn("Numérotation de la zone", "areaNumber", maxWidth);
		areaNumberColumn.setCellFactory(EditableCellTextField.<Zone, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		areaNumberColumn.setOnEditCommit(event -> {
			Integer newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaNumber(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setAreaTypeColumn(double maxWidth) {
		areaTypeColumn = createColumn("Type de zone", "areaType", maxWidth);
		areaTypeColumn.setCellFactory(EditableCellComboBox.<Zone, EAreaType>forTableColumn(new AreaTypeConverter(), FXCollections.observableArrayList(Stream.of(EAreaType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		areaTypeColumn.setOnEditCommit(event -> {
			EAreaType newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaType(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setActivityTypeColumn(double maxWidth) {
		activityTypeColumn = createColumn("Type d'activité", "activityType", maxWidth);
		activityTypeColumn.setCellFactory(EditableCellComboBox.<Zone, EActivityType>forTableColumn(new ActivityTypeConverter(), FXCollections.observableArrayList(Stream.of(EActivityType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		activityTypeColumn.setOnEditCommit(event -> {
			EActivityType newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setActivityType(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setColorColumn(double maxWidth) {
		colorColumn = createColumn("Couleur", "fillColor", maxWidth);
		colorColumn.setCellFactory(EditableCellComboBox.<Zone, UIColor>forTableColumn(new UIColorConverter(), FXCollections.observableArrayList(UIElements.DEFAULT_ZONE_COLORS.stream()
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		colorColumn.setOnEditCommit(event -> {
			UIColor newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setFillColor(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setAreaSizeColumn(double maxWidth) {
		areaSizeColumn = createColumn("Taille de la zone", "areaSize", maxWidth);
		areaSizeColumn.setCellFactory(EditableCellTextField.<Zone, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		areaSizeColumn.setOnEditCommit(event -> {
			int newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaSize(newVal);
			presLayout.updateShapes();
		});
	}
	
}
