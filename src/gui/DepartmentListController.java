package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
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
	

}
