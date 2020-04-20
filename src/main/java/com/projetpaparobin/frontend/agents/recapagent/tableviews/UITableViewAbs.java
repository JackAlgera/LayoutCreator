package com.projetpaparobin.frontend.agents.recapagent.tableviews;

import com.projetpaparobin.frontend.agents.layout.PresentationLayoutAgent;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

public abstract class UITableViewAbs<T> extends TableView<T> {

	protected static double CELL_SIZE = 28;
	
	protected PresentationLayoutAgent presLayout;
		
	public UITableViewAbs(PresentationLayoutAgent presLayout, double width) {
		super();
		this.presLayout = presLayout;
		prepareTable(width);
	}
	
	protected void prepareTable(double width) {
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
	
	protected <S> TableColumn<T, S> createColumn(String header, String getterName, double maxWidth) {
		TableColumn<T, S> column = new TableColumn<T, S>(header);
		column.setCellValueFactory(new PropertyValueFactory<T, S>(getterName));
		column.setMaxWidth(maxWidth);
		column.setMinWidth(maxWidth);
		column.setStyle("-fx-alignment: CENTER;");
		return column;
	}
	
}
