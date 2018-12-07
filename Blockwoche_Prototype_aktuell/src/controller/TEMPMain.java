package controller;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import view.StackedBarChart;
import view.StackedBarChart.InputArraysNotEquivalent;

public class TEMPMain {
	private static String szenario = "1";
	private static String szenario1 = "SzenarienXML/Szenario " + szenario + "/ampelstatistics.xml";

	public static void main(String[] args) {
		
		try {
			Document theXMLDoc = new SAXBuilder().build(szenario1);
			
			//the <settings> ... </settings> node
    		Element root = theXMLDoc.getRootElement();
    		
    		List <Element> ampelSets = root.getChildren("wellenGenerator");
    		
			
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Integer[][] intArr = new Integer[3][];
    	intArr[0] = new Integer[7];
    	intArr[1] = new Integer[3];
    	intArr[2] = new Integer[2];
    	intArr[0][0] = 10;
    	intArr[1][0] = 5;
    	intArr[2][1] = 3;
    	
    	String[] strArr = new String[3];
    	strArr[0] = "Szenario 1";
    	strArr[1] = "Szenario 2";
    	strArr[2] = "Szenario 3";
    	
    	Color[] c = new Color[7];
    	c[0] = Color.decode("#00FF00");
    	c[1] = Color.decode("#0000FF");
    	c[2] = Color.decode("#00AAAA");
    	c[3] = Color.decode("#FFFFFF");
    	c[4] = Color.decode("#000000");
    	c[5] = Color.decode("#FFFF00");
    	c[6] = Color.decode("#FF00FF");
    	
    	Color backCol = Color.CYAN;
    	
        try {
			StackedBarChart s = new StackedBarChart("Stacked Bar Chart Frame", "Waiting Time", strArr, intArr, backCol, c);
		} catch (StackedBarChart.InputArraysNotEquivalent e) {
			e.printStackTrace();
		}
		
	}

}
