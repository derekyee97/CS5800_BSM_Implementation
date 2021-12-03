package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.dataccess.BSM_Hibernate_ConnectionFactory;
import model.entities.HistoricalPrice;
import model.entities.Item;
import model.entities.ItemPicture;
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
	List<File> images=new ArrayList<>();
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
			String result="$"+price.getText()+";"+priceDate.getValue().toString();
			Label addPriceLabel=new Label(result);
			priceHolder.getChildren().addAll(addPriceLabel);
		});
		
		pictureB.setOnAction(event->{
			File file=pictureChooser.showOpenDialog(stageRef);
			Label chosenFile=new Label(file.toString());
			pictureHolder.getChildren().add(chosenFile);
			images.add(file);
		});
	
		submitB.setOnAction(event->{
			BSM_Hibernate_ConnectionFactory factory=new BSM_Hibernate_ConnectionFactory();
			try {
				Session session=factory.getSession();
				//adding item 
				Item addedItem=new Item(productField.getText(),typeOfItem.getValue());
				session.beginTransaction();
				session.save(addedItem);
				session.getTransaction().commit();	
				
				//adding all dates 
				session=factory.getSession();
				session.beginTransaction();
				List<Node> priceList=priceHolder.getChildren();
				for(Node node: priceList)
				{
					String[] parts=((Label)node).getText().split(";"); //each label has 2 parts: price and date 
					float price=Float.parseFloat(parts[0].substring(1));   //substring to take out the $ 
					System.out.println("HEEERE "+parts.length);
					 
					LocalDate startDate=LocalDate.parse(parts[1]);
					HistoricalPrice priceOption=new HistoricalPrice(addedItem.getId(),price,Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
					session.save(priceOption);
				}
				session.getTransaction().commit();
				
				//adding all pictures 
				session=factory.getSession(); 
				session.beginTransaction(); 
				List<Node> pictureList=priceHolder.getChildren(); 
				for(File img: images)
				{
					
					byte[] bFile=new byte[(int)img.length()];
					try {
						FileInputStream imgStream=new FileInputStream(img);
						imgStream.read(bFile);
						imgStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ItemPicture picture=new ItemPicture(addedItem.getId(),bFile);
					session.save(picture);
				}
				session.getTransaction().commit();
				
				Alert confirm=new Alert(AlertType.CONFIRMATION);
				confirm.setContentText(productField.getText()+" added successfully!");
				confirm.show();
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});
		
	}
	public void clear()
	{
		productL.setText("Enter product name");
		price.setText("Enter in the format XX.XX");
		priceHolder.getChildren().clear();
		pictureHolder.getChildren().clear();
		images.clear();
		
	}
	public VBox getMenu()
	{
		return menu;
	}
}
