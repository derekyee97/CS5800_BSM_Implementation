package view;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Add_New_Item_Menu 
{
	VBox menu=new VBox(20);
	Label menuHeaderL=new Label("Adding New Items"); 
	Label typeOfItemL=new Label("Type of Item"); 
	ChoiceBox<String> typeOfItem=new ChoiceBox(); 
	HBox choiceHolder=new HBox(20);
	Label productL=new Label("Product Name"); 
	TextField productField=new TextField("Enter product name");
	HBox productBox=new HBox(20);
	Label priceL=new Label("Set Price for Given Date(s)");
	TextField price=new TextField("Enter in the format XX.XX"); 
	DatePicker priceDate=new DatePicker();
	Button addPriceB=new Button("Add Price");
	HBox priceBox=new HBox(20);
	VBox priceHolder=new VBox(20); 
	Label pictureL=new Label("Upload Pictures"); 
	Button pictureB=new Button("Upload"); 
	FileChooser pictureChooser=new FileChooser(); 
	HBox pictureBox=new HBox(20); 
	VBox pictureHolder=new VBox(20); 
	Button submitB=new Button("Submit");
	Button cancelB=new Button("Cancel");
	HBox buttonHolder=new HBox(20);
	Stage stageRef; 
	
		
	public Add_New_Item_Menu(Stage stage) //need a reference to stage for filechooser 
	{
		stageRef=stage;
		typeOfItem.getItems().addAll("Tops","Shoes","Bottoms","Stationary","Books"); 
		choiceHolder.getChildren().addAll(typeOfItemL,typeOfItem);
		productBox.getChildren().addAll(productL,productField);
		priceBox.getChildren().addAll(priceL,price,priceDate,addPriceB);
		pictureBox.getChildren().addAll(pictureL,pictureB);
		pictureChooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.jpg","*.png"));
		buttonHolder.getChildren().addAll(submitB,cancelB); 
		menu.getChildren().addAll(menuHeaderL,choiceHolder,productBox,priceBox,priceHolder,pictureBox,pictureHolder,buttonHolder);
		
		menu.setAlignment(Pos.CENTER);choiceHolder.setAlignment(Pos.CENTER);productBox.setAlignment(Pos.CENTER);
		priceHolder.setAlignment(Pos.CENTER);pictureBox.setAlignment(Pos.CENTER);pictureHolder.setAlignment(Pos.CENTER);
		priceBox.setAlignment(Pos.CENTER);buttonHolder.setAlignment(Pos.CENTER);
		
		cancelB.setOnAction(event->{
			clear();			
		});
		
		addPriceB.setOnAction(event->{
			String result="$"+price.getText()+"          "+priceDate.getValue().toString();
			Label addPriceLabel=new Label(result);
			priceHolder.getChildren().addAll(addPriceLabel);
		});
		
		pictureB.setOnAction(event->{
			File file=pictureChooser.showOpenDialog(stageRef);
			Label chosenFile=new Label(file.toString());
			pictureHolder.getChildren().add(chosenFile);
		});
	}
	public void clear()
	{
		productL.setText("Enter product name");
		price.setText("Enter in the format XX.XX");
		priceHolder.getChildren().clear();
		pictureHolder.getChildren().clear();
		
	}
	public VBox getMenu()
	{
		return menu;
	}
}
