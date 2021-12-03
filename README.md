# CS5800_BSM_Implementation
----------------------------------------

## Packages 

### Model DataAccess 
	This package holds our connection factory that connects to our databse when we create an instance of it. 
	
###Model Entities 
	This package contains all of the entities that we used for our project. This includes things like Customer, Item, Order, etc along with their corresponding mappings for hibernate. 
	-Customer entity is used to create a customer 
	-Professor entity is used to create a professor using the BroncoID from customer 
	-Student entity is used to create a student using the BroncoID from customer 
	-Order entity is used to keep track of orders from costumers 
	-OrderItems entity is used to keep track of what was inside an order as well as important information like quantity, current price of item, etc 
	-Item entity is used to keep track of what item is allowed to show up in the store, and creating new items 
	-Item Picture entity is used to keep track of what picture belongs to what item 
	-Historical Price Entity is used to keep track of what price an item should be at a given date
	
###Model View 
	This package contains all the GUI and functionality implementation for our offline application. The corresponding names of the files give the user an idea of what page they are looking at, as well as its functionality. We run the "BSM_Main_Menu" class file to start up our application 
	- Add_New_Item_Menu delivers menu to add new item as well as creating the connnection to our database and adding the item for us 
	- BSM_Registration_Menu delivers menu to add new customer whether they be a professor or student
	- Generate_Report_Menu will deliver menu to generate report as well as filter for customer and date conditions. It also has functionality to look up items in an order given the orderID is provided.
	-Order_Management_Menu provides the checkout page of our application, and functionality to complete an order 
	- Shop_Menu provides the shop GUI that will load items that are in the database as well as their corresponding price given the date 
	- BSM_Main_Menu will provide the main menu that will create instances of all of our other pages so the user can interact with our application as needed 
	
	
