package pao;

import java.util.HashMap;
import java.util.Map;
import net.sf.javaml.utils.ArrayUtils;

public class ClusterRow {
	
	private static final int MAX_DISTANCES = 1000, 
								DIFFERENCE = 30;
	
	
	private Map<Integer,Node> clusters = new HashMap<Integer,Node>();
	
	private double[] distances = new double[MAX_DISTANCES];
	private double distance;
	
	  
		static class Node {
		  
		    Node left;

		    Node right;

		    Leaf cluster;
		    

		    public Node(Leaf cluster) {
		      this.cluster = cluster;
		    }
	  }
	  
	  
	
	  Node joinClusters(Node node, Node subNode1, Node subNode2) {
		  
		  if (subNode1.cluster.getDistance() >= subNode2.cluster.getDistance()) {
			  
			  node.left = subNode1;
			  node.right = subNode2;
		  } 
		  else {  
			  
			  node.left = subNode2;
			  node.right = subNode1;
		  }
		  
		  return node;
	  }
	  
	  
	  void createClusters(int amount) {
		  for (int i = 0; i < amount; i++) {
			  
			  Leaf cluster = new Leaf(i, 0, 0);
			  Node node = new Node(cluster);
			  
			  clusters.put(i, node);
		  }
	  }
	  
	  
	  void calculateDistances(Node node, int begin) {
		  double max = ArrayUtils.max(distances); 
		  
		  for (int j = 0; j < clusters.size(); j++) {
			  for (int i = begin; i <= clusters.get(j).cluster.getID(); i++) {
			  
				  distance = setDistance(distances[i], max);
				  searchTree(node, i);
			  }
		  }
	  }
	  
	  
	  private double setDistance(double m, double max) {

		  m = (m * 100) / max;
		  m = (m * (Main.WIDTH - Main.SHIFT)) / 100;
		  
		  return m;
	  }
	  
	  
	  private void searchTree(Node node, int level) {
			if (node == null) {
				return;
			}
	
			
			if (node.cluster.getID() == level) {
				node.cluster.setDistance(distance);	
				
				if (((node.left.cluster.getDistance() + DIFFERENCE) > distance) ||
					((node.right.cluster.getDistance() + DIFFERENCE) > distance)) {
					
					node.cluster.setDistance(distance + DIFFERENCE);	
				} 
				else {
					node.cluster.setDistance(distance);	
				}
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
	  
	  
	  public void addDistance(double d, int index) {
		  distances[index] = d;
	  }
	  
	  
	  public Node createNode(Leaf cluster) {
		  return new Node(cluster);
	  }
	  
	  public Map<Integer,Node> getList() {
		  return clusters;
	  }
	  
	  public boolean listContains(int id) {
		  return clusters.containsKey(id);
	  }
	  
	  
	  public void updateNode(int index, Node node) {
		  clusters.remove(index);
		  clusters.put(index, node);
	  }
	  
	  public void removeNode(int index) {
		  clusters.remove(index);
	  }
	  
	  public int getAmountClusters() {
		  return clusters.size();
	  }
	  
	  public Node getNode(int index) {
		  return clusters.get(index);
	  }
}
