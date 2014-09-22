package pao;


import ui.DrawUserInterface;
import ui.Event;
import ui.Colour;
import ui.UIAuxiliaryMethods;
import ui.UserInterfaceFactory;
import pao.ClusterRow.Node;


public class Main {
	
	static final int HEIGHT = 800, 
			   		 WIDTH = 600, 
			   		 SHIFT = 50;


	Node node;

	DrawUserInterface ui;

	Colour black;

	String howToDraw, end;

	int diameter = 0, width = 0, DEFAULT_WIDTH = 0;


	Main() {

		howToDraw = "There are only two variables in the dataset.\nUse two-dimensional coordinate system instead of Dendrogram?";
		end = "There is nothing left to do.\nExit program?";

		black = new Colour(0,0,0);
		
		ui = UserInterfaceFactory.getDrawUI(HEIGHT, WIDTH);
		UIAuxiliaryMethods.askUserForInput();
	}
	
	
	void ClusterData(ClusterMethod method, NumberRow numRow, UnitRow data) {
		method.setData(numRow, data);
		method.analyzeData();
		node = method.getClusterTree().getNode(0);
	}
	
	void selectClusteringMethod(NumberRow numRow, UnitRow data) {
		String option = UIAuxiliaryMethods.askUserForChoice("Select method", 
															  "Single linkage", 
															  "Complete linkage", 
															  "Average linkage");
		
		if (option.equals("Single linkage")) {
			ClusterData(new SingleMethod(), numRow, data);
		}
		else if (option.equals("Complete linkage")) {
			ClusterData(new CompleteMethod(), numRow , data);
		}
		else if (option.equals("Average linkage")) {
			ClusterData(new AverageMethod(), numRow, data);
		}
		
		else {
			System.exit(1);
		}
	}
	
	void showCluster(Dendrogram d) {
		
		ui.drawLine(d.gBeginX(), d.gPosition(), ((WIDTH + SHIFT) - d.gLeftDistance()), d.gPosition(), black); //left
		ui.drawLine(d.gBeginX(), d.gWidth(), ((WIDTH + SHIFT) - d.gRightDistance()), d.gWidth(), black); // right
		ui.drawLine(d.gBeginX(), d.gPosition(), d.gBeginX(), d.gWidth(), black); // center
		
		if (d.rightHasName()) {
			ui.drawText(((WIDTH + SHIFT) - d.gRightDistance()) + 15, d.gWidth() - 5, d.gName("right"), black);
		}		
		if (d.leftHasName()) {
			ui.drawText(((WIDTH + SHIFT) - d.gLeftDistance()) + 15, d.gPosition() - 5, d.gName("left"), black);
		}
	}
	
	
	void showDistanceLine() {
		int d = 0;
		
		ui.drawLine(100, 10, WIDTH + SHIFT, 10, black);
		
		for (int i = 0; i < 6; i++) {
			ui.drawLine(d += 100, 5, d, 15, black);
		}
	}
	
	
	void showClusters(Dendrogram d, int begin, int amount, int DEFAULT_WIDTH) {
		
		while(begin < amount) {
			
			d.searchTree(this.node, begin += 1, (d.gRootPosition() + DEFAULT_WIDTH));
			showCluster(d);
			d.resetPosition(DEFAULT_WIDTH);
		}
	}
	
	
	void showDendrogram(Dendrogram d, int begin, int amount, int DEFAULT_WIDTH) {
		ui.clear();
		
		showDistanceLine();
		showClusters(d, begin, amount, DEFAULT_WIDTH);
		
		ui.showChanges();
	}
	
	
	void setWidthForDendrogram(Node node, int DEFAULT_WIDTH) {
		width = DEFAULT_WIDTH;
		
		findWidthForDendrogram(node.right, 1);
		findWidthForDendrogram(node.left, 2);
		
		node.cluster.setWidth(width);
	}
	
	
	void findWidthForDendrogram(Node node, int d) {
		if (node == null) {
			return;
		} 
		
		if (d == 1) { findWidthForDendrogram(node.left, 1); };
		if (d == 2) { findWidthForDendrogram(node.right, 2); };
		
		width += (node.cluster.getWidth() / 2); 
	}	
	
	
	
	
	void setTreeWidth(Node node, int level) {
		if (node.left == null || node.right == null) {
			return;
		}
		
		if (node.cluster.getID() == level) {
			
			setWidthForDendrogram(node, DEFAULT_WIDTH);
		}
		
		if (node.cluster.getID() > level) {
			setTreeWidth(node.left, level);
			setTreeWidth(node.right, level);
		}
		else if (node.cluster.getID() < level) {
			setTreeWidth(node.right, level);
			setTreeWidth(node.left, level);
		} 
		else {
			setTreeWidth(node.left, level);
			setTreeWidth(node.right, level);
		}
	}
	
	
	
	void drawDendrogram(UnitRow data) {
		DEFAULT_WIDTH = ((WIDTH - SHIFT) / data.getAmountObjects());
		
		int	amount = data.getAmountObjects(),
			begin = data.getAmountObjects();
		
		for (int i = amount; i <= node.cluster.getID(); i++) {
			setTreeWidth(node, i);
		}
		
		
		Dendrogram d = new Dendrogram();
		
		d.createNamesList(data);
		d.setRootPosition(node);
		
		
		while (true) {
			
			Event event = ui.getEvent();
		
			if (event.name.equals("other_key")) {
				
				showDendrogram(d, begin, amount += 1, DEFAULT_WIDTH);
			}
			if (amount == node.cluster.getID()) {
				
				if (UIAuxiliaryMethods.askUserForBoolean(end)) {
					System.exit(1);
				} 
				else {
					break;
				}
			}
		}
	}
	
	
	
	void prepareCartesian(UnitRow data) { // this method draws two-dimensional coordinate system from initial data;
		
		ui.drawLine(160, 600, 160, 20, black); // X-as  
		ui.drawLine(130, 50, 800, 50, black);  // and Y-as
		ui.drawText(10, 300, data.getObject(0).getVarName(0), black);
		ui.drawText(400, 5, data.getObject(0).getVarName(1), black);
		
		
		double max1 = 0.0,
				max2 = 0.0;
		
		for (int i = 0; i < data.getAmountObjects(); i++) {
			
			if (max1 < data.getObject(i).getOrVariable(0)) {
				max1 = data.getObject(i).getOrVariable(0);
			}
			if (max2 < data.getObject(i).getOrVariable(1)) {
				max2 = data.getObject(i).getOrVariable(1);
			}
		}
		
		
		int nextDividingLineY = 50,
			nextDividingLineX = 160;
		
		for (int i = 1; i < 11; i++) { // divide X and Y by 10;
			int factor = (i * 10);
			
			int t1 = (((int)max1 * 10) * factor) / 100;
			int t2 = (((int)max2 * 10) * factor) / 100;
			
			String text1 = Double.toString((double)t1 / 10);
			String text2 = Double.toString((double)t2 / 10);
			
			
			ui.drawText(117, (nextDividingLineY += 50) - 3, text1, black);
			ui.drawText((nextDividingLineX += 50) - 7, 25, text2, black);

			ui.drawLine(155, nextDividingLineY, 165, nextDividingLineY, black);
			ui.drawLine(nextDividingLineX, 45, nextDividingLineX, 55, black);
		}
		
		
		for (int i = 0; i < data.getAmountObjects(); i++) { // draw points;
			
			double y = data.getObject(i).getOrVariable(0);
			double x = data.getObject(i).getOrVariable(1);
			
			int factorY = (((int)(y * 10) * 100) / (int)(max1 * 10));
			int factorX = (((int)(x * 10) * 100) / (int)(max2 * 10));
			
			int pY = ((factorY * 500) / 100);
			int pX = ((factorX * 500) / 100);
			
			ui.drawCircle(pX + 160, pY + 50, 5, 5, black, true);
		}
		ui.showChanges();
		
		drawCartesian(data, max1, max2);
	}
	
	
	void drawCartesian(UnitRow data, double max1, double max2) {
		Cartesian xy = new Cartesian(data);
		xy.setMaxXY(max1, max2);
		
		int amount = data.getAmountObjects();
		
		
		while (true) {
			
			Event event = ui.getEvent();
			
			if (event.name.equals("other_key")) {
				xy.searchTree(node, amount += 1);
		
				ui.drawCircle(xy.getX() + 160, xy.getY() + 50, xy.getWidth(), xy.getHeight(), black, false);
				ui.showChanges();
			}
			if (amount == node.cluster.getID()) {
				if (UIAuxiliaryMethods.askUserForBoolean(end)) {
					System.exit(1);
				} 
				else {
					break;
				}
			}
		}
	}
	
	
	void start() {
		UnitReader reader = new UnitReader();
		UnitRow data = new UnitRow();
		NumberRow numRow = new NumberRow();
		
		data = reader.readData();
		numRow.fillNumberRow(data);
		selectClusteringMethod(numRow, data);
		
		if (data.getObject(0).getVariables().length == 2) {
			if (UIAuxiliaryMethods.askUserForBoolean(howToDraw)) {
				prepareCartesian(data);
			}
			else {
				drawDendrogram(data);
			}
		}
		else {
			drawDendrogram(data);
		}
		
	}

	public static void main(String[] argv) {
		new Main().start();
	}
}
