package com.projetpaparobin.frontend.agents.recapagent;

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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ZoneTableView extends TableView<Zone> {

	private static double CELL_SIZE = 28;
	
	private PresentationLayoutAgent presLayout;
	private TableColumn<Zone, String> areaNameColumn;
	private TableColumn<Zone, EActivityType> activityTypeColumn;
	private TableColumn<Zone, EAreaType> areaTypeColumn;
	private TableColumn<Zone, UIColor> colorColumn;
	private TableColumn<Zone, Integer> areaNumberColumn, areaSizeColumn;
	
	@SuppressWarnings("unchecked")
	public ZoneTableView(PresentationLayoutAgent presLayout, double width) {
		super();
		this.presLayout = presLayout;
		prepareTable(width);
		
		width = width * 0.97;
		double nbrColumns = 6;
		
		setAreaNameColumn(width / nbrColumns);
		setNumberColumn(width / nbrColumns);
		setAreaTypeColumn(width / nbrColumns);
		setActivityTypeColumn(width / nbrColumns);
		setAreaSizeColumn(width / nbrColumns);
		setColorColumn(width / nbrColumns);
		
		this.getColumns().addAll(areaNameColumn, areaNumberColumn, areaTypeColumn, activityTypeColumn, areaSizeColumn, colorColumn); 
		this.setItems(LayoutHandler.getInstance().getZones());
	}
	
	private void prepareTable(double width) {
		this.setMaxWidth(width);
		this.setMinWidth(width);
		this.setFixedCellSize(CELL_SIZE);
//		this.prefHeightProperty().bind(Bindings.size(LayoutHandler.getInstance().getZones()).multiply(this.getFixedCellSize()).add(26));
		
		this.setEditable(true);				
		this.getSelectionModel().cellSelectionEnabledProperty().set(true);
		
		this.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE) {
				this.getSelectionModel().clearSelection();
			}
		});
		
	}
	
	private <T> TableColumn<Zone, T> createColumn(String header, String getterName, double maxWidth) {
		TableColumn<Zone, T> column = new TableColumn<Zone, T>(header);
		column.setCellValueFactory(new PropertyValueFactory<Zone, T>(getterName));
		column.setMaxWidth(maxWidth);
		column.setMinWidth(maxWidth);
		column.setStyle("-fx-alignment: CENTER;");
		return column;
	}
	
	private void setAreaNameColumn(double maxWidth) {
		areaNameColumn = createColumn("Area name", "areaName", maxWidth);
		areaNameColumn.setCellFactory(EditableCellTextField.<Zone, String>forTableColumn(new DefaultStringConverter(), null));
		
		areaNameColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaName(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setNumberColumn(double maxWidth) {		
		areaNumberColumn = createColumn("Area number", "areaNumber", maxWidth);
		areaNumberColumn.setCellFactory(EditableCellTextField.<Zone, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		areaNumberColumn.setOnEditCommit(event -> {
			Integer newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaNumber(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setAreaTypeColumn(double maxWidth) {
		areaTypeColumn = createColumn("Area type", "areaType", maxWidth);
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
		activityTypeColumn = createColumn("Activity type", "activityType", maxWidth);
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
		colorColumn = createColumn("Color", "fillColor", maxWidth);
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
		areaSizeColumn = createColumn("Area Size", "areaSize", maxWidth);
		areaSizeColumn.setCellFactory(EditableCellTextField.<Zone, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		areaSizeColumn.setOnEditCommit(event -> {
			int newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setAreaSize(newVal);
			presLayout.updateShapes();
		});
	}
	
}
