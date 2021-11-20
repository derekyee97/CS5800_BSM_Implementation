package view;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/*************************************************
 * @author Derek Yee
 * This is the main class that is used to run the program. 
 * Utilizes JavaFX for GUI components.
 ************************************************/
public class Bsm_Main_Menu extends Application 
{
	public static void main(String[] args)
	{
		launch(args);
	}
	//global variables 
	BorderPane root=new BorderPane();
	HBox menu=new HBox(30);
	Button addItemB=new Button("Add Item");
	Button registrationB=new Button("Registration");
	Button orderManB=new Button("Order Management");
	Button storeB=new Button("Store");
	Button genReportB=new Button("Generate Report");
	
	Region padderRegion = new Region();
	Bsm_Registration_Menu registrationMenu;
	Add_New_Item_Menu addItemMenu;
	Shop_Menu shopMenu;
	public void start(Stage myStage) 
	{
		registrationMenu=new Bsm_Registration_Menu();
		addItemMenu=new Add_New_Item_Menu (myStage);
		shopMenu=new Shop_Menu();
		Scene myScene=new Scene(root,700,700);
		menu.setAlignment(Pos.CENTER);
		root.setLeft(padderRegion);
		
		menu.getChildren().addAll(registrationB,storeB,addItemB,genReportB);
		menu.setAlignment(Pos.CENTER);
		padderRegion.setPrefWidth(10);
		root.setTop(menu);
				
		myStage.setScene(myScene);
		myStage.setFullScreen(true);
		myStage.show();
		
		//button functionalities
		registrationB.setOnAction(event->{
			VBox test=registrationMenu.getMenu();
			test.setAlignment(Pos.CENTER);
			root.setCenter(test);
		});
		addItemB.setOnAction(event->{
			VBox test=addItemMenu.getMenu();
			test.setAlignment(Pos.CENTER);
			root.setCenter(test);
		});
		storeB.setOnAction(event->{
			HBox test=shopMenu.getMenu(); 
			root.setCenter(test);
		});
		
	}
	
}

