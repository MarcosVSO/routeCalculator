package base;

public class Routes {
	private String weight_name;
	private String weight;
	private String duration;
	private String distance;
	private Geometry geometry = new Geometry();
	
	
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWeight_name() {
		return weight_name;
	}
	public void setWeight_name(String weight_name) {
		this.weight_name = weight_name;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}
