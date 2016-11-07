package main;

public class comboBoxElement {
	private String name;
	private int place;
	
	public comboBoxElement(String name, int place){
		this.name = name;
		this.place = place;
	}
	
	public String getName(){return name;}
	public int getPlace(){return place;}
	public void setName(String n)
	{
		name = n;
	}
	public void setPlace(int p)
	{
		place = p;
	}
}
