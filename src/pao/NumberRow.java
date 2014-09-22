package pao;


public class NumberRow {

		
	private static final int MAX_ROWS = 1000,
					   			MAX_COLUMNS = 1000;

	private double[][] numberRow;
	
	
	NumberRow() {
		
		numberRow = new double[MAX_ROWS][MAX_COLUMNS];
	}
	
	
	
	double getEuclideanDistance(double[] vars1, double[] vars2) {
		double distance = 0;
		
		for (int i = 0; i < vars1.length; i++) {
			
			distance += Math.pow((vars1[i] - vars2[i]), 2);
		}
		
		return Math.sqrt(distance);
	}
	
	
	
	double getPearsonCorrelation(double[] vars1,double[] vars2) {
		
        double result = 0,
         		sum_sq_x = 0,
         		sum_sq_y = 0,
         		sum_coproduct = 0,
         		mean_x = vars1[0],
         		mean_y = vars2[0];
         		
        for(int i = 2; i < vars1.length + 1; i += 1) {
        	
            double sweep = Double.valueOf(i - 1) / i;
            double delta_x = vars1[i-1] - mean_x;
            double delta_y = vars2[i-1] - mean_y;
            
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        
        double pop_sd_x = (double) Math.sqrt(sum_sq_x / vars1.length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y / vars2.length);
        double cov_x_y = sum_coproduct / vars1.length;
        
        result = cov_x_y / (pop_sd_x * pop_sd_y);
        
        return (1 - result);
    }
	
	
	 
	double calculateDistance(double[] v1, double[] v2, int fomula) {
		if (fomula == 0) {
			return getPearsonCorrelation(v1, v2);
		}
		
		return getEuclideanDistance(v1, v2);
	}
	
	
	void fillNumberRow(UnitRow data) {
		
		for (int y = 0; y < data.getAmountObjects(); y++) {
			for (int x = 0; x < data.getAmountObjects(); x++) {
				
				if (y > x) { 
							
					numberRow[y][x] = calculateDistance(data.getObject(y).getVariables(), 
														data.getObject(x).getVariables(), data.getFomula());
					
				}
			}
		} 
	}
	
	
	public double getDistance(int y, int x) {
		return numberRow[y][x];
	}
	
	public double[][] getMatrix() {
		return numberRow;
	}
}
