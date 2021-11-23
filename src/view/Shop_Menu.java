package view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.jdbc.BinaryStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.dataccess.BSM_Hibernate_ConnectionFactory;
import model.entities.HistoricalPrice;
import model.entities.Item;
import model.entities.ItemPicture;

public class Shop_Menu 
{
	Label storeL=new Label("Store"); 
	HBox menu=new HBox(20); 
	VBox itemOptions=new VBox(20); 
	GridPane displayItemsBox=new GridPane(); 
	VBox itemsHolder=new VBox(20); 
	Label itemsHolderLabel;
	Button shoesB=new Button("Shoes"),topsB=new Button("Tops"),bottomsB=new Button("Bottoms"),stationaryB=new Button("Stationary"),booksB=new Button("Books"); 
	Button backB=new Button("<- Back"); 
	VBox itemDetailView=new VBox(10); 
	ImageView detailViewPic; 
	Label itemName;
	Label detailItemID,detailPrice; 
	//Button addToCartB=new Button("Add To Cart"); 
	Button closeDetailViewB=new Button("Close");
	boolean isActive=false;
	static HashSet<Integer> cart=new HashSet<>();	
	static ArrayList<Item> actualCart=new ArrayList<>(); 
	static ArrayList<Float> prices=new ArrayList<>();
	@SuppressWarnings("deprecation")
	public Shop_Menu()
	{
		BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
		itemOptions.getChildren().addAll(shoesB,topsB,bottomsB,stationaryB,booksB);
		menu.getChildren().add(itemOptions);
		menu.setAlignment(Pos.CENTER);
		displayItemsBox.setAlignment(Pos.CENTER);
		itemOptions.setAlignment(Pos.CENTER);itemsHolder.setAlignment(Pos.CENTER);
				
		
		//SETTING UP TOPS SELECTIONS 	
		topsB.setOnAction(event->{				
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			itemsHolder.getChildren().clear(); 
			itemsHolderLabel=new Label("Tops");
			itemsHolderLabel.setFont(new Font(30));
			itemsHolder.getChildren().addAll(itemsHolderLabel,displayItemsBox,backB); 
			//filling gridPane
			try {
				
				Session session=factory.getSession();
				if(!isActive)
				{
					session.beginTransaction();
					isActive=true;
				}
				Criteria crit=session.createCriteria(Item.class);
				crit.add(Restrictions.eq("type","Tops"));
				List<Item> allTops=crit.list();
				int row=0,column=0;
				for(Item item: allTops)
				{
					Image image; 
					ImageView view;
					Criteria picCriteria=session.createCriteria(ItemPicture.class);
					picCriteria.add(Restrictions.eq("itemID", item.getId()));
					List<ItemPicture> pictures=picCriteria.list();
					if(pictures.size()==0) //no pictures for item 
					{
						String filePath=System.getProperty("user.dir")+"/defaultImages/topIcon.jpg";
						image=new Image(filePath);
						view=new ImageView(image);
					}
					else //item has picture 
					{
						image=new Image(new ByteArrayInputStream(pictures.get(0).getImage())); //selecting first picture
						view=new ImageView(image); 
					}
					view.setFitHeight(60);
					view.setPreserveRatio(true); 
					Button itemButton=new Button(); 
					itemButton.setPrefSize(60,60);
					itemButton.setGraphic(view);
					Button addToCartB=new Button("Add To Cart");
					displayItemsBox.add(itemButton, column, row);
										
					column++;
					if(column%3==0)
					{
						column=0;
						row++;
					}
					if(row%3==0)
					{
						row=0;
					}
					//item icon button functionality 
					itemButton.setOnAction(event1->{
						itemName=new Label(item.getName());
						detailItemID=new Label("ID: "+item.getId());
						detailViewPic=new ImageView(image);
						detailViewPic.setFitHeight(100); 
						detailViewPic.setPreserveRatio(true); 
						//getting price 
						Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);
						priceCriteria.add(Restrictions.eq("itemid", item.getId()));
						List<HistoricalPrice> prices=priceCriteria.list();
						Calendar cal=Calendar.getInstance();  //just want date not time 
						cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
						Date today=cal.getInstance().getTime();
						for(HistoricalPrice historicalPrice: prices)
						{
							if(today.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
							{
								detailPrice=new Label("Price: $"+historicalPrice.getPrice());
								break;
							}
						}
						

						setDetailView();
						itemDetailView.getChildren().add(4,addToCartB);
						itemDetailView.setPadding(new Insets(10,10,10,500));
						menu.getChildren().add(itemDetailView);
					});
					addToCartB.setOnAction(event2->{
						if(!cart.contains(item.getId()))
						{
							prices.add(Float.parseFloat(detailPrice.getText().substring(8)));
							actualCart.add(item);
							cart.add(item.getId());
							Alert success=new Alert(AlertType.CONFIRMATION);
							success.setContentText(itemName.getText()+" added to cart");
							success.show();	
							
						}						
						else
						{
							Alert error=new Alert(AlertType.ERROR);
							error.setContentText(itemName.getText()+" is already in cart");
							error.show();
						}
					});
					
				}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			menu.getChildren().add(itemsHolder);			
		});
		shoesB.setOnAction(event->{
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			itemsHolder.getChildren().clear(); 
			itemsHolderLabel=new Label("Shoes");
			itemsHolderLabel.setFont(new Font(30));
			itemsHolder.getChildren().addAll(itemsHolderLabel,displayItemsBox,backB); 
			//filling gridPane
			try {
				
				Session session=factory.getSession();
				if(!isActive)
				{
					session.beginTransaction();
					isActive=true;
				}
				Criteria crit=session.createCriteria(Item.class);
				crit.add(Restrictions.eq("type","Shoes"));
				List<Item> allTops=crit.list();
				int row=0,column=0;
				for(Item item: allTops)
				{
					Image image; 
					ImageView view;
					Criteria picCriteria=session.createCriteria(ItemPicture.class);
					picCriteria.add(Restrictions.eq("itemID", item.getId()));
					List<ItemPicture> pictures=picCriteria.list();
					if(pictures.size()==0) //no pictures for item 
					{
						String filePath=System.getProperty("user.dir")+"/defaultImages/shoesIcon.jpg";
						image=new Image(filePath);
						view=new ImageView(image);
					}
					else //item has picture 
					{
						image=new Image(new ByteArrayInputStream(pictures.get(0).getImage())); //selecting first picture
						view=new ImageView(image); 
					}
					view.setFitHeight(60);
					view.setPreserveRatio(true); 
					Button itemButton=new Button(); 
					itemButton.setPrefSize(60,60);
					itemButton.setGraphic(view);
					Button addToCartB=new Button("Add to cart");
					displayItemsBox.add(itemButton, column, row);
										
					column++;
					if(column%3==0)
					{
						column=0;
						row++;
					}
					if(row%3==0)
					{
						row=0;
					}
					//item icon button functionality 
					itemButton.setOnAction(event1->{
						itemName=new Label(item.getName());
						detailItemID=new Label("ID: "+item.getId());
						detailViewPic=new ImageView(image);
						detailViewPic.setFitHeight(100); 
						detailViewPic.setPreserveRatio(true); 
						//getting price 
						Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);
						priceCriteria.add(Restrictions.eq("itemid", item.getId()));
						List<HistoricalPrice> prices=priceCriteria.list();
						Calendar cal=Calendar.getInstance();  //just want date not time 
						cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
						Date today=cal.getInstance().getTime();
						for(HistoricalPrice historicalPrice: prices)
						{
							if(today.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
							{
								detailPrice=new Label("Price: $"+historicalPrice.getPrice());
								break;
							}
						}
						

						setDetailView();
						itemDetailView.getChildren().add(4,addToCartB);
						itemDetailView.setPadding(new Insets(10,10,10,500));
						menu.getChildren().add(itemDetailView);
					});
					addToCartB.setOnAction(event2->{
						if(!cart.contains(item.getId()))
						{
							prices.add(Float.parseFloat(detailPrice.getText().substring(8)));
							actualCart.add(item);
							cart.add(item.getId());
							Alert success=new Alert(AlertType.CONFIRMATION);
							success.setContentText(itemName.getText()+" added to cart");
							success.show();	
							
						}						
						else
						{
							Alert error=new Alert(AlertType.ERROR);
							error.setContentText(itemName.getText()+" is already in cart");
							error.show();
						}
					});
					
				}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			menu.getChildren().add(itemsHolder);
		});
		bottomsB.setOnAction(event->{
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			itemsHolder.getChildren().clear(); 
			itemsHolderLabel=new Label("Bottoms");
			itemsHolderLabel.setFont(new Font(30));
			itemsHolder.getChildren().addAll(itemsHolderLabel,displayItemsBox,backB); 
			//filling gridPane
			try {
				
				Session session=factory.getSession();
				if(!isActive)
				{
					session.beginTransaction();
					isActive=true;
				}
				Criteria crit=session.createCriteria(Item.class);
				crit.add(Restrictions.eq("type","Bottoms"));
				List<Item> allTops=crit.list();
				int row=0,column=0;
				for(Item item: allTops)
				{
					Image image; 
					ImageView view;
					Criteria picCriteria=session.createCriteria(ItemPicture.class);
					picCriteria.add(Restrictions.eq("itemID", item.getId()));
					List<ItemPicture> pictures=picCriteria.list();
					if(pictures.size()==0) //no pictures for item 
					{
						String filePath=System.getProperty("user.dir")+"/defaultImages/bottomsIcon.png";
						image=new Image(filePath);
						view=new ImageView(image);
					}
					else //item has picture 
					{
						image=new Image(new ByteArrayInputStream(pictures.get(0).getImage())); //selecting first picture
						view=new ImageView(image); 
					}
					view.setFitHeight(60);
					view.setPreserveRatio(true); 
					Button itemButton=new Button(); 
					itemButton.setPrefSize(60,60);
					itemButton.setGraphic(view);
					Button addToCartB=new Button("Add to cart");
					displayItemsBox.add(itemButton, column, row);
										
					column++;
					if(column%3==0)
					{
						column=0;
						row++;
					}
					if(row%3==0)
					{
						row=0;
					}
					//item icon button functionality 
					itemButton.setOnAction(event1->{
						itemName=new Label(item.getName());
						detailItemID=new Label("ID: "+item.getId());
						detailViewPic=new ImageView(image);
						detailViewPic.setFitHeight(100); 
						detailViewPic.setPreserveRatio(true); 
						//getting price 
						Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);
						priceCriteria.add(Restrictions.eq("itemid", item.getId()));
						List<HistoricalPrice> prices=priceCriteria.list();
						Calendar cal=Calendar.getInstance();  //just want date not time 
						cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
						Date today=cal.getInstance().getTime();
						for(HistoricalPrice historicalPrice: prices)
						{
							if(today.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
							{
								detailPrice=new Label("Price: $"+historicalPrice.getPrice());
								break;
							}
						}
						

						setDetailView();
						itemDetailView.getChildren().add(4,addToCartB);
						itemDetailView.setPadding(new Insets(10,10,10,500));
						menu.getChildren().add(itemDetailView);
					});
					addToCartB.setOnAction(event2->{
						if(!cart.contains(item.getId()))
						{
							prices.add(Float.parseFloat(detailPrice.getText().substring(8)));
							actualCart.add(item);
							cart.add(item.getId());
							Alert success=new Alert(AlertType.CONFIRMATION);
							success.setContentText(itemName.getText()+" added to cart");
							success.show();	
							
						}						
						else
						{
							Alert error=new Alert(AlertType.ERROR);
							error.setContentText(itemName.getText()+" is already in cart");
							error.show();
						}
					});
					
				}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			menu.getChildren().add(itemsHolder);
		});
		
		stationaryB.setOnAction(event->{
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			itemsHolder.getChildren().clear(); 
			itemsHolderLabel=new Label("Stationary");
			itemsHolderLabel.setFont(new Font(30));
			itemsHolder.getChildren().addAll(itemsHolderLabel,displayItemsBox,backB); 
			//filling gridPane
			try {
				
				Session session=factory.getSession();
				if(!isActive)
				{
					session.beginTransaction();
					isActive=true;
				}
				Criteria crit=session.createCriteria(Item.class);
				crit.add(Restrictions.eq("type","Stationary"));
				List<Item> allTops=crit.list();
				int row=0,column=0;
				for(Item item: allTops)
				{
					Image image; 
					ImageView view;
					Criteria picCriteria=session.createCriteria(ItemPicture.class);
					picCriteria.add(Restrictions.eq("itemID", item.getId()));
					List<ItemPicture> pictures=picCriteria.list();
					if(pictures.size()==0) //no pictures for item 
					{
						String filePath=System.getProperty("user.dir")+"/defaultImages/stationaryIcon.png";
						image=new Image(filePath);
						view=new ImageView(image);
					}
					else //item has picture 
					{
						image=new Image(new ByteArrayInputStream(pictures.get(0).getImage())); //selecting first picture
						view=new ImageView(image); 
					}
					view.setFitHeight(60);
					view.setPreserveRatio(true); 
					Button itemButton=new Button(); 
					itemButton.setPrefSize(60,60);
					itemButton.setGraphic(view);
					Button addToCartB=new Button("Add to cart");
					displayItemsBox.add(itemButton, column, row);
										
					column++;
					if(column%3==0)
					{
						column=0;
						row++;
					}
					if(row%3==0)
					{
						row=0;
					}
					//item icon button functionality 
					itemButton.setOnAction(event1->{
						itemName=new Label(item.getName());
						detailItemID=new Label("ID: "+item.getId());
						detailViewPic=new ImageView(image);
						detailViewPic.setFitHeight(100); 
						detailViewPic.setPreserveRatio(true); 
						//getting price 
						Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);
						priceCriteria.add(Restrictions.eq("itemid", item.getId()));
						List<HistoricalPrice> prices=priceCriteria.list();
						Calendar cal=Calendar.getInstance();  //just want date not time 
						cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
						Date today=cal.getInstance().getTime();
						for(HistoricalPrice historicalPrice: prices)
						{
							if(today.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
							{
								detailPrice=new Label("Price: $"+historicalPrice.getPrice());
								break;
							}
						}
						

						setDetailView();
						itemDetailView.getChildren().add(4,addToCartB);  
						itemDetailView.setPadding(new Insets(10,10,10,500));
						menu.getChildren().add(itemDetailView);
					});
					addToCartB.setOnAction(event2->{
						if(!cart.contains(item.getId()))
						{
							prices.add(Float.parseFloat(detailPrice.getText().substring(8)));
							actualCart.add(item);
							cart.add(item.getId());
							Alert success=new Alert(AlertType.CONFIRMATION);
							success.setContentText(itemName.getText()+" added to cart");
							success.show();	
							
						}						
						else
						{
							Alert error=new Alert(AlertType.ERROR);
							error.setContentText(itemName.getText()+" is already in cart");
							error.show();
						}
						
					});
					
				}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			menu.getChildren().add(itemsHolder);
		});
		
		booksB.setOnAction(event->{
			displayItemsBox.getChildren().clear(); 
			menu.getChildren().clear(); 
			itemsHolder.getChildren().clear(); 
			itemsHolderLabel=new Label("Books");
			itemsHolderLabel.setFont(new Font(30));
			itemsHolder.getChildren().addAll(itemsHolderLabel,displayItemsBox,backB); 
			//filling gridPane
			try {
				Session session=factory.getSession();
				if(!isActive)
				{
					session.beginTransaction();
					isActive=true;
				}
				Criteria crit=session.createCriteria(Item.class);
				crit.add(Restrictions.eq("type","Books"));
				List<Item> allTops=crit.list();
				int row=0,column=0;
				for(Item item: allTops)
				{
					Image image; 
					ImageView view;
					Criteria picCriteria=session.createCriteria(ItemPicture.class);
					picCriteria.add(Restrictions.eq("itemID", item.getId()));
					List<ItemPicture> pictures=picCriteria.list();
					if(pictures.size()==0) //no pictures for item 
					{
						String filePath=System.getProperty("user.dir")+"/defaultImages/bookIcon.jpg";
						image=new Image(filePath);
						view=new ImageView(image);
					}
					else //item has picture 
					{
						image=new Image(new ByteArrayInputStream(pictures.get(0).getImage())); //selecting first picture
						view=new ImageView(image); 
					}
					view.setFitHeight(60);
					view.setPreserveRatio(true); 
					Button itemButton=new Button(); 
					itemButton.setPrefSize(60,60);
					itemButton.setGraphic(view);
					displayItemsBox.add(itemButton, column, row);
					Button addToCartB=new Button("Add To Cart");					
					column++;
					if(column%3==0)
					{
						column=0;
						row++;
					}
					if(row%3==0)
					{
						row=0;
					}
					//item icon button functionality 
					itemButton.setOnAction(event1->{
						itemName=new Label(item.getName());
						detailItemID=new Label("ID: "+item.getId());
						detailViewPic=new ImageView(image);
						detailViewPic.setFitHeight(100); 
						detailViewPic.setPreserveRatio(true); 
						//getting price 
						Criteria priceCriteria=session.createCriteria(HistoricalPrice.class);
						priceCriteria.add(Restrictions.eq("itemid", item.getId()));
						List<HistoricalPrice> prices=priceCriteria.list();
						Calendar cal=Calendar.getInstance();  //just want date not time 
						cal.set(Calendar.HOUR_OF_DAY,0);cal.set(Calendar.MINUTE,0);cal.set(Calendar.SECOND,0);cal.set(Calendar.MILLISECOND,0);
						Date today=cal.getInstance().getTime();
						for(HistoricalPrice historicalPrice: prices)
						{
							if(today.compareTo(historicalPrice.getStart())>=0) //0 if same, 1 if after 
							{
								detailPrice=new Label("Price: $"+historicalPrice.getPrice());
								break;
							}
						}
						

						setDetailView();
						itemDetailView.getChildren().add(4,addToCartB);   
						itemDetailView.setPadding(new Insets(10,10,10,500));
						menu.getChildren().add(itemDetailView);
					});
					addToCartB.setOnAction(event2->{
						
						if(!cart.contains(item.getId()))
						{
							prices.add(Float.parseFloat(detailPrice.getText().substring(8)));
							actualCart.add(item);
							cart.add(item.getId());
							Alert success=new Alert(AlertType.CONFIRMATION);
							success.setContentText(itemName.getText()+" added to cart");
							success.show();	
							
						}						
						else
						{
							Alert error=new Alert(AlertType.ERROR);
							error.setContentText(itemName.getText()+" is already in cart");
							error.show();
						}									
					});
					
				}
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			menu.getChildren().add(itemsHolder);
		});
		backB.setOnAction(event->{
			menu.getChildren().clear();
			menu.getChildren().add(itemOptions);
			
		});
		closeDetailViewB.setOnAction(event->{
			menu.getChildren().remove(menu.getChildren().size()-1); 
		});
	}
	public HBox getMenu()
	{
		return menu; 
	}
	private void setDetailView()
	{
		itemDetailView.getChildren().clear(); //clear detail view 
		itemDetailView.getChildren().addAll(detailViewPic,itemName,detailItemID,detailPrice,closeDetailViewB);
		itemDetailView.setAlignment(Pos.CENTER);
	}	
}
