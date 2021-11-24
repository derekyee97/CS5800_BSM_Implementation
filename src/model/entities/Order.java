package model.entities;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order 
{
	@Id //format when id is auto generated 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="orderid")
	private int orderID;
	
	@Column(name="broncoid")
	private int broncoID; 
	
	@Column(name="date")
	private Date date; 
	
	@Column(name="time")
	private Timestamp time; 
	
	
	@Column(name="status")
	private String status; 
	
	@Column(name="originalprice")
	private float ogPrice; 
	
	@Column(name="discountprice")
	private float discountPrice; 
	
	public Order(int broncoID,Date date,Timestamp time, String status, float ogPrice, float discountPrice)
	{
		this.broncoID=broncoID;
		this.date=date;
		this.time=time;
		this.status=status;
		this.ogPrice=ogPrice;
		this.discountPrice=discountPrice;
	}
	public Order()
	{
		
	}
	
	public void setBroncoID(int id)
	{
		broncoID=id;
	}
	public void setDate(Date date)
	{
		this.date=date;
	}
	public void setTime(Timestamp time)
	{
		this.time=time;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public void setOGPrice(float price)
	{
		ogPrice=price;
	}
	public void setDiscountPrice(float price)
	{
		discountPrice=price;
	}
	public int getOrderID()
	{
		return orderID;
	}
	public int getBroncoID()
	{
		return broncoID;
	}
	public Date getDate()
	{
		return date;
	}
	public Timestamp getTime()
	{
		return time;
	}
	public String getStatus()
	{
		return status;
	}
	public float getOGPrice()
	{
		return ogPrice;
	}
	public float getDiscountPrice()
	{
		return discountPrice;
	}
}
