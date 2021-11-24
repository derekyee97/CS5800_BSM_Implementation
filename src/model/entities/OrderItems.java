package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderitems")
public class OrderItems 
{
	@Id //format when id is auto generated 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="orderitemid")
	private int orderItemID;
	
	@Column(name="orderid")
	private int orderID;
	
	@Column(name="broncoid")
	private int broncoID;
	
	@Column(name="itemid")
	private int itemID;
	
	@Column(name="price")
	private float price;
	
	@Column(name="priceid")
	private int priceID;
	
	@Column(name="quantity")
	private int quantity;
	
	public OrderItems(int orderID, int broncoID, int itemID, float price, int priceID, int quantity)
	{
		this.orderID=orderID;
		this.broncoID=broncoID;
		this.itemID=itemID;
		this.price=price;
		this.priceID=priceID;
		this.quantity=quantity; 
	}
	public OrderItems()
	{
		
	}
	public void setorderItemID(int id)
	{
		orderItemID=id;
	}
	public void setItemID(int id)
	{
		itemID=id;
	}
	public void setorderItemID(float price)
	{
		this.price=price;
	}
	public void setPriceID(int id)
	{
		priceID=id;
	}
	public void setQuantity(int id)
	{
		quantity=id;
	}
	public int getorderID()
	{
		return orderID;
	}
	public int getBroncoID()
	{
		return broncoID;
	}
	public float getPrice()
	{
		return price;
	}
	public int getItemID()
	{
		return itemID;
	}
	public int getPriceID()
	{
		return priceID;
	}
	public int getQuantity()
	{
		return quantity;
	}
}
