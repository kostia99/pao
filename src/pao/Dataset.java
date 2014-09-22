package pao;

public class Dataset {
		
	private String name;
	private String[] varNames;
	private int id;	 
	private double[] variables, originalVariables;
	
	
	Dataset(String name, int id, double[] originalVariables, double[] variables, String[] varNames) {
		this.name = name;
		this.id = id;
		this.variables = variables;
		this.originalVariables = originalVariables;
		this.varNames = varNames;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public void setVariable(int index, double value) {
		variables[index] = value;
	}
	
	public double[] getVariables() {
		return variables;
	}
	
	public double getVariable(int index) {
		return variables[index];
	}
	
	public double getOrVariable(int index) {
		return originalVariables[index];
	}
	
	public String getVarName(int index) {
		return varNames[index];
	}


}
