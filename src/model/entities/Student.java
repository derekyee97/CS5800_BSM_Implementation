package model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student 
{
	@Id
	@Column(name="broncoid")
	private int broncoID;
	
	@Column(name="major")
	private String major;
	
	@Column(name="minor")
	private String minor;
	
	@Column(name="enterdate")
	private Date enterDate;
	
	@Column(name="graddate")
	private Date gradDate;	
	
	public Student(int id, String major, String minor, Date enter, Date grad)
	{
		broncoID=id;
		this.major=major;
		this.minor=minor;
		enterDate=enter;
		gradDate=grad;
	}
	public Student()
	{
		
	}
	public void setID(int id)
	{
		broncoID=id;
	}
	public void setMajor(String major)
	{
		this.major=major;
	}
	public void setMinor(String minor)
	{
		this.minor=minor;
	}
	public void setEnterDate(Date enter)
	{
		enterDate=enter;
	}
	public void setGradDate(Date grad)
	{
		gradDate=grad;
	}
	public int getID()
	{
		return broncoID;
	}
	public String getMajor()
	{
		return major;
	}
	public String getMinor()
	{
		return minor;
	}
	public Date getEnterDate()
	{
		return enterDate;
	}
	public Date getGradDate()
	{
		return gradDate;
	}
}
