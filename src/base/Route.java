package base;

import java.util.ArrayList;

public class Route {
	private String code;
	private ArrayList<WayPoint> waypoints = new ArrayList<WayPoint>();
	private ArrayList<Routes> routes = new ArrayList<Routes>();
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public ArrayList<WayPoint> getWaypoints() {
		return waypoints;
	}
	
	public void setWaypoints(ArrayList<WayPoint> waypoints) {
		this.waypoints = waypoints;
	}
	
	public ArrayList<Routes> getRoutes() {
		return routes;
	}
	
	public void setgetRoutes(ArrayList<Routes> routes) {
		this.routes = routes;
	}
	
	public String getRoutesDistance() {
		ArrayList<Routes> rota = this.getRoutes();
	    return (rota.get(0).getDistance());
	}
	
	public String getRoutesDuration() {
		ArrayList<Routes> rota = this.getRoutes();
	    return (rota.get(0).getDuration());
	}
	
	public ArrayList<String> getWaypointsSaida() {
		ArrayList<WayPoint> point = this.getWaypoints();
		return point.get(0).getLocation();
	}
	
	public ArrayList<String> getWaypointsChegada() {
		ArrayList<WayPoint> point = this.getWaypoints();
		return point.get(1).getLocation();
	}
	
	public ArrayList<String[]> getCoordenadasRota(){
		Geometry geo = this.getRoutes().get(0).getGeometry();
		ArrayList<String[]> coordenadasRota = geo.getCoordinates();
		return coordenadasRota;
	}

	
}
