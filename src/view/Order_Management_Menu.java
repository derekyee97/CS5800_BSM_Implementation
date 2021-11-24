package view;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dataccess.BSM_Hibernate_ConnectionFactory;
import model.entities.Customer;
import model.entities.HistoricalPrice;
import model.entities.Item;
import model.entities.Order;
import model.entities.OrderItems;
import model.entities.Professor;
import model.entities.Student;

public class Order_Management_Menu 
{
	VBox menu=new VBox(30);
	VBox cartItems=new VBox(20);
	Label nameL=new Label("Name"); 
	Label priceL=new Label("Price"); 
	Label quantityL=new Label("Quanity"); 
	Label idL=new Label("Item ID");
	Label removeL=new Label("Remove");
	Label subTotalL=new Label("Sub Total"); 
	Label subTotalPrice=new Label("");
	HBox subTotalHolder=new HBox(40);
	Label DiscountL=new Label("Discount"); 
	Label header=new Label("CART");
	TextField broncoIDInput=new TextField("Enter Bronco ID");
	Label totalL=new Label("Total"),totalAmountL=new Label("");
	Label emptyCart=new Label("Cart is Empty");
	Button completeOrder=new Button("Complete Order"); 
	HBox discountHolder=new HBox(40);
	HBox totalHolder=new HBox(40);
	Label discountPriceL;
	GridPane itemsCartView=new GridPane(); 
	BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
	List<Item> itemList;
	float subTotal;
	float discountedTotal;
	
	public Order_Management_Menu()
	{
		discountHolder.getChildren().addAll(DiscountL,broncoIDInput);
		subTotalHolder.getChildren().addAll(subTotalL,subTotalPrice);
		totalHolder.getChildren().addAll(totalL,totalAmountL); 
		subTotalHolder.setAlignment(Pos.CENTER);		
		discountHolder.setAlignment(Pos.CENTER);
		totalHolder.setAlignment(Pos.CENTER);
	}
	public void makeMenu()
	{		
		menu.getChildren().clear(); 
		cartItems.getChildren().clear(); 
		itemsCartView.getChildren().clear(); 
		subTotalHolder.getChildren().clear();
		itemsCartView.setHgap(10);itemsCartView.setVgap(10);itemsCartView.setAlignment(Pos.CENTER);
		if(Shop_Menu.cart.isEmpty()) 
		{
			menu.getChildren().add(emptyCart);
			emptyCart.setAlignment(Pos.CENTER);
		}		
		else
		{
			HBox cartLabels=new HBox(50); 
			cartLabels.getChildren().addAll(nameL,idL,priceL,quantityL);			
			itemList=Shop_Menu.actualCart;
			int row=1; 
			itemsCartView.add(nameL, 0, 0);itemsCartView.add(idL, 1, 0);itemsCartView.add(priceL, 2, 0);itemsCartView.add(quantityL, 3, 0);itemsCartView.add(removeL, 4, 0);			
			for(int i=0;i<itemList.size();i++)
			{				
				Item curr=itemList.get(i); 
				Label itemName=new Label(curr.getName());
				Label itemID=new Label(Integer.toString(curr.getId()));
				Label itemPrice=new Label(Float.toString(Shop_Menu.prices.get(i)));
				ChoiceBox<Integer> quantity=new ChoiceBox(); 
				quantity.getItems().addAll(1,2,3,4,5,6,7,8,9);
				quantity.setValue(1);
				Button remove=new Button("Remove Item"); 			
				itemsCartView.add(itemName, 0, row);itemsCartView.add(itemID, 1, row);itemsCartView.add(itemPrice,2,row);itemsCartView.add(quantity, 3, row);itemsCartView.add(remove, 4, row);
				row++;
				subTotal+=Shop_Menu.prices.get(i); //adding initial prices to subTotal
				
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
					calcPrice();
				});
				quantity.setOnAction(event->{ //recalculate price 
					calcPrice(); 
					
				});
			}
			DecimalFormat df=new DecimalFormat("###.##");
			subTotalPrice=new Label("$"+df.format(subTotal));
			
			subTotalHolder.getChildren().addAll(subTotalL,subTotalPrice);
			menu.getChildren().addAll(header,itemsCartView,subTotalHolder,discountHolder,completeOrder);
			calcPrice(); 
			completeOrder.setOnAction(event->{
				BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
				try {
					Session session=factory.getSession();
					session.beginTransaction();
					Customer customer=session.get(Customer.class,Integer.parseInt(broncoIDInput.getText()));
					if(customer==null) //customer does not exist
					{
						Alert error=new Alert(AlertType.ERROR);
						error.setContentText("USER DOES NOT EXIST");
						error.show();
					}
					else
					{
						float discount;
						boolean isStudent=false;
						//applying discount 
						if(session.get(Professor.class,customer.getID())==null)
						{
							discount=(float) 0.9;
							isStudent=true;
						}
						else
						{
							discount=(float) 0.8;
						}
						menu.getChildren().remove(3); 
						discountHolder.getChildren().remove(1); //remove textfield 
						discountedTotal=subTotal*discount;  //apply discount	
						String discountPriceText="";
						if(isStudent)
						{
							discountPriceText+="-10% (STUDENT DISCOUNT)";
						}
						else
						{
							discountPriceText+="-20% (Professor DISCOUNT)"; 
						}												
						discountPriceL=new Label(discountPriceText);
						discountHolder.getChildren().add(discountPriceL);
						menu.getChildren().add(3,discountHolder);
						menu.getChildren().remove(4); //delete complete order button 
						totalAmountL=new Label("$"+df.format(discountedTotal));
						totalHolder.getChildren().remove(1);
						totalHolder.getChildren().add(totalAmountL);
						menu.getChildren().add(totalHolder);
						//creating order
						int broncoId=customer.getID();
						Date currDate=new Date(System.currentTimeMillis());
						Timestamp time=new Timestamp(System.currentTimeMillis());
						String status="Counter";
						Order newOrder=new Order(broncoId,currDate,time,status,subTotal,discountedTotal);
						session.save(newOrder);session.getTransaction().commit(); //save order into database
						
						//creating order items 
						int priceIndex=7;
						int quantityIndex=8;
						int itemID=6;
						List<Node>cartItems=itemsCartView.getChildren();
						session=factory.getSession(); 
						session.beginTransaction(); 
						while(itemID<=cartItems.size()) 
						{
							Label itemIDL=(Label)cartItems.get(itemID);
							int historyPriceID=0;
							Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);			
							priceCriteria.add(Restrictions.eq("itemid", Integer.parseInt(itemIDL.getText())));
							List<HistoricalPrice> prices=priceCriteria.list();							
							for(HistoricalPrice historicalPrice: prices)
							{
								if(currDate.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
								{
									historyPriceID=historicalPrice.getPriceID();
									break;
								}
							}
							float orderItemPrice=Float.parseFloat(((Label)cartItems.get(priceIndex)).getText());
							ChoiceBox num=(ChoiceBox)cartItems.get(quantityIndex); 
							int quantity=(Integer)num.getValue(); 
							OrderItems newOrderItem=new OrderItems(newOrder.getOrderID(),broncoId,Integer.parseInt(itemIDL.getText()),orderItemPrice,historyPriceID,quantity);
							session.save(newOrderItem);
							priceIndex+=5;quantityIndex+=5;itemID+=5;
							
						}
						session.getTransaction().commit();			
						
						
					}
									
					
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			});
		
		}
		
		
		
	}
	private void calcPrice()
	{
		List<Node> cartItems=itemsCartView.getChildren(); 
		int priceIndex=7;
		int quantityIndex=8;
		menu.getChildren().remove(2); //remove subtotal holder
		subTotalHolder.getChildren().remove(1); //remove prev calculated subtotal 
		subTotal=0; 
		while(priceIndex<=cartItems.size()) //going through gridpane in increments of 5 
		{
			Label priceOfItem=(Label)cartItems.get(priceIndex);
			ChoiceBox num=(ChoiceBox)cartItems.get(quantityIndex); 
			subTotal+=Float.parseFloat(priceOfItem.getText())*((Integer)num.getValue()); 
			priceIndex+=5;
			quantityIndex+=5;
		}
		DecimalFormat df=new DecimalFormat("###.##");
		subTotalPrice=new Label("$"+df.format(subTotal));
		subTotalHolder.getChildren().add(subTotalPrice);
		menu.getChildren().add(2,subTotalHolder);
		
	}
	public VBox getMenu()
	{
		return menu; 
	}
}
