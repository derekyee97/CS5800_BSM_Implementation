package view;

import java.time.LocalDate;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Bsm_Registration_Menu 
{
	//customer fields
		String firstName,lastName,phone,address,role; 
		LocalDate dateOfBirth;
		int broncoID; 
		
		//student fields 
		LocalDate startDate,gradDate;
		String major,minor;
		
		
		//professor fields 
		String department,office,research; 
	
	VBox registrationMenu=new VBox(20); 
	GridPane customerFields=new GridPane(); 
	GridPane studentFields=new GridPane(); 
	GridPane professorFields=new GridPane();
	Label registrationL=new Label("Registration Form"); 
	Button registrationB=new Button("Register");
	//customer fields 
	Label firstNameL=new Label("First Name"),lastNameL=new Label("Last Name");
	Label dateOfBirthL=new Label("Date of Birth"); 
	Label phoneL=new Label("Phone Number"),addressL=new Label("Address"); 
	Label broncoIDL=new Label("Bronco ID"),roleL=new Label("Role"); 
	TextField firstNameField=new TextField("Enter first name"),lastNameField=new TextField("Enter last name");
	TextField phoneField=new TextField("Enter phone number in (XXX)-XXX-XXX format"),addressField=new TextField("Enter address");
	DatePicker dateOfBirthField=new DatePicker();
	ChoiceBox<String> roleField=new ChoiceBox<>(); 
	//student fields 
	Label startDateL=new Label("Start Date"),gradDateL=new Label("Grad Date");
	Label majorL=new Label("Major"),minorL=new Label("Minor");
	DatePicker startDateField=new DatePicker();	DatePicker gradDateField=new DatePicker();
	TextField majorField=new TextField("Enter Major");	TextField minorField=new TextField("Enter Minor");
	//professor fields 
	Label departmentL=new Label("Department"),officeL=new Label("Office"); 
	Label researchL=new Label("Research");
	TextField departmentField=new TextField("Enter Department");TextField officeField=new TextField("Enter Office");
	TextField researchField=new TextField("Enter Research Area");
	
	public Bsm_Registration_Menu()
	{
		registrationMenu.setAlignment(Pos.CENTER);customerFields.setAlignment(Pos.CENTER);studentFields.setAlignment(Pos.CENTER);professorFields.setAlignment(Pos.CENTER);
		registrationL.setStyle("-fx-font-weight: bold");
		customerFields.add(firstNameL,0, 0);customerFields.add(firstNameField, 1, 0);
		customerFields.add(lastNameL,0, 1);customerFields.add(lastNameField, 1, 1);
		customerFields.add(dateOfBirthL,0, 2);customerFields.add(dateOfBirthField, 1, 2);
		customerFields.add(phoneL,0, 3);customerFields.add(phoneField, 1, 3);
		customerFields.add(addressL,0, 4);customerFields.add(addressField, 1, 4);
		customerFields.add(roleL,0, 5);customerFields.add(roleField, 1, 5);
		
		studentFields.add(startDateL, 0, 0);studentFields.add(startDateField, 1, 0);
		studentFields.add(gradDateL, 0, 1);studentFields.add(gradDateField, 1, 1);
		studentFields.add(majorL, 0, 2);studentFields.add(majorField, 1, 2);
		studentFields.add(minorL, 0, 3);studentFields.add(minorField, 1, 3);
		
		professorFields.add(departmentL, 0, 0);	professorFields.add(departmentField, 1, 0);
		professorFields.add(officeL, 0, 1);	professorFields.add(officeField, 1, 1);
		professorFields.add(researchL, 0, 2);	professorFields.add(researchField, 1, 2);
		
		registrationMenu.getChildren().addAll(registrationL,customerFields,studentFields,registrationB);
		
		roleField.getItems().addAll("Student","Professor");
		
		roleField.setValue("Student");
		
		roleField.setOnAction(event->{   //logic to switch forms based on selection 
			registrationMenu.getChildren().remove(registrationMenu.getChildren().size()-2);  //student-professor fields always second to last in the menu
			if(roleField.getValue().equals("Student"))
			{
				registrationMenu.getChildren().add(registrationMenu.getChildren().size()-1,studentFields);
			}
			else
			{
				registrationMenu.getChildren().add(registrationMenu.getChildren().size()-1,professorFields);
			}
					
		});
	}
	public VBox getMenu()
	{
		return registrationMenu;
	}
}
