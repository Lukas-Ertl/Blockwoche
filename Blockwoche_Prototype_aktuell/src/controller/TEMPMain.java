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
	private static String szenarioNummer = "1";
	private static String szenario = "SzenarienXML/Szenario " + szenarioNummer + "/auswertung.xml";
	private static String szenarioNummerZwei = "2";
	private static String szenarioZwei = "SzenarienXML/Szenario " + szenarioNummer + "/auswertung.xml";
	
	private static String persistent = "xml/persistentstatistics.xml";

	public static void main(String[] args) {
		
		try {
			Document theXMLDoc = new SAXBuilder().build(szenario);
			
			//the <statistics> ... </statistics> node
    		Element root = theXMLDoc.getRootElement();
    		
    		List<Element> ampelSets = root.getChildren("ampelset");
    		int ampelSetsSize = ampelSets.size(); 
    		Integer[][] intArr = new Integer[ampelSetsSize][];

    		for(int i=0; i<ampelSetsSize; i++)
    		{
    			Element ampelSet = ampelSets.get(i);
    			List<Element> ampeln = ampelSet.getChildren("ampel");
    			intArr[i] = new Integer[ampeln.size()];
    			for(int j=0; j<ampeln.size(); j++)
    			{
    				Element ampel = ampeln.get(j);
    				intArr[i][j] = Integer.parseInt( ampel.getChildText("insgesamtewartezeit") );
    			}
    		}
    		
    		String[] strArr = new String[ampelSetsSize];
    		for(int i=0; i<ampelSetsSize; i++)
    		{
    			strArr[i] = "Ampel Set " + i;
    		}
    		
    		Color[] c = new Color[4];
    		c[0] = Color.decode("#FFE376");
    		c[1] = Color.decode("#65E832");
    		c[2] = Color.decode("#FF8CCF");
    		c[3] = Color.decode("#6764E8");
    		
    		Color backCol = Color.decode("#39FFF2");
    		
    		try {
    			StackedBarChart s = new StackedBarChart("Stacked Bar Chart Frame", "Waiting Time", strArr, intArr, backCol, c);
    		} catch (StackedBarChart.InputArraysNotEquivalent e) {
    			e.printStackTrace();
    		}
			
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		try {
			Document theXMLDoc = new SAXBuilder().build(szenario);
    		Element root = theXMLDoc.getRootElement();
    		List<Element> ampelSets = root.getChildren("ampelset");
    		int ampelSetsSize = ampelSets.size(); 
    		
    		Document theXMLDocZwei = new SAXBuilder().build(szenarioZwei);
    		Element rootZwei = theXMLDocZwei.getRootElement();
    		List<Element> ampelSetsZwei = rootZwei.getChildren("ampelset");
    		int ampelSetsSizeZwei = ampelSetsZwei.size();
    		
    		Integer[][] intArr = new Integer[ampelSetsSize+ampelSetsSizeZwei][];

    		for(int i=0; i<intArr.length; i++)
    		{
    			Element ampelSet = ampelSets.get(i);
    			List<Element> ampeln = ampelSet.getChildren("ampel");
    			intArr[i] = new Integer[ampeln.size()];
    			for(int j=0; j<ampeln.size(); j++)
    			{
    				Element ampel = ampeln.get(j);
    				intArr[i][j] = Integer.parseInt( ampel.getChildText("insgesamtewartezeit") );
    			}
    		}
    		
    		String[] strArr = new String[ampelSetsSize];
    		for(int i=0; i<ampelSetsSize; i++)
    		{
    			strArr[i] = "Ampel Set " + i;
    		}
    		
    		Color[] c = new Color[4];
    		c[0] = Color.decode("#FFE376");
    		c[1] = Color.decode("#65E832");
    		c[2] = Color.decode("#FF8CCF");
    		c[3] = Color.decode("#6764E8");
    		
    		Color backCol = Color.decode("#39FFF2");
    		
    		try {
    			StackedBarChart s = new StackedBarChart("Stacked Bar Chart Frame", "Waiting Time", strArr, intArr, backCol, c);
    		} catch (StackedBarChart.InputArraysNotEquivalent e) {
    			e.printStackTrace();
    		}
			
		} catch (JDOMException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
