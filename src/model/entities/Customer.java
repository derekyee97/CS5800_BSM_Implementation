package model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "customer")
public class Customer 
{
	@Id
	@Column(name="broncoid")
	private int broncoID; 
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="phone")
	private String phoneNumber;
	
	
	@Column(name="dob")
	private Date dob;
			
	public Customer(int broncoid, String first, String last, String phone, Date dob)
	{
		this.broncoID=broncoid;
		this.firstName=first;
		this.lastName=last;
		this.phoneNumber=phone;
		this.dob=dob;
	}
	public Customer()
	{
		
	}
	public void setID(int id)
	{
		broncoID=id;
	}
	public void setFirst(String first)
	{
		firstName=first;
	}
	public void setLast(String last)
	{
		lastName=last;
	}
	public void setPhone(String phone)
	{
		phoneNumber=phone;
	}
	public void setFirst(Date date)
	{
		dob=date;
	}
	public int getID()
	{
		return broncoID; 
	}
	public String getFirst()
	{
		return firstName;
	}
	public String getLast()
	{
		return lastName;
	}
	public String getPhone()
	{
		return phoneNumber;
	}
	public Date getDOB()
	{
		return dob;
	}
	@Override
	public String toString() {
		return "Customer [Broncoid=" + broncoID + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phoneNumber + ", dob=" + dob + "]";
	}
	
	
}
