package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable{

	private Seller entity;  // create dependency with Seller class
	
	private SellerService service; // create dependency with SellerService
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>(); // list for those interested (objects) in an event 
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setSeller (Seller entity) {
		this.entity = entity;
	}
	
	public void setSellerService (SellerService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener (DataChangeListener listener) {
		dataChangeListeners.add(listener);  // add objects interested in the event to the list
	}
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		entity = getFormData();  
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();  //close current window
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {  
			listener.onDataChanged();
		}
		
		
	}

	private Seller getFormData() {   // get the content of textfields to instantiate a new object(department)
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Validation error"); // exception instantiaded
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null  || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}	
		obj.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();  //close current window

	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rg) {
		initializeNodes();
	}
	
	private void initializeNodes() {   // constraints
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormData() {   // take the contents of the text fields to assign to the object  - in this case: Seller object
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtId.setText(String.valueOf(entity.getId()));   // String.valueof = convert from number to string
		txtName.setText(entity.getName());
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {  // checks if there is this error name in the colletion
			labelErrorName.setText(errors.get("name"));
		}
	}
}
