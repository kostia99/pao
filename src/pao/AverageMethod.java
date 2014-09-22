package pao;


class AverageMethod extends Linkage {	
	
	protected double[][] updateMatrix(double[][] m, int jX, int jY, int AMOUNT_CLUSTERS) {
		for(int x = 0; x < AMOUNT_CLUSTERS; x++) {
			
			if (x < jX && x < jY) {
				
				m[jX][x] = average(m[jX][x], m[jY][x]);	
			} 
			else if (x < jX && x >= jY) {
				
				m[jX][x] = average(m[jX][x], m[x][jY]);
			} 
			else if (x >= jX && x < jY) {
				
				m[x][jX] = average(m[x][jX], m[jY][x]);
			} 
			else if (x >= jX && x >= jY) {
				
				m[x][jX] = average(m[x][jX], m[x][jY]);
			}
		}
		
		return m;
	}
	
	double average(double jX, double jY) {
		return ((jX + jY) / 2);
	}
}

