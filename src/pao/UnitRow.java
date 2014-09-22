package pao;

import java.util.HashMap;
import java.util.Map;


public class UnitRow {
	
	private Map<Integer,Dataset> data = new HashMap<Integer,Dataset>();
	
	private int fomula = 0, amount = 0;
	
	void addOneObject(Dataset o) {
		data.put(o.getID(), o);
	}
	
	public int getAmountObjects() {
		return data.size();
	}
	
	public Dataset getObject(int id) {
		return data.get(id);
	}
	
	public boolean dataContains(int id) {
		return data.containsKey(id);
	}
	
	public void setFomula(int value) {
		fomula = value;
	}
	
	public void setAmountClusters(int amount) {
		this.amount = amount;
	}	
	
	public int getTotalAmountClusters() {
		return amount;
	}
	
	public int getFomula() {
		return fomula;
	}
}
