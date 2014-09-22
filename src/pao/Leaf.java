package pao;

public class Leaf {

	private int id, width;
	private double distance;
	
	
	Leaf(int id, double distance, int width) {
		
		this.id = id;
		this.distance = distance;
		this.width = width;
	}
	

	public int getID() {
		return id;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
