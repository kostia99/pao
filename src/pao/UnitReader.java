package pao;

import java.util.Scanner;
import net.sf.javaml.utils.ArrayUtils;


public class UnitReader {
		
	private static final int MAX_OBJECTS = 1000, 
								MAX_VARIABLES = 50;

	private int numberOfObjects, fomula;
	
	
	UnitReader() {
		
		numberOfObjects = 0;
		fomula = 0;
	}
	
	
	
	double normalize(double value, double max, double min) {
		return (value - min) / (max - min);
	}
	
	
	double[][] normalizeData(double[][] vars) {
		double[] temp = new double[numberOfObjects];
		
		for (int i = 0; i < vars[0].length; i++) {
			
			for (int j = 0; j < numberOfObjects; j++) {
				temp[j] = vars[j][i];
			}
			
			
			for (int j = 0; j < numberOfObjects; j++) {	
				vars[j][i] = normalize(temp[j], ArrayUtils.max(temp), ArrayUtils.min(temp));
			}
		}
		
		
		if (vars[0].length > 50) {
			
			vars = onlyWithHighStandardDeviation(vars);
		} 
		else {
			fomula = 1;
		}
		
		return vars;
	}
	
	
	double[][] onlyWithHighStandardDeviation(double[][] vars) {
		vars = calculateStandardDeviation(vars);
		
		double[][] variables = new double[MAX_OBJECTS][MAX_VARIABLES];
		
		for (int i = 0; i < variables[0].length; i++) {
			
			int index = ArrayUtils.maxIndex(vars[numberOfObjects]);
			
			for (int j = 0; j < numberOfObjects; j++) {
				variables[j][i] = vars[j][index];
			}
			
			vars[numberOfObjects][index] = 0;
		}
		
		return variables;
	}
	
	
	double[][] calculateStandardDeviation(double[][] vars) {
		
		for (int i = 0; i < vars[0].length; i++) {
			double som = 0;
			
			for (int j = 0; j < numberOfObjects; j++) {
				som += vars[j][i];
			}
			
			
			double average = som / (numberOfObjects - 1);
			double sum = 0;
			
			
			for (int j = 0; j < numberOfObjects; j++) {
				sum += Math.pow((vars[j][i] - average), 2);
			}
			
			
			vars[numberOfObjects][i] = Math.sqrt(sum / (numberOfObjects - 2));
		}
		
		return vars;
	}
	
	
	double[][] copyOriginalVars(double[][] variables) {
		double[][] originalVars = new double[variables.length][variables[0].length];
		
		for (int y = 0; y < variables.length; y++) {
			for (int x = 0; x < variables[y].length; x++) {
				originalVars[y][x] = variables[y][x];
			}
		}
		
		return originalVars;
	}
	
	
	UnitRow readData() {
		Scanner in = new Scanner(System.in);
		
		int totalNumberOfClusters = in.nextInt(), numberOfElements = in.nextInt(), numberOfVariables = in.nextInt();
		
		String empty = in.nextLine();
		
		String[] varNames = new String[2];
		
		if (numberOfVariables == 2) {
			String variablesNames = in.nextLine();
			Scanner namesIn = new Scanner(variablesNames);
			String sort = namesIn.next();
			
			varNames[0] = namesIn.next();
			varNames[1] = namesIn.next();
		} 
		else {
			String empty2 = in.nextLine();
		}
		
		
		double[][] variables = new double[MAX_OBJECTS][numberOfVariables];
		
		String[] names = new String[MAX_OBJECTS];
		
		
		while (in.hasNextLine()) {
			
			String line = in.nextLine();
			
			Scanner inLine = new Scanner(line);
			inLine.useDelimiter("\t");
			
			names[numberOfObjects] = inLine.next();

			
			int i = 0;
			
			while (inLine.hasNext()) {
				variables[numberOfObjects][i] = inLine.nextDouble();
				i += 1;
			}
			
			numberOfObjects += 1;
		}
		
		
		
		UnitRow data = makeData(names, 
								copyOriginalVars(variables),
								normalizeData(variables),  
								varNames, 
								totalNumberOfClusters);
		
		
		return data;
	}
	
	
	UnitRow makeData(String[] names, double[][] orVars, double[][] variables, String[] varNames, int total) {
		UnitRow data = new UnitRow();
		
		data.setFomula(fomula);
		data.setAmountClusters(total);
		
		for (int i = 0; i < numberOfObjects; i++) {
			
			Dataset object = new Dataset(names[i], i, orVars[i], variables[i], varNames);
			data.addOneObject(object);
		}
		
		return data;
	}
	

}
