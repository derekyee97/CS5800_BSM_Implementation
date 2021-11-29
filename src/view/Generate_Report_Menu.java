package view;

import java.awt.ContainerOrderFocusTraversalPolicy;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.dataccess.BSM_Hibernate_ConnectionFactory;
import model.entities.Customer;
import model.entities.Item;
import model.entities.Order;
import model.entities.OrderItems;

public class Generate_Report_Menu 
{
	VBox menu=new VBox(30); 
	Label generateReportsL=new Label("Generate Reports"); 
	Label filterL=new Label("Filters");
	Button filterB=new Button("Filter"); Button resetB=new Button("Reset");
	GridPane filterHolder=new GridPane();	 
	Label startRangeL=new Label("Start"); Label endRangeL=new Label("End Range");
	Label customerL=new Label("Customers");
	ChoiceBox<Customer> customerChoiceBox=new ChoiceBox(); 
	DatePicker startRange=new DatePicker(); DatePicker endRange=new DatePicker(); 
	BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
	GridPane orderInformation=new GridPane();
	Label nameL=new Label("Bronco ID"),orderIDL=new Label("Order ID"),dateL=new Label("Date"),orderTotalL=new Label("Order Total");
	float total; 
	GridPane displayItems=new GridPane(); 
	Label itemNameL=new Label("Item Name"),itemPriceL=new Label("Item Price"),quantityL=new Label("Quantity");	
	Button searchItemsInOrderB=new Button("Show Items"); 
	TextField searchField=new TextField("enter order ID");
	Label getItemsInOrderL=new Label("Show items in order");
	HBox getItemsFunctionsHolder=new HBox(20);
	VBox getItemsDisplay=new VBox(20); 
	
	
	HBox totalHolder=new HBox(30);
	Label totalL=new Label("Total Revenue");Label totalValueL=new Label("");
	public Generate_Report_Menu()
	{
		try
		{
			Session session=factory.getSession();
			session.beginTransaction();
			List<Customer> customersList=getCustomers(session); 
			
			//loaded all customers filter 
			for(Customer customer: customersList)
			{
				customerChoiceBox.getItems().add(customer);
			}
			//creating filter section
			customerChoiceBox.setPrefSize(60,30);
			startRange.setPrefSize(60,30);endRange.setPrefSize(60,30);
			filterHolder.add(filterL, 0, 0);
			filterHolder.add(customerL, 0,1);filterHolder.add(customerChoiceBox, 0,2);
			filterHolder.add(startRangeL, 1, 1);filterHolder.add(startRange, 1, 2);
			filterHolder.add(endRangeL, 2,1);filterHolder.add(endRange,2,2);
			filterHolder.add(filterB, 1,3);filterHolder.add(resetB, 2,3);
			filterHolder.setHgap(10);filterHolder.setVgap(10);		
			
			//creating order section
			List<Order> ordersList=new ArrayList<>();
			List<String> customerNames=new ArrayList<>();
			for(Customer customer: customersList) //populating orderlist with all orders 
			{
				List<Order> customerOrders=getOrders(customer.getID(),session);
				ordersList.addAll(customerOrders);
			}
			createOrderInformation(ordersList); 
			orderInformation.setHgap(15);orderInformation.setVgap(10);
			
			totalHolder.getChildren().addAll(totalL,totalValueL);
			
			getItemsFunctionsHolder.getChildren().addAll(getItemsInOrderL,searchField,searchItemsInOrderB);
			searchItemsInOrderB.setOnAction(event->{
				List<OrderItems> itemsInOrder=getItems(Integer.parseInt(searchField.getText()),session);
				createItemsInformation(itemsInOrder,session);
			});
			
			filterB.setOnAction(event->{
				Customer selectedCustomer=customerChoiceBox.getValue(); 
				List<Order> customerOrders=getOrders(selectedCustomer.getID(),session);
				if(startRange.getValue()==null && endRange.getValue()==null)
				{
					if(customerOrders.isEmpty())
					{
						Alert errorAlert=new Alert(AlertType.ERROR);
						errorAlert.setContentText("No orders for this customer within the given date range. Please update the filters and try again");
						errorAlert.show();
					}					
					createOrderInformation(customerOrders);
					
				}
				else
				{
					Date startDate=Date.from(startRange.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
					Date endDate=Date.from(endRange.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());					
					List<Order>filteredOrders=new ArrayList<>(); 					
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
//					int year=startDate.getYear()+1900; 
//					int month=startDate.getMonth()+1;
//					String monthString="";
//					if(month<10)
//					{
//						monthString+="0"; 
//						monthString+=month;
//					}
//					else
//					{
//						monthString+=month;
//					}
//					int day=startDate.getDate(); 
//					String dayString="";
//					if(day<10)
//					{
//						dayString+="0";
//						dayString+=day; 
//					}
//					else
//					{
//						dayString+=day;
//					}
//					String startString=year+"-"+monthString+"-"+dayString;
//					try {
//						startDate=sdf.parse(startString);
//						System.out.println("YES");
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				//	Date start=sdf.parse(startDate.toString().substring(0,9));
					for(Order order: customerOrders)
					{
						
						if(order.getDate().after(startDate))
						{
							
							if(order.getDate().before(endDate))
							{
								filteredOrders.add(order);
								
							}
						}
					
					}
					if(filteredOrders.size()==0)
					{
						Alert errorAlert=new Alert(AlertType.ERROR);
						errorAlert.setContentText("No orders for this customer within the given date range. Please update the filters and try again");
						errorAlert.show();
					}					
					createOrderInformation(filteredOrders);
					
				}
			});
			
			resetB.setOnAction(event->{
				createOrderInformation(ordersList);
			});
			
			displayItems.setHgap(15);displayItems.setVgap(15);
			menu.getChildren().addAll(generateReportsL,filterHolder,orderInformation,totalHolder,getItemsFunctionsHolder);
			menu.setAlignment(Pos.CENTER);orderInformation.setAlignment(Pos.CENTER);filterHolder.setAlignment(Pos.CENTER);totalHolder.setAlignment(Pos.CENTER);
			getItemsFunctionsHolder.setAlignment(Pos.CENTER);displayItems.setAlignment(Pos.CENTER);
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private List<Customer> getCustomers(Session session)
	{
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<Customer> criteriaQuery=criteria.createQuery(Customer.class);		
		criteriaQuery.from(Customer.class);
		List<Customer> results=session.createQuery(criteriaQuery).getResultList();
		return results;
	}
	private List<Order> getOrders(int customerID,Session session)
	{
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery=criteria.createQuery(Order.class);		
		Root<Order> currOrder=criteriaQuery.from(Order.class);
		criteriaQuery.where(criteria.equal(currOrder.get("broncoID"), customerID));
		List<Order> results=session.createQuery(criteriaQuery).getResultList();
		return results;
	}
	private void createOrderInformation(List<Order> ordersList)
	{
		orderInformation.getChildren().clear();
		total=0;
		orderInformation.add(nameL, 0, 0);orderInformation.add(orderIDL, 1, 0);orderInformation.add(dateL, 2, 0);orderInformation.add(orderTotalL,3, 0);
		int row=1;
		DecimalFormat df=new DecimalFormat("###.##");	
		for(Order order: ordersList)
		{
			Label broncoID=new Label(Integer.toString(order.getBroncoID()));
			Label orderID=new Label(Integer.toString(order.getOrderID()));
			Date date=order.getDate();			
			Label dateLabel=new Label(date.toString());
			float orderTotal=order.getDiscountPrice(); //how much the user paid after discount 
			total+=orderTotal; //update total 			
			Label orderTotalL=new Label("$"+df.format(orderTotal));
			orderInformation.add(broncoID, 0, row);
			orderInformation.add(orderID, 1, row);
			orderInformation.add(dateLabel, 2, row);
			orderInformation.add(orderTotalL, 3, row);
			row++;
		}
		totalValueL.setText("$"+df.format(total));
	}
	private List<OrderItems> getItems(int orderID,Session session)
	{
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<OrderItems> criteriaQuery=criteria.createQuery(OrderItems.class);		
		Root<OrderItems> currOrderItem=criteriaQuery.from(OrderItems.class);
		criteriaQuery.where(criteria.equal(currOrderItem.get("orderID"), orderID));
		List<OrderItems> results=session.createQuery(criteriaQuery).getResultList();
		return results;
	}
	private String getItemName(int itemID,Session session)
	{
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<Item> criteriaQuery=criteria.createQuery(Item.class);		
		Root<Item> currOrderItem=criteriaQuery.from(Item.class);
		criteriaQuery.where(criteria.equal(currOrderItem.get("itemid"), itemID));
		List<Item> results=session.createQuery(criteriaQuery).getResultList();
		return results.get(0).getName();
	}
	private void createItemsInformation(List<OrderItems> orderItems,Session session)
	{		
		displayItems.getChildren().clear();
		displayItems.add(itemNameL, 0, 0);displayItems.add(itemPriceL, 1, 0);displayItems.add(quantityL, 2, 0);
		DecimalFormat df=new DecimalFormat("###.##");
		int row=1;
		for(OrderItems orderItem: orderItems)
		{
			float price=orderItem.getPrice();
			int quantity=orderItem.getQuantity();
			String itemName=getItemName(orderItem.getItemID(),session);
			displayItems.add(new Label(itemName), 0, row);
			displayItems.add(new Label(Integer.toString(quantity)), 2, row);
			displayItems.add(new Label("$"+df.format(price)), 1, row);
			row++;
		}
		menu.getChildren().add(displayItems);
	}
	public VBox getMenu()
	{
		return menu;
	}
}
