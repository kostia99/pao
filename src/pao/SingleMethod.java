package pao;


class SingleMethod extends Linkage {
	
	protected double[][] updateMatrix(double[][] m, int jX, int jY, int AMOUNT_CLUSTERS) {
		for(int x = 0; x < AMOUNT_CLUSTERS; x++) {
			
			if (x < jX && x < jY) {
				if (m[jX][x] > m[jY][x]) {
					m[jX][x] = m[jY][x];
				}
			} else if (x < jX && x >= jY) {
				if (m[jX][x] > m[x][jY]) {
					m[jX][x] = m[x][jY];
				}
			} else if (x >= jX && x < jY) {
				if (m[x][jX] > m[jY][x]) {
					m[x][jX] = m[jY][x];
				}
			} else if (x >= jX && x >= jY) {
				if (m[x][jX] > m[x][jY]) {
					m[x][jX] = m[x][jY];
				}
			}
		}
		
		return m;
	}
	
}
