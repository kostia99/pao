package pao;

import java.util.ArrayList;
import java.util.List;
import pao.ClusterRow.Node;


class Dendrogram {
	
	private List<String> names = new ArrayList<String>();
	
	private int beginX = 0, 
				 distance = 0, 
				 leftDistance = 0, 
				 rightDistance = 0, 
				 width = 0, 
				 position = 0, 
				 rootPosition = 0,
				 rN = 0,
				 lN = 0;
	
	private boolean rightHasName = false, leftHasName = false;
	
	private String currentID;
	
	
	
	String gName(String name) {
		
		if (name.equals("right")) {
			name = names.get(rN); 
		} 
		else if (name.equals("left")) {
			name = names.get(lN);
		}
		
		return name;
	}
	
	
	void resetPosition(int DEFAULT_WIDTH) {
		rightHasName = false;
		leftHasName = false;
		position = DEFAULT_WIDTH;
	}
	
	
	void createNamesList(UnitRow data) {
		for (int i = 0; i < data.getAmountObjects(); i++) {
			names.add(data.getObject(i).getName());
		}
	}
	
	
	void setRootPosition(Node node) {
		if (node.cluster.getWidth() == 0) {
			return;
		}
		setRootPosition(node.left);
		
		rootPosition += (node.left.cluster.getWidth() / 2);
	}
	
	
	void setCoordinates(Node node) {
		beginX = ((Main.WIDTH + Main.SHIFT) - (int)node.cluster.getDistance());
		distance = (int)node.cluster.getDistance();
		leftDistance = (int)node.left.cluster.getDistance();
		rightDistance = (int)node.right.cluster.getDistance();
		width = position + node.cluster.getWidth();
	}
	
	
	void setPosition(Node node, int p) {
		position = p;
		currentID = Integer.toString(node.cluster.getID());
		setCoordinates(node);
		
		if (node.right.cluster.getDistance() == 0) {
			rightHasName = true;
			rN = node.right.cluster.getID();
			
		}
		if (node.left.cluster.getDistance() == 0) {
			leftHasName = true;
			lN = node.left.cluster.getID();
		}
	}
	
	
	void searchTree(Node node, int level, int p) {
		if (node.left == null || node.right == null) {
			return;
		}

		
		if (node.cluster.getID() == level) {
			
			setPosition(node, p);
		}
		
		if (node.cluster.getID() > level) {
			searchTree(node.left, level, p - (node.left.cluster.getWidth() / 2));
			searchTree(node.right, level, (p - (node.right.cluster.getWidth() / 2)) + node.cluster.getWidth());
		}
		else if (node.cluster.getID() < level) {
			searchTree(node.right, level, (p + (node.right.cluster.getWidth() / 2)) + node.cluster.getWidth());
			searchTree(node.left, level, p - (node.left.cluster.getWidth() / 2));
		} 
		else {
			searchTree(node.left, level, p - (node.left.cluster.getWidth() / 2));
			searchTree(node.right, level, (p + (node.right.cluster.getWidth() / 2)) + node.cluster.getWidth());
		}
	}
	
	
	public int gBeginX() {
		return beginX;
	}
	
	public int gDistance() {
		return distance;
	}
	
	public int gLeftDistance() {
		return leftDistance;
	}
	
	public int gRightDistance() {
		return rightDistance;
	}
	
	public int gWidth() {
		return width;
	}
	
	public int gPosition() {
		return position;
	}
	
	public int gRootPosition() {
		return rootPosition;
	}
	
	public boolean rightHasName() {
		return rightHasName;
	}
	
	public boolean leftHasName() {
		return leftHasName;
	}
	
	public String gCurrentID() {
		return currentID;
	}
}
