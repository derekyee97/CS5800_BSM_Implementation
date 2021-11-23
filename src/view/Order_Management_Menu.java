package view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dataccess.BSM_Hibernate_ConnectionFactory;
import model.entities.Item;

public class Order_Management_Menu 
{
	VBox menu=new VBox(10);
	VBox cartItems=new VBox(20);
	Label nameL=new Label("Name"); 
	Label priceL=new Label("Price"); 
	Label quantityL=new Label("Quanity"); 
	Label idL=new Label("Item ID");
	Label subTotalL=new Label("Sub Total"); 
	Label subTotalPrice;
	Label DiscountL; 
	TextField broncoIDInput=new TextField("Enter Bronco ID");
	Label totalL=new Label("Total"); 
	Label total;
	Label emptyCart=new Label("Cart is Empty");
	Button completeOrder=new Button("Complete Order"); 
	BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
	List<Item> itemList;
	
	
	public void makeMenu()
	{		
		menu.getChildren().clear(); 
		cartItems.getChildren().clear(); 
		if(Shop_Menu.cart.isEmpty()) 
		{
			menu.getChildren().add(emptyCart);
			emptyCart.setAlignment(Pos.CENTER);
		}
		else
		{
			HBox cartLabels=new HBox(50); 
			cartLabels.getChildren().addAll(nameL,idL,priceL,quantityL);			
			itemList=Shop_Menu.actualCart; //converting hashset to list
			for(int i=0;i<itemList.size();i++)
			{
				HBox itemRow=new HBox(50);
				Item curr=itemList.get(i); 
				Label itemName=new Label(curr.getName());
				Label itemID=new Label(Integer.toString(curr.getId()));
				Label itemPrice=new Label(Float.toString(Shop_Menu.prices.get(i)));
				ChoiceBox<Integer> quantity=new ChoiceBox(); 
				quantity.getItems().addAll(1,2,3,4,5,6,7,8,9);
				Button remove=new Button("Remove Item"); 
				itemRow.getChildren().addAll(itemName,itemID,itemPrice,quantity,remove);
				cartItems.getChildren().add(itemRow);		
				
				
				remove.setOnAction(event->{
					int index=0;
					while(curr!=itemList.get(index))
					{
						index++; 
					}
					Shop_Menu.prices.remove(index); //the itemlist size corresponds to price list size 
					Shop_Menu.cart.remove(curr.getId()); 
					Shop_Menu.actualCart.remove(index);
					makeMenu();
				});
			}
			menu.getChildren().add(cartItems);
		}
		
		
		
	}
	public VBox getMenu()
	{
		return menu; 
	}
}
