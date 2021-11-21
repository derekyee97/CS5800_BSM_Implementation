package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "itempicture")
public class ItemPicture 
{
	@Id //format when id is auto generated 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="itempictureid")
	private int itemPictureID;
	
	@Column(name="itemid")
	private int itemID;
	
	
	@Column(name="picture")
	private byte[] image;
	
	public ItemPicture(int itemID, byte[] picture)
	{
		this.itemID=itemID;
		this.image=picture;
	}
	public ItemPicture()
	{
		
	}
	public void setItemID(int id)
	{
		itemID=id;
	}
	public void setPicture(byte[] picture)
	{
		image=picture;
	}
	public int getItemId()
	{
		return itemID;
	}
	public int getID()
	{
		return itemPictureID;
	}
	public byte[] getImage()
	{
		return image;
	}
}
