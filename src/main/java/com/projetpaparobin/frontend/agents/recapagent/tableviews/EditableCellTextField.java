package com.projetpaparobin.frontend.agents.recapagent.tableviews;

import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TextFormatter.Change;

public class EditableCellTextField<S, T> extends TableCell<S, T> {
	    			
	private TextField textField;
	private StringConverter<T> converter;
	private UnaryOperator<Change> filter;

	public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(StringConverter<T> converter, UnaryOperator<Change> filter) {
		return list -> new EditableCellTextField<S, T>(converter, filter);
	}
    	
	public EditableCellTextField(StringConverter<T> converter, UnaryOperator<Change> filter) {
		this.converter = converter;
		this.filter = filter;
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		createTextField();
		setText(null);
		setGraphic(textField);
		textField.selectAll();
		textField.requestFocus();
	}
		
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		
		setText(getString());
		setGraphic(null);
	}
    
	@Override
	public void commitEdit(T newValue) {
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
	
	private void createTextField() {
    	textField = new TextField(getString());
    	
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					commitEdit(converter.fromString(textField.getText()));
				}
			}
		});
		
		textField.setOnAction(event -> {
			this.commitEdit(converter.fromString(textField.getText()));
			event.consume();
		});
		
		textField.setAlignment(Pos.CENTER);
		if(filter != null) {
			textField.setTextFormatter(new TextFormatter<String>(filter));
		}
    }
	
}
