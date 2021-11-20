package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.entities.Customer;
import model.entities.Professor;
import model.entities.Student;

public class BSM_Hibernate_ConnectionFactory 
{
	SessionFactory factory = new Configuration().
            configure("hibernate.cfg.xml")
            .addAnnotatedClass(Customer.class)
            .addAnnotatedClass(Student.class)
            .addAnnotatedClass(Professor.class)
            .buildSessionFactory();

	public Session getSession() throws ClassNotFoundException, SQLException
	{
		return factory.getCurrentSession();
	}
	
	
}
