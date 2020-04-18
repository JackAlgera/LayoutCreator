package com.projetpaparobin.frontend.agents.recapagent;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.zones.Zone;

import javafx.beans.binding.Bindings;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ZoneTableView extends TableView<Zone> {

	private TableColumn<Zone, String> areaNameColumn, areaNumberColumn, areaTypeColumn, activityTypeColumn, areaSizeColumn;
	
	@SuppressWarnings("unchecked")
	public ZoneTableView(double width) {
		super();
		this.setMaxWidth(width);
		this.setMinWidth(width);
		this.setFixedCellSize(25);
		this.prefHeightProperty().bind(Bindings.size(LayoutHandler.getInstance().getZones()).multiply(this.getFixedCellSize()).add(26));
		
		width = width * 0.95;
		double nbrColumns = 5;
		
		areaNameColumn = createColumn("Area name", "areaName", width / nbrColumns);
		areaNumberColumn = createColumn("Area number", "areaNumber", width / nbrColumns);
		areaTypeColumn = createColumn("Area type", "areaType", width / nbrColumns);
		activityTypeColumn = createColumn("Activity type", "activityType", width / nbrColumns);
		areaSizeColumn = createColumn("Area Size", "areaSize", width / nbrColumns);
		
		this.getColumns().addAll(areaNameColumn, areaNumberColumn, areaTypeColumn, activityTypeColumn, areaSizeColumn);
		this.setItems(LayoutHandler.getInstance().getZones());
	}
	
	private TableColumn<Zone, String> createColumn(String header, String getterName, double maxWidth) {
		TableColumn<Zone, String> column = new TableColumn<Zone, String>(header);
		column.setCellValueFactory(new PropertyValueFactory<Zone, String>(getterName));
		column.setMaxWidth(maxWidth);
		column.setMinWidth(maxWidth);
		return column;
	}
	
}
