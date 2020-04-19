package com.projetpaparobin.frontend.agents.recapagent;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class EditableCellComboBox<S, T> extends TableCell<S, T> {
	    
	private ComboBox<String> comboBox;
	private StringConverter<T> converter;
	private ObservableList<String> list;

	public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> converter, ObservableList<String> list) {
		return l -> new EditableCellComboBox<S, T>(converter, list);
	}
    	
	public EditableCellComboBox(StringConverter<T> converter, ObservableList<String> list) {
		this.converter = converter;
		this.list = list;
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		creatComboBox();
		setText(null);
		setGraphic(comboBox);
		comboBox.show();
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}
		
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setText(getString());
		setGraphic(null);
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}
    
	@Override
	public void commitEdit(T newValue) {
		setContentDisplay(ContentDisplay.TEXT_ONLY);
		super.commitEdit(newValue);
		cancelEdit();
	}
	
	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
				
		if(empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(item.toString());
			setGraphic(null);
		}
	}
	
    private String getString() {
    	if(converter == null) {
            return getItem() == null ? "" : getItem().toString();
    	} else {
            return getItem() == null ? "" : converter.toString(getItem());    		
    	}
    }
	
	private void creatComboBox() {
    	comboBox = new ComboBox<String>(list);
    	comboBox.setValue(getItem().toString());
    	comboBox.addEventHandler(ComboBox.ON_HIDDEN, event -> {
			this.commitEdit(converter.fromString(comboBox.getValue()));
			event.consume();
    	});
    }
	
}

