package model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="historicalprice")
public class HistoricalPrice
{
	@Id //format when id is auto generated 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="priceid")
	private int priceID;
	
	@Column(name="itemid")
	private int itemid; 
	
	@Column(name="price")
	private float price;
	
	@Column(name="start")
	private Date startDate;
	
	
	public HistoricalPrice(int itemid, float price, Date start)
	{
		this.itemid=itemid;
		this.price=price;
		this.startDate=start;
	}
	public HistoricalPrice()
	{
		
	}
	
	public float getPrice()
	{
		return price;
	}
	public int getPriceID()
	{
		return priceID;
	}
	public Date getStart()
	{
		return startDate; 
	}
	public int getItemID()
	{
		return itemid; 
	}
	
	@Override
	public String toString() {
		return "Historical Price: Item [id=" + itemid + ", price=" + price + ", priceID=" + priceID + "Start Date: "+startDate+ "]";
	}
	
}
