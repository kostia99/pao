package pao;

import pao.ClusterRow.Node;

public class Cartesian {
		
	UnitRow data;
	

	private int maxX, 
				 maxY, 
				 minX, 
				 minY, 
				 x, 
				 y, 
				 height, 
				 width;
	
	private double max1, 
		    		max2; 
	
	
	Cartesian(UnitRow data) {
		
		this.data = data;
	}
	
	
	
	void setDiameter(Node node) {
		if (node.cluster.getDistance() == 0) {
			return;
		}
		
		setDiameter(node.left);
		setDiameter(node.right);
	}
	
	
	int p (int i, int xy, double max) {
		double p = data.getObject(i).getOrVariable(xy);
		
		int factor = (((int)(p * 10) * 100) / (int)(max * 10));
		int coordinate = ((factor * 500) / 100);
		
		return coordinate;
	}
	
	
	void searchSubTree(Node node) {
		if (node == null) { return; }
		
		searchSubTree(node.left);
		searchSubTree(node.right);
		
		if (node.cluster.getDistance() == 0) {
			if (maxX < p(node.cluster.getID(), 1, max2)) {
				maxX = p(node.cluster.getID(), 1, max2); 
			}
			if (maxY < p(node.cluster.getID(), 0, max1)) {
				maxY = p(node.cluster.getID(), 0, max1);
			}
			if (minX > p(node.cluster.getID(), 1, max2)) {
				minX = p(node.cluster.getID(), 1, max2);
			}
			if (minY > p(node.cluster.getID(), 0, max1)) {
				minY = p(node.cluster.getID(), 0, max1);
			}
		}
	}
	
	
	void setCoordinates(Node node) {
		maxX = 0;
		maxY = 0;
		minX = 1000;
		minY = 1000;
		
		searchSubTree(node);
			
		x = minX + ((maxX - minX) / 2);
		y = minY + ((maxY - minY) / 2);
			
		width = (maxX - minX) + 20;
		height = (maxY - minY) + 20;
	}
	
	
	void searchTree(Node node, int level) {
		if (node == null) { return; }
		
		
		if (node.cluster.getID() == level) {	
			setCoordinates(node);
		}
		
		if (node.cluster.getID() > level) {
			searchTree(node.left, level);
			searchTree(node.right, level);
		}
		else if (node.cluster.getID() < level) {
			searchTree(node.right, level);
			searchTree(node.left, level);
		} 
		else {
			searchTree(node.left, level);
			searchTree(node.right, level);
		}	
	}
	
	
	public void setMaxXY(double max1, double max2) {
		this.max1 = max1;
		this.max2 = max2;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

}
