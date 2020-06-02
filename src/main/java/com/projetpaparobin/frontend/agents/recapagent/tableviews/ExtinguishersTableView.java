package com.projetpaparobin.frontend.agents.recapagent.tableviews;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.projetpaparobin.documents.LayoutHandler;
import com.projetpaparobin.documents.tabs.ETabHandlerEvent;
import com.projetpaparobin.documents.tabs.ITabHandler;
import com.projetpaparobin.documents.tabs.TabHandler;
import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;
import com.projetpaparobin.frontend.agents.recapagent.converters.UIColorConverter;
import com.projetpaparobin.objects.extinguishers.EExtinguisherType;
import com.projetpaparobin.objects.extinguishers.EProtectionType;
import com.projetpaparobin.objects.extinguishers.Extinguisher;
import com.projetpaparobin.objects.zones.Zone;
import com.projetpaparobin.utils.UIColor;
import com.projetpaparobin.utils.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class ExtinguishersTableView extends UITableViewAbs<Extinguisher> implements ITabHandler, ListChangeListener<Zone> {
	
	private static TabHandler tabHandler = TabHandler.getInstance();
	private static LayoutHandler selectedLayoutHandler = null;
	
	private TableColumn<Extinguisher, Integer> fabricationYearColumn;
	private TableColumn<Extinguisher, String> protectionTypeColumn, brandColumn, numberColumn, extinguisherTypeColumn, localColumn, zoneColumn;
	private TableColumn<Extinguisher, Boolean> isNewColumn;
	private TableColumn<Extinguisher, UIColor> colorColumn;

	double nbrColumns = 9;
	
	private ObservableList<String> zoneNames;
	
	@SuppressWarnings("unchecked")
	public ExtinguishersTableView(PresentationLayoutAgent presLayout, double width) {
		super(presLayout, width);
		
		width = width * 0.95;

		setZoneColumn(width / nbrColumns);
		setNumberColumn(width / nbrColumns);				
		setExtinguisherTypeColumn(width / nbrColumns);		
		setProtectionTypeColumn(width / nbrColumns);	
		setFabricationYearColumn(width / nbrColumns);	
		setBrandColumn(width / nbrColumns);	
		setLocalColumn(width / nbrColumns);
		setColorColumn(width / nbrColumns);
		setIsNewColumn(width / nbrColumns);
		this.getColumns().addAll(zoneColumn, numberColumn, extinguisherTypeColumn, protectionTypeColumn, fabricationYearColumn, brandColumn, localColumn, colorColumn, isNewColumn); 
		tabHandler.addListener(this);
	}	
	
	public void resizePanel(double width, double height) {
		this.setMaxWidth(width);
		this.setMinWidth(width);
		width = width * 0.95;
		zoneColumn.setMaxWidth(width / nbrColumns);
		zoneColumn.setMinWidth(width / nbrColumns);
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
		isNewColumn.setMaxWidth(width / nbrColumns);
		isNewColumn.setMinWidth(width / nbrColumns);
	}
	
	private void setZoneColumn(double maxWidth) {
		zoneNames = FXCollections.observableArrayList(tabHandler.getAllZones().stream()
				.map(zone -> zone.getId().getDefaultAreaName())
				.collect(Collectors.toList()));

		zoneColumn = createColumn("Zone", maxWidth);
		zoneColumn.setCellValueFactory(new PropertyValueFactory<Extinguisher, String>("zoneDisplayText"));
		zoneColumn.setCellFactory(EditableCellComboBox.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), zoneNames));
		
		zoneColumn.setOnEditCommit(event -> {		
			if(selectedLayoutHandler == null) {
				return;
			}
			Zone oldZone = null;
			if(event.getOldValue() != null) {
				oldZone = selectedLayoutHandler.getZoneFromDefaultZoneName(event.getOldValue());
			}
			Zone newZone = null;
			if(event.getNewValue() != null) {
				newZone = selectedLayoutHandler.getZoneFromDefaultZoneName(event.getNewValue());
			}
			
			Extinguisher ex = event.getTableView().getItems().get(event.getTablePosition().getRow());
			
			if(oldZone != null) {
				oldZone.removeExtinguisher(ex);
			}
			if(newZone != null) {
				newZone.addExtinguisher(ex);
				ex.setZone(newZone);
			}
			presLayout.updateShapes();
		});
	}
	
	private void setNumberColumn(double maxWidth) {
		numberColumn = createColumn("Numérotation", maxWidth);
		numberColumn.setCellValueFactory(v -> v.getValue().numberProperty());
		numberColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), UIElements.getNumberFilter()));
		
		numberColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setNumber(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setFabricationYearColumn(double maxWidth) {
		fabricationYearColumn = createColumn("Année de\fabrication", maxWidth);
		fabricationYearColumn.setCellValueFactory(v -> v.getValue().fabricationYearProperty().asObject());
		fabricationYearColumn.setCellFactory(EditableCellTextField.<Extinguisher, Integer>forTableColumn(new IntegerStringConverter(), UIElements.getNumberFilter()));
		
		fabricationYearColumn.setOnEditCommit(event -> {
			Number newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setFabricationYear(newVal.intValue());
			presLayout.updateShapes();
		});
	}
	
	private void setExtinguisherTypeColumn(double maxWidth) {
		extinguisherTypeColumn = createColumn("Type\nd'extincteur", maxWidth);
		extinguisherTypeColumn.setCellValueFactory(v -> v.getValue().extinguisherTypeProperty());
		extinguisherTypeColumn.setCellFactory(EditableCellComboBox.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), FXCollections.observableArrayList(Stream.of(EExtinguisherType.values())
				.filter(type -> !type.equals(EExtinguisherType.OTHER))
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		extinguisherTypeColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setExtinguisherType(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setProtectionTypeColumn(double maxWidth) {
		protectionTypeColumn = createColumn("Type de\nprotection", maxWidth);
		protectionTypeColumn.setCellValueFactory(v -> v.getValue().protectionTypeProperty());
		protectionTypeColumn.setCellFactory(EditableCellComboBox.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), FXCollections.observableArrayList(Stream.of(EProtectionType.values())
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		protectionTypeColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setProtectionType(newVal);
			presLayout.updateShapes();
		});
	}
		
	private void setBrandColumn(double maxWidth) {
		brandColumn = createColumn("Marque", maxWidth);
		brandColumn.setCellValueFactory(v -> v.getValue().brandProperty());
		brandColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), null));
		
		brandColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setBrand(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setLocalColumn(double maxWidth) {
		localColumn = createColumn("Local", maxWidth);
		localColumn.setCellValueFactory(v -> v.getValue().localProperty());
		localColumn.setCellFactory(EditableCellTextField.<Extinguisher, String>forTableColumn(new DefaultStringConverter(), null));
		
		localColumn.setOnEditCommit(event -> {
			String newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setLocal(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setColorColumn(double maxWidth) {
		colorColumn = createColumn("Couleur", maxWidth);
		colorColumn.setCellValueFactory(new PropertyValueFactory<Extinguisher, UIColor>("color"));
		colorColumn.setCellFactory(EditableCellComboBox.<Extinguisher, UIColor>forTableColumn(new UIColorConverter(), FXCollections.observableArrayList(UIElements.DEFAULT_EXTINGUISHER_COLORS.stream()
				.map(val -> val.toString())
				.collect(Collectors.toList()))));
		
		colorColumn.setOnEditCommit(event -> {
			UIColor newVal = (event.getNewValue() != null) ? event.getNewValue() : event.getOldValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setColor(newVal);
			presLayout.updateShapes();
		});
	}
	
	private void setIsNewColumn(double maxWidth) {
		isNewColumn = createColumn("Nouvel\nExtincteur", maxWidth);
		isNewColumn.setCellValueFactory(v -> v.getValue().isNewProperty());
		isNewColumn.setCellFactory(CheckBoxTableCell.forTableColumn(isNewColumn));
	}

	@Override
	public void handleTabHAndlerEvent(ETabHandlerEvent event) {
		switch (event) {
		case ADDED_NEW_TAB:
			break;
		case CHANGED_SELECTED_TAB:
			if(selectedLayoutHandler != null) {
				selectedLayoutHandler.removeZonesListListener(this);
			}			
			
			selectedLayoutHandler = tabHandler.getSelectedLayoutHandler();
			
			if(selectedLayoutHandler != null) {
				zoneNames.clear();
				for (Zone zone : selectedLayoutHandler.getZones()) {
					zoneNames.add(zone.getId().getDefaultAreaName());
				}
				this.setItems(selectedLayoutHandler.getExtinguishers());
				selectedLayoutHandler.addZonesListListener(this);
			}
			break;
		case REMOVED_TAB:
			break;
		}
	}

	@Override
	public void onChanged(Change<? extends Zone> change) {
		while(change.next()) {
		}
		zoneNames.clear();
		for (Zone zone : selectedLayoutHandler.getZones()) {
			zoneNames.add(zone.getId().getDefaultAreaName());
		}
	}
	
}
