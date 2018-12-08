package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import view.StackedBarChart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * New main Class to run multiple Scenarios / open graphs throught a GUI
 * Choose from JSON or XML scenarios per mouseclick
 * 
 * @author Team 4
 * @version 11-2018
 *
 */

public class Main {
	
	/** Filepath for the persistent statistics */
	static String persistent = "xml/persistentstatistics.xml";
	/** hashmap of scenario paths */
	static HashMap<Integer, String> szenarioPaths = new HashMap<Integer, String>();
	/** initialisation of the scenario path hashmap */
	static void init()
	{
		szenarioPaths.put(1, "SzenarienXML/Szenario 1/auswertung.xml");
		szenarioPaths.put(2, "SzenarienXML/Szenario 2/auswertung.xml");
	}

	/** GUI frame */
	static JFrame theFrame;

	/** Scenario Folder */
	private static String scenarioFolder;

	/**
	 * Shows the GUI to select from simulations and graphs
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Main.createAndShowGUI();
	}
	
	/**
	 * creates the frame and adds the buttons / listeners
	 */
	public static void createAndShowGUI() {
		ButtonListener xmlListener = new ButtonListener();
		GraphButtonListener graListen = new GraphButtonListener();
		
		Main.theFrame = new JFrame("Auswahlfeld");

		JPanel contentPanel = new JPanel(new FlowLayout());
		Main.theFrame.setContentPane(contentPanel);

		JButton xmlButton1 = new JButton("XML1");
		xmlButton1.addActionListener(xmlListener);
		JButton xmlButton2 = new JButton("XML2");
		xmlButton2.addActionListener(xmlListener);

		JButton jsonButton1 = new JButton("JSON1");
		jsonButton1.addActionListener(xmlListener);
		JButton jsonButton2 = new JButton("JSON2");
		jsonButton2.addActionListener(xmlListener);

		JButton graphOneButton = new JButton("Szenario 1 Graph");
		graphOneButton.addActionListener(graListen);
		JButton graphTwoButton = new JButton("Szenario Vergleich");
		graphTwoButton.addActionListener(graListen);
		JButton graphThreeButton = new JButton("Mehrfach Vergleichung");
		graphThreeButton.addActionListener(graListen);
		
		Main.theFrame.getContentPane().add(xmlButton1);
		Main.theFrame.getContentPane().add(xmlButton2);
		Main.theFrame.getContentPane().add(jsonButton1);
		Main.theFrame.getContentPane().add(jsonButton2);
		Main.theFrame.getContentPane().add(graphOneButton);
		Main.theFrame.getContentPane().add(graphTwoButton);
		Main.theFrame.getContentPane().add(graphThreeButton);
		Main.theFrame.pack();
		Main.theFrame.setSize(400, 200);
		Main.theFrame.setVisible(true);
		Main.theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

/**
 * Listener class to start simulations
 * (closes the selection frame upon starting the simulation to avoid errors) 
 * 
 * @author Team 4
 *
 */
class ButtonListener implements ActionListener {

	/** Scenario Folder */
	private static String scenarioFolder;

	/** checks which button was pressed and opens the associated simulation */
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("XML1")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "XML");
		} else if (e.getActionCommand().equals("XML2")) {
			ButtonListener.scenarioFolder = "Szenario 2";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "XML");
		} else if (e.getActionCommand().equals("JSON1")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "JSON");
		} else if (e.getActionCommand().equals("JSON2")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "JSON");
		}
		
		Main.theFrame.dispose();
	}
}

/**
 * Listener class to open graphs
 * 
 * @author Team 4
 *
 */
class GraphButtonListener implements ActionListener {

	/** Scenario Folder */
	private static String scenarioFolder;

	/** checks which button was pressed and opens the associated graph */
	public void actionPerformed(ActionEvent e) {
		Main.init();
		
		if (e.getActionCommand().equals("Szenario 1 Graph")) {
			
			try {
				String szenario = Main.szenarioPaths.get(1);
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
	    		} catch (StackedBarChart.InputArraysNotEquivalent exc) {
	    			exc.printStackTrace();
	    		}
				
			} catch (JDOMException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} else if (e.getActionCommand().equals("Szenario Vergleich")) {
			
			try {
				int NUM_OF_SETS = 2;
				Integer[][] intArr = new Integer[NUM_OF_SETS][];

				for(int szenNum=0; szenNum<NUM_OF_SETS; szenNum++)
				{
					String szenario = Main.szenarioPaths.get(szenNum+1);
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
	    		} catch (StackedBarChart.InputArraysNotEquivalent exc) {
	    			exc.printStackTrace();
	    		}
				
			} catch (JDOMException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} else if (e.getActionCommand().equals("Mehrfach Vergleichung")) {
			
			try {
				Document theXMLDoc = new SAXBuilder().build(Main.persistent);
				
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
	    		} catch (StackedBarChart.InputArraysNotEquivalent exc) {
	    			exc.printStackTrace();
	    		}
				
			} catch (JDOMException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
