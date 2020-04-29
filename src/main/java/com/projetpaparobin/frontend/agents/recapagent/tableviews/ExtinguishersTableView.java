package com.projetpaparobin.frontend.agents.recapagent.tableviews;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.recapagent.converters.ProtectionTypeConverter;
import com.projetpaparobin.frontend.agents.recapagent.converters.UIColorConverter;
import com.projetpaparobin.objects.extinguishers.EExtinguisherType;
import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ExtinguishersTableView extends UITableViewAbs<Extinguisher> {

	private TableColumn<Extinguisher, Integer> fabricationYearColumn;
	private TableColumn<Extinguisher, EProtectionType> protectionTypeColumn;
	private TableColumn<Extinguisher, String> brandColumn, numberColumn, extinguisherTypeColumn, localColumn;
	private TableColumn<Extinguisher, Boolean> isNewColumn;
	private TableColumn<Extinguisher, UIColor> colorColumn;
	
	@SuppressWarnings("unchecked")
	public ExtinguishersTableView(PresentationLayoutAgent presLayout, double width) {
		super(presLayout, width);
		
		width = width * 0.97;
		double nbrColumns = 7;

		setNumberColumn(width / nbrColumns);				
		setExtinguisherTypeColumn(width / nbrColumns);		
		setProtectionTypeColumn(width / nbrColumns);	
		setFabricationYearColumn(width / nbrColumns);	
		setBrandColumn(width / nbrColumns);	
		setLocalColumn(width / nbrColumns);
		setColorColumn(width / nbrColumns);
		this.getColumns().addAll(numberColumn, extinguisherTypeColumn, protectionTypeColumn, fabricationYearColumn, brandColumn, localColumn, colorColumn); 
		this.setItems(LayoutHandler.getInstance().getExtinguishers());
	}	
	
	public void resizePanel(double width, double height) {
		this.setMaxWidth(width);
		this.setMinWidth(width);
		width = width * 0.97;
		double nbrColumns = 7;
		fabricationYearColumn.setMaxWidth(width / nbrColumns);
		fabricationYearColumn.setMinWidth(width / nbrColumns);
		protectionTypeColumn.setMaxWidth(width / nbrColumns);
		protectionTypeColumn.setMinWidth(width / nbrColumns);
		brandColumn.setMaxWidth(width / nbrColumns);
		brandColumn.setMinWidth(width / nbrColumns);
		numberColumn.setMaxWidth(width / nbrColumns);
		numberColumn.setMinWidth(width / nbrColumns);
		extinguisherTypeColumn.setMaxWidth(width / nbrColumns);
		extinguisherTypeColumn.setMinWidth(width / nbrColumns);
		colorColumn.setMaxWidth(width / nbrColumns);
		colorColumn.setMinWidth(width / nbrColumns);
		localColumn.setMaxWidth(width / nbrColumns);
		localColumn.setMinWidth(width / nbrColumns);
	}
	
	private void setNumberColumn(double maxWidth) {
		numberColumn = createColumn("Numérotation", "number", maxWidth);
		numberColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), UIElements.getNumberFilter()));
		
		numberColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setNumber(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setFabricationYearColumn(double maxWidth) {
		fabricationYearColumn = createColumn("Année de fabrication", "fabricationYear", maxWidth);
		fabricationYearColumn.setCellFactory(EditableCellTextField.<Extinguisher, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		fabricationYearColumn.setOnEditCommit(event -> {
			Integer newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setFabricationYear(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setExtinguisherTypeColumn(double maxWidth) {
		extinguisherTypeColumn = createColumn("Type d'extincteur", "extinguisherType", maxWidth);
		extinguisherTypeColumn.setCellFactory(EditableCellComboBox.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), FXCollections.observableArrayList(Stream.of(EExtinguisherType.values())
				.filter(type -> !type.equals(EExtinguisherType.OTHER))
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		extinguisherTypeColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setExtinguisherType(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setProtectionTypeColumn(double maxWidth) {
		protectionTypeColumn = createColumn("Type de protection", "protectionType", maxWidth);
		protectionTypeColumn.setCellFactory(EditableCellComboBox.<Extinguisher, EProtectionType>forTableColumn(new ProtectionTypeConverter(), FXCollections.observableArrayList(Stream.of(EProtectionType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		protectionTypeColumn.setOnEditCommit(event -> {
			EProtectionType newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setProtectionType(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setBrandColumn(double maxWidth) {
		brandColumn = createColumn("Marque", "brand", maxWidth);
		brandColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), null));
		
		brandColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setBrand(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setLocalColumn(double maxWidth) {
		localColumn = createColumn("Local", "local", maxWidth);
		localColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), null));
		
		localColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setLocal(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setColorColumn(double maxWidth) {
		colorColumn = createColumn("Couleur", "fillColor", maxWidth);
		colorColumn.setCellFactory(EditableCellComboBox.<Extinguisher, UIColor>forTableColumn(new UIColorConverter(), FXCollections.observableArrayList(UIElements.DEFAULT_EXTINGUISHER_COLORS.stream()
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		colorColumn.setOnEditCommit(event -> {
			UIColor newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).getId().setColor(newVal);
			presLayout.updateShapes();
		});
	}
	
}
