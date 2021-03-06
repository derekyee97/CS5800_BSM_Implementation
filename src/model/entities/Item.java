package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item implements Comparable<Item>
{
	@Id //format when id is auto generated 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="itemid")
	private int itemid;
	
	@Column(name="name")
	private String itemName;
	
	@Column(name="type")
	private String type;
	
	public Item(String name, String type)
	{
		itemName=name;
		this.type=type;
	}
	public Item()
	{
		
	}
	public void setName(String name)
	{
		itemName=name;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	public int getId()
	{
		return itemid;
	}
	public String getType()
	{
		return type;
	}
	public String getName()
	{
		return itemName;
	}
	@Override
	public String toString() {
		return "Item [id=" + itemid + ", name=" + itemName + ", type=" + type + "]";
	}
	
	
    @Override 
    public int compareTo(Item itemCompared)
    {
    	System.out.println("COMPARISON");
        if (itemid==itemCompared.getId()) {
 
            // item is the same 
            return 0;
        }
        
        else 
        {
 
            // item is different
            return 1;
        }
    }
    @Override
    public boolean equals(Object obj) {
 
        if(itemid==((Item)obj).getId())
        {
        	return true;
        }
        return false;
    }
	
}
