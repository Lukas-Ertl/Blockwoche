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

import io.Auswertung.StatistikHolder;
import io.Auswertung.StatistikHolderAmpel;
import model.Ampel;
import model.Auto;
import model.Station;


/*
 * Reads the old simulation statistic data and adds the current
 * 
 * @author Team 4
 * @version 11-2018
 */
public class PersistentFile {

	private static String thePersistentStatistikDatafile = "xml/persistentstatistics.xml";
	private ArrayList<DataHolder> statisticList = new ArrayList<DataHolder>();
	
	/*
	 * Constructor for PersistentFile 
	 * @author Team 4
	 */
	public PersistentFile() {
		readXML();
		writeXML();
	}
	
	
	/* Calculates the average waiting time (moved to separate method for JUnit Bonusaufgabe)
	 * @author Team 4
	 * @return averageWaitingtime
	 */
	
	private long calculateAverageWaitingtime(long insgesamteWartezeit, int autoAnzahl) {
		
		
		return insgesamteWartezeit/autoAnzahl;
	}
		
	
	/*
	 * Reads the existing Data in the XML file and adds the Data from the current Simulation
	 * @author Team 4
	 */
	private void readXML() {
		try {
			
			// read the information from the XML file into a JDOM Document
			Document theXMLDoc = new SAXBuilder().build(thePersistentStatistikDatafile);
			// the <settings> ... </settings> node
			Element root = theXMLDoc.getRootElement();

			List<Element> statistiken = root.getChildren("scenario");

			for (Element theStatistik : statistiken) {
				

				String label = null;
				long wartezeit = 0;

				// get the label
				label = theStatistik.getChildText("scenario");

				// get the wartezeit
				wartezeit = Integer.parseInt(theStatistik.getChildText("durschnittswartezeit"));

				statisticList.add(new DataHolder(wartezeit, label));
			}
			//add the current statistic to the list
			long insgesammtWartezeit = 0;
			int autoAnzahl = 0;
			for (Station station : Station.getAllStations()) {

				if (station.getClass() == Ampel.class) {

					for (Auto auto : Auto.getAlleAutos()) {

						insgesammtWartezeit = insgesammtWartezeit + auto.getWarteZeit(station);
						autoAnzahl = autoAnzahl + ((int) (auto.getBesuchteAutos(station)));

					}
				}
			}
			
			statisticList.add(new DataHolder(calculateAverageWaitingtime(insgesammtWartezeit,autoAnzahl),Factory.getScenario()));
			
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
	}
		
	
	
	/*
	 * writes the Data into the XML File
	 * @author Team 4
	 */
	private void writeXML() {
		// the new JDOM XML document
				Document theStatistikAuswertunsXmlFile = new Document();

				// set the documents XML root element
				Element rootXMLElement = new Element("statistics");
				theStatistikAuswertunsXmlFile.setRootElement(rootXMLElement);

				for (int listPosition = 0; listPosition < statisticList.size(); listPosition++) {

					// create a new XML Element for the Scenario and add it below the root XML Element
					Element statisticXMLElement = new Element("scenario");
					rootXMLElement.addContent(statisticXMLElement);

					// create new XML Elements for ampelname,
					// and add the new Element as child of the car XML Element
					statisticXMLElement.addContent(new Element("scenario").setText("" + statisticList.get(listPosition).scenario));
					statisticXMLElement.addContent(new Element("durschnittswartezeit").setText("" + statisticList.get(listPosition).gesWartezeit));

					

				}

				try {
					// the JDOM XML document is complete
					XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

					// Write the XML File
					xmlOutputter.output(theStatistikAuswertunsXmlFile, new FileOutputStream(thePersistentStatistikDatafile));

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	/*
	 * sub Class for easier data handling
	 * @author Team 4
	 */
	protected class DataHolder{
		long gesWartezeit=0;
		String scenario="empty";
		
		protected DataHolder(long zeit, String name) {
			this.gesWartezeit = zeit;
			this.scenario = name;
		}
	}
}
