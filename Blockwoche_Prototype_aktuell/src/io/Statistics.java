package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import controller.Simulation;
import model.Ampel;
import model.Auto;
import model.Station;

/**
 * A class for printing statistics
 * 
 * @author Jaeger, Schmidt, edited by Team 4
 * @version  11-2018
 * original written on 2015-11-18
 */
public class Statistics {

	private static String buffer;
	
	/** The XML Fiel for Auto Statistics (in every Scenario Folder) */
	private static String theAutoXmlFile = Simulation.getFolder() +"/" + Simulation.getScenario() + "/autostatistics.xml";
	/** The XML Fiel for Ampel Statistics (in every Scenario Folder) */
	private static String theAmpelXmlFile = Simulation.getFolder() +"/" + Simulation.getScenario() + "/ampelstatistics.xml";

	/**
	 * appends the given String to the buffer
	 *
	 * @param message the message to append
	 */
	public static void update(String message) {

		buffer = buffer + message + "\n";
	}

	/**
	 * writes the given String to console
	 *
	 * @param message the message to write to console
	 */
	public static void show(String message) {

		System.out.println(message);

	}

	/**
	 * Writes Statistics for Auto in a XML file
	 * written data: 
	 * -ampelname 
	 * -Wartezeit
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void writeAutoStatistics() {
		// the new JDOM XML document
		Document theAutoXmlDoc = new Document();

		// set the documents XML root element
		Element rootXMLElement = new Element("statistics");
		theAutoXmlDoc.setRootElement(rootXMLElement);

		// go through the cars list
		// go through the cars list
		for (Auto auto : Auto.getAlleAutos()) {

			// create a new XML Element for auto and add it below the root XML Element
			Element autoXMLElement = new Element("auto");
			rootXMLElement.addContent(autoXMLElement);

			// System.out.println("Stats print " + auto.getMessDaten().size());

			// go through the cars list
			for (ArrayList<Object> station : auto.getMessDaten()) {
				// create a new XML Element for auto and add it below the root XML Element
				Element ampelXMLElement = new Element("ampel");
				autoXMLElement.addContent(ampelXMLElement);

				// create new XML Elements for ampelname,
				// and add the new Element as child of the car XML Element

				// System.out.println(((String)
				// auto.getMessDaten().get(auto.getMessDaten().size()-1).get(0) ));

				ampelXMLElement.addContent(new Element("ampelname")
						.setText(((String) auto.getMessDaten().get(auto.getMessDaten().size() - 1).get(0))));
				ampelXMLElement.addContent(new Element("wartezeit")
						.setText("" + auto.getMessDaten().get(auto.getMessDaten().size() - 1).get(1)));
			}
		}

		try {
			// the JDOM XML document is complete
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

			// Write the XML File
			xmlOutputter.output(theAutoXmlDoc, new FileOutputStream(theAutoXmlFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *  Writes Statistics for Ampel in a XML file
	 * 
	 * written data: 
	 * -ampelname 
	 * -insgesammteWartezeit 
	 * -autoanzahl
	 * -durchschnittswartezeit
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void writeAmpelStatistics() {
		Auswertung a = new Auswertung();
		a.auswerten();
		/*
		// the new JDOM XML document
		Document theAmpelXmlDoc = new Document();

		// set the documents XML root element
		Element rootXMLElement = new Element("statistics");
		theAmpelXmlDoc.setRootElement(rootXMLElement);

		// System.out.println("Stats print " + auto.getMessDaten().size());

		// go through the cars list
		for (Station station : Station.getAllStations()) {

			if (station.getClass() == Ampel.class) {

				long insgesammtWartezeit = 0;
				int autoAnzahl = 0;
				
				for (Auto auto : Auto.getAlleAutos()) {

					insgesammtWartezeit = insgesammtWartezeit + auto.getWarteZeit(station);
					autoAnzahl = autoAnzahl + ((int) (auto.getBesuchteAutos(station)));

				}

				// create a new XML Element for auto and add it below the root XML Element
				Element ampelXMLElement = new Element("ampel");
				rootXMLElement.addContent(ampelXMLElement);

				// create new XML Elements for ampelname,
				// and add the new Element as child of the car XML Element

				// System.out.println(((String)
				// auto.getMessDaten().get(auto.getMessDaten().size()-1).get(0) ));

				ampelXMLElement.addContent(new Element("ampelname").setText(station.getLabel()));
				ampelXMLElement.addContent(new Element("insgesamtewartezeit").setText("" + insgesammtWartezeit));
				ampelXMLElement.addContent(new Element("autoanzahl").setText("" + autoAnzahl));

				if (autoAnzahl == 0 || insgesammtWartezeit == 0) {
					ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("0"));
				} else {

					ampelXMLElement.addContent(new Element("durchschnittswartezeit")
							.setText("" + ((double) insgesammtWartezeit) / ((double) autoAnzahl)));

				}

			}

		}

		try {
			// the JDOM XML document is complete
			XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());

			// Write the XML File
			xmlOutputter.output(theAmpelXmlDoc, new FileOutputStream(theAmpelXmlFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 */
	}
}
