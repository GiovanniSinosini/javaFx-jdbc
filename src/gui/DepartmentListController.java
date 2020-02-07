package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{

	private DepartmentService service; // connect DepartmentService with DepartmentController -> solid principle
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	
	@FXML
	public void onBtNewAction(ActionEvent event) {   // ActionEvent event = argument to identify the controller
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage); // form to create
	
	}
	
	public void setDepartmentService(DepartmentService service) {  // connect DepartmentService with DepartmentController -> solid principle = inversion of control   
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));  // start column behavior - standard command 
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name")); // start column behavior - standard command 
		
		Stage stage = (Stage) Main.getMainScene().getWindow(); // get window reference
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty()); // for table and window are the same size
		
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Department> list = service.findAll(); // load service list
		obsList = FXCollections.observableArrayList(list); // assign list to obsList
		tableViewDepartment.setItems(obsList);  //  load on the screen
		
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {   // to instantiate the dialog window - goal = create a new department
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));  //load view
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController(); // get controller
			controller.setDepartment(obj);  // insert obj into the controller
			controller.updateFormData();  // load the contents of the obj into the form
			
			Stage dialogStage = new Stage();   // one stage in front of the other, so it is necessary to instantiate another stage
			dialogStage.setTitle("Enter Department data");  // window title
			dialogStage.setScene(new Scene(pane)); // What will the scene be? The loaded panel
			dialogStage.setResizable(false);  // Can it be scaled or not?
			dialogStage.initOwner(parentStage); 
			dialogStage.initModality(Modality.WINDOW_MODAL);  // until you close the current window, you cannot access other windows
			dialogStage.showAndWait();
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
		}
		
		
	}

}
