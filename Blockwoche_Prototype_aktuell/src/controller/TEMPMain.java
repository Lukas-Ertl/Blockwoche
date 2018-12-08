package controller;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import view.StackedBarChart;

public class TEMPMain {
	private static HashMap<Integer, String> szenarioPaths = new HashMap<Integer, String>();
	private static void init()
	{
		szenarioPaths.put(1, "SzenarienXML/Szenario 1/auswertung.xml");
		szenarioPaths.put(2, "SzenarienXML/Szenario 2/auswertung.xml");
	}
	
	private static String persistent = "xml/persistentstatistics.xml";

	public static void main(String[] args) {
		init();
		
		try {
			String szenario = szenarioPaths.get(1);
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
			int NUM_OF_SETS = 2;
			Integer[][] intArr = new Integer[NUM_OF_SETS][];

			for(int szenNum=0; szenNum<NUM_OF_SETS; szenNum++)
			{
				String szenario = szenarioPaths.get(szenNum+1);
				Document theXMLDoc = new SAXBuilder().build(szenario);
	    		Element root = theXMLDoc.getRootElement();
	    		List<Element> ampelSets = root.getChildren("ampelset");
	    		int ampelSetsSize = ampelSets.size();
	    		
	    		ArrayList<Integer> intArrList = new ArrayList<Integer>();
	    		for(int i=0; i<ampelSetsSize; i++)
	    		{
	    			Element ampelSet = ampelSets.get(i);
	    			List<Element> ampeln = ampelSet.getChildren("ampel");
	    			for(int j=0; j<ampeln.size(); j++)
	    			{
	    				Element ampel = ampeln.get(j);
	    				intArrList.add( Integer.parseInt( ampel.getChildText("insgesamtewartezeit") ) );
	    			}
	    		}
	    		intArr[szenNum] = new Integer[intArrList.size()];
	    		for(int i=0; i<intArrList.size(); i++)
	    		{
	    			intArr[szenNum][i] = intArrList.get(i);
	    		}
    		}
    		
    		String[] strArr = new String[NUM_OF_SETS];
    		for(int i=0; i<NUM_OF_SETS; i++)
    		{
    			strArr[i] = "Szenario " + i;
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
			Document theXMLDoc = new SAXBuilder().build(persistent);
			
			//the <statistics> ... </statistics> node
    		Element root = theXMLDoc.getRootElement();
    		
    		List<Element> runs = root.getChildren("scenario");
    		Integer[][] intArr = new Integer[runs.size()][];

    		for(int i=0; i<runs.size(); i++)
    		{
    			intArr[i] = new Integer[1];
    			String warteZeitText = runs.get(i).getChildText("durschnittswartezeit");
    			Integer warteZeit = Integer.parseInt( warteZeitText );
    			intArr[i][0] = warteZeit;
    		}
    		
    		String[] strArr = new String[runs.size()];
    		for(int i=0; i<runs.size(); i++)
    		{
    			strArr[i] = "Durchlauf  " + i;
    		}
    		
    		Color[] c = new Color[4];
    		c[0] = Color.decode("#FFE376");
    		
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
