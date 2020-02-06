package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {


	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView2("/gui/DepartMentList.fxml");
	}
	@FXML
	public void onMenuItemABoutAction() {
		loadView("/gui/About.fxml");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private synchronized void loadView(String absoluteName) {   // synchronized = ensures that it is not interrupted
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // load the main
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // getRoot = load first element (ScrollPane)  - getContente = next element 
		
		Node mainMenu = mainVBox.getChildren().get(0); // first element within "children" (menuBar) = storing menuBar in a variable
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu); 
		mainVBox.getChildren().addAll(newVBox.getChildren()); 
		
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	private synchronized void loadView2(String absoluteName) {   // synchronized = ensures that it is not interrupted
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // load the main
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // getRoot = load first element (ScrollPane)  - getContente = next element 
		
		Node mainMenu = mainVBox.getChildren().get(0); // first element within "children" (menuBar) = storing menuBar in a variable
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu); 
		mainVBox.getChildren().addAll(newVBox.getChildren()); 
		
		DepartmentListController controller = loader.getController();  // create dependency with provisional list
		controller.setDepartmentService(new DepartmentService());
		controller.updateTableView();
		
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
