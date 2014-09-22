package pao;


abstract class Linkage implements ClusterMethod {
	
	private ClusterRow tree = new ClusterRow();
	private Leaf cluster;

	private double[][] matrix;
	private int AMOUNT_CLUSTERS, id;
	
	
	protected abstract double[][] updateMatrix(double[][] m, int jX, int jY, int AMOUNT_CLUSTERS);
	
	public ClusterRow getClusterTree() { return tree; }
	
	
	public void setData(NumberRow numRow, UnitRow data) {
		matrix = numRow.getMatrix();
		
		AMOUNT_CLUSTERS = data.getAmountObjects();
		id = AMOUNT_CLUSTERS;
		
		tree.createClusters(AMOUNT_CLUSTERS);
	}
	
	
	
	public void analyzeData() {
		
		while (tree.getAmountClusters() > 1) {

			double min = 1000.0;
			int jX = 1000, jY = 1000;


			for (int y = 0; y < AMOUNT_CLUSTERS; y++) {
				for (int x = 0; x < AMOUNT_CLUSTERS; x++) {
					
					if (y > x && tree.listContains(y) && tree.listContains(x)) {
	
						if (min > matrix[y][x]) {
	
							min = matrix[y][x];
	
								jX = x;
								jY = y;
						}
							
					} 
				}
			}
				
			linkClusters(min, jX, jY);
		}
		
		tree.calculateDistances(tree.getNode(0), AMOUNT_CLUSTERS + 1);
	}
	
	
	private void linkClusters(double min, int jX, int jY) {
		
		cluster = new Leaf(id += 1, 0, 0);
		
		tree.addDistance(min, id);
		tree.updateNode(jX, (tree.joinClusters(tree.createNode(cluster), tree.getNode(jX), tree.getNode(jY))));
		tree.removeNode(jY);
		
		matrix = updateMatrix(matrix, jX, jY, AMOUNT_CLUSTERS);
	}
}
