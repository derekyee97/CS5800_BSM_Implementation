package model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
public class Professor
{
	@Id
	@Column(name="broncoid")
	private int broncoID;
	
	@Column(name="department")
	private String department;
	
	@Column(name="office")
	private String office;
	
	@Column(name="research")
	private String research;
	
	public Professor(int id, String department, String office, String research)
	{
		broncoID=id;
		this.department=department;
		this.office=office;
		this.research=research;
	}
	public Professor()
	{
		
	}
	
	public void setId(int id)
	{
		broncoID=id;
	}
	public void setDepartment(String department)
	{
		this.department=department;
	}
	public void setOffice(String office)
	{
		this.office=office;
	}
	public void setResearch(String research)
	{
		this.research=research;
	}
	public int getID()
	{
		return broncoID;
	}
	public String getDepartment()
	{
		return department;
	}
	public String getOffice()
	{
		return office;
	}
	public String getResearch()
	{
		return research;
	}
}
