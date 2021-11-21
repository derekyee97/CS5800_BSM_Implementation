package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Shop_Menu 
{
	Label storeL=new Label("Store"); 
	HBox menu=new HBox(20); 
	VBox itemOptions=new VBox(20); 
	GridPane displayItemsBox=new GridPane(); 
	VBox itemsHolder=new VBox(20); 
	Button shoesB=new Button("Shoes"),topsB=new Button("Tops"),bottomsB=new Button("Bottoms"),stationaryB=new Button("Stationary"),booksB=new Button("Books"); 
	Button backB=new Button("<- Back"); 
		
	public Shop_Menu()
	{
		itemOptions.getChildren().addAll(shoesB,topsB,bottomsB,stationaryB,booksB);
		menu.getChildren().add(itemOptions);
		menu.setAlignment(Pos.CENTER);
		itemOptions.setAlignment(Pos.CENTER);
		itemsHolder.getChildren().addAll(displayItemsBox,backB); 
		topsB.setOnAction(event->{
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			menu.getChildren().add(itemsHolder);
			
		});
		backB.setOnAction(event->{
			menu.getChildren().clear();
			menu.getChildren().add(itemOptions);
			
		});
		
	}
	public HBox getMenu()
	{
		return menu; 
	}
}
