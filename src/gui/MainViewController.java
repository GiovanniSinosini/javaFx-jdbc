package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.SellerService;

public class MainViewController implements Initializable {


	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {  // action initialization
			controller.setSellerService (new SellerService());
			controller.updateTableView();  // create dependency with list
		});
	}
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartMentList.fxml", (DepartmentListController controller) -> {  // action initialization
			controller.setDepartmentService (new DepartmentService());
			controller.updateTableView();  // create dependency with list
		});
	}
	@FXML
	public void onMenuItemABoutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> InitializingAction) {   // synchronized = ensures that it is not interrupted  -  <T> = generic - Consumer<T> InitializingAction = generic function
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); // load the main
		VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // getRoot = load first element (ScrollPane)  - getContente = next element 
		
		Node mainMenu = mainVBox.getChildren().get(0); // first element within "children" (menuBar) = storing menuBar in a variable
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu); 
		mainVBox.getChildren().addAll(newVBox.getChildren()); 
		
		T controller = loader.getController(); // will return the controller of the "T controller" (generic type = which in this case is "DepartmentListController")
		InitializingAction.accept(controller);   // run the controller returned above
		
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
