package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Reads the Statistics of the Autos, analyzes them and writes the average
 * waiting time and the amount of Autos per Ampel
 * 
 * @author Team 4
 * @version 11-2018
 */

public class Auswertung {
	/**
	 * use in merge others only for testing private static String
	 * 
	 */
	 private String theStatistikAuswertungdatafile = Factory.getFolder() +"/" + Factory.getScenario() + "/auswertung.xml";
	 private String theAutoStatistikdatafile = Factory.getFolder() + "/" + Factory.getScenario() + "/autostatistics.xml";
	 private String theSteuerLogikdatafile = Factory.getFolder() + "/" + Factory.getScenario() + "/steuerlogik.xml";
	 
	private ArrayList<StatistikHolder> autoList = new ArrayList<StatistikHolder>();
	private HashMap<String, ArrayList<StatistikHolderAmpel>> ampelMap = new HashMap<String, ArrayList<StatistikHolderAmpel>>();
	private HashMap<String, String> ampelSetsForNames = new HashMap<String, String>();
	
	/**
	 * Constructor for Auswertung
	 * @author Team 4
	 */
	public void auswerten() {
		readXML();
		writeXML();
	}

	/**
	 * reads the Data from a XML File
	 */
	private void readXML() {
		try {
			//open the XML and find out the sets for the ampeln
			Document theSetsXMLDoc = new SAXBuilder().build( theSteuerLogikdatafile );
			Element steuerLogikRoot = theSetsXMLDoc.getRootElement();
			Element steuerLogik = steuerLogikRoot.getChild("steuerLogik");
			List<Element> ampelSets = steuerLogik.getChildren("ampelSet");
			
			
			for(int i=0; i<ampelSets.size(); i++)
			{
				Element ampelSet = ampelSets.get(i);
				List<Element> ampeln = ampelSet.getChildren("ampelLabel");
				for(Element ampel : ampeln)
				{
					ampelSetsForNames.put(ampel.getText(), "ampelset " + i);
				}
			}

			for(String set : ampelSetsForNames.values())
			{
				ampelMap.put(set, new ArrayList<StatistikHolderAmpel>());
			}
			
			// read the information from the XML file into a JDOM Document
			Document theXMLDoc = new SAXBuilder().build(theAutoStatistikdatafile);

			// the <settings> ... </settings> node
			Element root = theXMLDoc.getRootElement();

			List<Element> statistiken = root.getChildren("auto");

			for (Element theStatistik : statistiken) {
				Element ampelGroup = theStatistik.getChild("ampel");

				String label = null;
				int wartezeit = 0;

				// get the label
				label = ampelGroup.getChildText("ampelname");

				// get the wartezeit
				wartezeit = Integer.parseInt(ampelGroup.getChildText("wartezeit"));

				autoList.add(new StatistikHolder(label, wartezeit));
			}
			
			ArrayList<StatistikHolderAmpel> tempAmpList = new ArrayList<StatistikHolderAmpel>();
			// sort data into ampelList so the data is per Ampel
			if (tempAmpList.size() == 0)
				tempAmpList.add(new StatistikHolderAmpel(autoList.get(0).ampelName));
			
			for (StatistikHolder auto : autoList) {
				for (int i = 0; i < tempAmpList.size(); i++) {
					if ( !auto.isElementOfAmpelList(tempAmpList) ) {
						tempAmpList.add(new StatistikHolderAmpel(auto.ampelName));
					}
				}
			}
			for (StatistikHolderAmpel ampel : tempAmpList) {
				for (StatistikHolder auto : autoList) {
					if (ampel.ampelName.equals(auto.ampelName)) {
						ampel.wartezeit += auto.wartezeit;
						ampel.anzAutos++;
					}
				}
			}

			for (StatistikHolderAmpel ampel : tempAmpList) {
				String ampelSetName = ampelSetsForNames.get(ampel.ampelName);
				ArrayList<StatistikHolderAmpel> ampelSetList = ampelMap.get( ampelSetName );
				ampelSetList.add(ampel);
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * writes Data collected in readXML, after analyzation into a file
	 * 
	 */
	private void writeXML() {
		// the new JDOM XML document
		Document theStatistikAuswertunsXmlFile = new Document();

		// set the documents XML root element
		Element rootXMLElement = new Element("statistics");
		theStatistikAuswertunsXmlFile.setRootElement(rootXMLElement);
		
		for (String ampelSet : ampelMap.keySet()) {
			
			Element set = new Element("ampelset").setText(ampelSet);
			rootXMLElement.addContent(set);
			
			for (int listPosition = 0; listPosition < ampelMap.get(ampelSet).size(); listPosition++) {
	
				int gesWartezeit = +ampelMap.get(ampelSet).get(listPosition).wartezeit;
				int anzAutos = +ampelMap.get(ampelSet).get(listPosition).anzAutos;
				String ampelName = ampelMap.get(ampelSet).get(listPosition).ampelName;
	
				// create a new XML Element for auto and add it below the root XML Element
				Element ampelXMLElement = new Element("ampel");
				set.addContent(ampelXMLElement);
	
				// create new XML Elements for ampelname,
				// and add the new Element as child of the car XML Element
	
				ampelXMLElement.addContent(new Element("ampelname").setText(ampelName));
				ampelXMLElement.addContent(new Element("insgesamtewartezeit").setText("" + gesWartezeit));
				ampelXMLElement.addContent(new Element("autoanzahl").setText("" + anzAutos));
	
				if (anzAutos == 0 || gesWartezeit == 0) {
					ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("0"));
				} else {
	
					ampelXMLElement.addContent(new Element("durchschnittswartezeit")
							.setText("" + ((double) gesWartezeit) / ((double) anzAutos)));
	
				}
	
			}
		}

		try {
			// the JDOM XML document is complete
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

			// Write the XML File
			xmlOutputter.output(theStatistikAuswertunsXmlFile, new FileOutputStream(theStatistikAuswertungdatafile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Data Holder
	 * 
	 * @author Team 4
	 *
	 */

	protected class StatistikHolder {
		String ampelName;
		int wartezeit = 0;
		int anzAutos = 0;

		public StatistikHolder(String name, int zeit) {
			this.ampelName = name;
			this.wartezeit = zeit;
		}

		protected boolean isElementOfAmpelList(ArrayList<StatistikHolderAmpel> vergList) {
			for (StatistikHolderAmpel verg : vergList) {
				if (this.ampelName.equals(verg.ampelName)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Data Holder
	 * 
	 * @author Team 4
	 *
	 */

	protected class StatistikHolderAmpel {
		String ampelName;
		int wartezeit = 0;
		int anzAutos = 0;

		public StatistikHolderAmpel(String name) {
			this.ampelName = name;
		}

	}

}
