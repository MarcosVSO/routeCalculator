package base;

import java.util.ArrayList;

public class WayPoint {
	private String name;
	private ArrayList<String> location = new ArrayList<String>();
	
	public ArrayList<String> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<String> location) {
		this.location = location;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
