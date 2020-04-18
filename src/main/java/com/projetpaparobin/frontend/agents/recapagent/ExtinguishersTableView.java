package com.projetpaparobin.frontend.agents.recapagent;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.objects.extinguishers.Extinguisher;

import javafx.beans.binding.Bindings;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExtinguishersTableView extends TableView<Extinguisher> {

	private TableColumn<Extinguisher, String> numberColumn, extinguisherTypeColumn, protectionTypeColumn, fabricationYearColumn, brandColumn, isNewColumn, colorColumn;
		
	@SuppressWarnings("unchecked")
	public ExtinguishersTableView(double width) {
		super();
		this.setMaxWidth(width);
		this.setMinWidth(width);
		this.setFixedCellSize(25);
		this.prefHeightProperty().bind(Bindings.size(LayoutHandler.getInstance().getExtinguishers()).multiply(this.getFixedCellSize()).add(26));
		
		width = width * 0.95;
		int nbrColumns = 7;
		
		numberColumn = createColumn("Number", "number", width / nbrColumns);
		extinguisherTypeColumn = createColumn("Extinguisher type", "extinguisherType", width / nbrColumns);
		protectionTypeColumn = createColumn("Protection type", "protectionType", width / nbrColumns);
		fabricationYearColumn = createColumn("Fabrication year", "fabricationYear", width / nbrColumns);
		brandColumn = createColumn("Brand", "brand", width / nbrColumns);
		isNewColumn = createColumn("Is New", "isNew", width / nbrColumns);
		colorColumn = createColumn("Color", "color", width / nbrColumns);
		
		this.getColumns().addAll(numberColumn, extinguisherTypeColumn, protectionTypeColumn, fabricationYearColumn, brandColumn, isNewColumn, colorColumn);
		this.setItems(LayoutHandler.getInstance().getExtinguishers());
	}
	
	private TableColumn<Extinguisher, String> createColumn(String header, String getterName, double maxWidth) {
		TableColumn<Extinguisher, String> column = new TableColumn<Extinguisher, String>(header);
		column.setCellValueFactory(new PropertyValueFactory<Extinguisher, String>(getterName));
		column.setMaxWidth(maxWidth);
		column.setMinWidth(maxWidth);
		return column;
	}
	
}