<<<<<<< Updated upstream
package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.entities.Customer;
import model.entities.HistoricalPrice;
import model.entities.Item;
import model.entities.ItemPicture;
import model.entities.Professor;
import model.entities.Student;

public class BSM_Hibernate_ConnectionFactory 
{
	SessionFactory factory = new Configuration().
            configure("hibernate.cfg.xml")
            .addAnnotatedClass(Customer.class)
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Professor.class)
            .addAnnotatedClass(Item.class)
            .addAnnotatedClass(ItemPicture.class)
            .addAnnotatedClass(HistoricalPrice.class)
            .buildSessionFactory();

	public Session getSession() throws ClassNotFoundException, SQLException
	{
		return factory.getCurrentSession();
	}
	
	
}
=======
package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.entities.Customer;
import model.entities.HistoricalPrice;
import model.entities.Item;
import model.entities.ItemPicture;
import model.entities.Order;
import model.entities.OrderItems;
import model.entities.Professor;
import model.entities.Student;

public class BSM_Hibernate_ConnectionFactory 
{
	SessionFactory factory = new Configuration().
            configure("hibernate.cfg.xml")
            .addAnnotatedClass(Customer.class)
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Professor.class)
            .addAnnotatedClass(Item.class)
            .addAnnotatedClass(ItemPicture.class)
            .addAnnotatedClass(HistoricalPrice.class)
            .addAnnotatedClass(Order.class)
            .addAnnotatedClass(OrderItems.class)
            .buildSessionFactory();

	public Session getSession() throws ClassNotFoundException, SQLException
	{
		return factory.getCurrentSession();
	}
	
	
}
>>>>>>> Stashed changes
