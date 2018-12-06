package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	/*
	 * use in merge others only for testing private static String
	 * theStatistikAuswertungdatafile = "xml/"+Factory.getFolder+"/auswertung.xml";
	 * private static String theAutoStatistikdatafile ="xml/"+Factory.getFolder+"/autostatistics.xml";
	 */

	private static String theStatistikAuswertungdatafile = "xml/auswertung.xml";
	private static String theAutoStatistikdatafile = "xml/autostatistics.xml";
	private ArrayList<StatistikHolder> autoList = new ArrayList<StatistikHolder>();
	private ArrayList<StatistikHolderAmpel> ampelList = new ArrayList<StatistikHolderAmpel>();

	public Auswertung() {
		readXML();
		writeXML();
	}

	/**
	 * reads the Data from a XML File
	 */
	private void readXML() {
		try {

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

				// get the position
				wartezeit = Integer.parseInt(ampelGroup.getChildText("wartezeit"));

				autoList.add(new StatistikHolder(label, wartezeit));
			}

			// sort data into ampelList so the data is per Ampel
			if (ampelList.size() == 0) {
				ampelList.add(new StatistikHolderAmpel(autoList.get(0).ampelName));
			}
			for (StatistikHolder auto : autoList) {
				for (int i = 0; i < ampelList.size(); i++) {
					if (!auto.isElementOfAmpelList(ampelList)) {
						ampelList.add(new StatistikHolderAmpel(auto.ampelName));
					}
				}
			}
			for (StatistikHolderAmpel ampel : ampelList) {
				for (StatistikHolder auto : autoList) {
					if (ampel.ampelName.equals(auto.ampelName)) {
						ampel.wartezeit += auto.wartezeit;
						ampel.anzAutos++;
					}
				}
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

		for (int listPosition = 0; listPosition < ampelList.size(); listPosition++) {

			int gesWartezeit = +ampelList.get(listPosition).wartezeit;
			int anzAutos = +ampelList.get(listPosition).anzAutos;
			String ampelName = ampelList.get(listPosition).ampelName;

			// create a new XML Element for auto and add it below the root XML Element
			Element ampelXMLElement = new Element("ampel");
			rootXMLElement.addContent(ampelXMLElement);

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
