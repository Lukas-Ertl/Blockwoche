package io;

import java.io.File;
import java.io.FileInputStream;
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

import model.Ampel;
import model.Auto;
import model.Station;
import model.TheObject;



/**
 * A class for printing statistics
 * 
 * @author Jaeger, Schmidt, edited by Team 4
 * @version 2015-11-18 edited on 11-2018
 */
public class Statistics {
/*
 * TODO: getter in factory for folderName
 * TODO: change EndStation call to constructor
 * 
 * 
 * 
 */
	
	
	private static String buffer; 
	// The XML File for Auto Statistics
//	private static String theAutoXmlFile = "xml/"+folderName+"/autostatistics.xml"; 
//	// The XML File for Ampel Statistics
//	private static String theAmpelXmlFile = "xml/"+folderName+"/ampelstatistics.xml"; 
	
	
	/*TEMPORARY Remove if Factory is adapted*/
	private static String theAutoXmlFile = "xml/autostatistics.xml"; 
	// The XML File for Ampel Statistics
	private static String theAmpelXmlFile = "xml/ampelstatistics.xml"; 
	
	
	
	//The XML File for Comparison between simulations
	private static String theCompareXmlFile = "xml/comparestatistics.xml";
	// The active Folder Name
	private static String folderName;
	
	
	/**
	 * Constructor 
	 * gets the Folder Name from Factory and calls the 3 write methods
	 */
	public Statistics() {
		
		
//		folderName = Factory.getFileName();
		writeAutoStatistics();
		writeAmpelStatistics();
		writeCompareStatistics();
	}
	
	
	/** appends the given String to the buffer
	 *
	 * @param message the message to append
	 */
	public static void update(String message) {
		
		buffer = buffer + message + "\n";
	}
	
	/** writes the given String to console
	 *
	 * @param message the message to write to console
	 */
	public static void show(String message) {
		
		System.out.println(message);
		
	}
	
	
	/**
	 * Created/Edited by Team 4
	 * writes the statistics for Auto in a XML File
	 * 
	 * Written Data:
	 * - amplename
	 * - Wartezeit
	 * 
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void writeAutoStatistics(){
		
		
		//the new JDOM XML document 
		Document theAutoXmlDoc = new Document();
		
		//set the documents XML root element  
		Element rootXMLElement = new Element("statistics");
		theAutoXmlDoc.setRootElement(rootXMLElement);
		
		//go through the cars list
		//go through the cars list
		for(Auto auto : Auto.getAlleAutos()){
			
			//create a new XML Element for auto and add it below the root XML Element
			Element autoXMLElement = new Element("auto"); 
			rootXMLElement.addContent(autoXMLElement);
			
			
			//System.out.println("Stats print " + auto.getMessDaten().size());
			
			//go through the cars list
			for(ArrayList<Object> station : auto.getMessDaten()){

					//create a new XML Element for auto and add it below the root XML Element
				Element ampelXMLElement = new Element("ampel"); 
				autoXMLElement.addContent(ampelXMLElement);
					
				//create new XML Elements for ampelname,
				//and add the new Element as child of the car XML Element
				
			//	System.out.println(((String)   auto.getMessDaten().get(auto.getMessDaten().size()-1).get(0)    ));
				
				ampelXMLElement.addContent(new Element("ampelname").setText(((String)   auto.getMessDaten().get(auto.getMessDaten().size()-1).get(0)    )));
				ampelXMLElement.addContent(new Element("wartezeit").setText( "" +   auto.getMessDaten().get(auto.getMessDaten().size()-1).get(1) ));
			}

		}
		
		
		 try {
	        	//the JDOM XML document is complete
	        	XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
	        	
	        	//Write the XML File
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
	 * Created/Edited by Team 4
	 * writes the statistics for Ampel in a XML File
	 * 
	 * 
	 * Written Data:
	 * -ampelname
	 * -insgesammte Wartezeit
	 * -autoanzahl
	 * -durchschnittswartezeit
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void writeAmpelStatistics(){
		
		
		//the new JDOM XML document 
		Document theAmpelXmlDoc = new Document();
		
		//set the documents XML root element  
		Element rootXMLElement = new Element("statistics");
		theAmpelXmlDoc.setRootElement(rootXMLElement);

			//System.out.println("Stats print " + auto.getMessDaten().size());
			
			//go through the cars list
			for(Station station : Station.getAllStations()){

				if (station.getClass() == Ampel.class) {

					long insgesammtWartezeit = 0;
					int autoAnzahl = 0;
					for(Auto auto : Auto.getAlleAutos()){
	
						insgesammtWartezeit = insgesammtWartezeit + auto.getWarteZeit(station);
						autoAnzahl = autoAnzahl + ((int)(auto.getBesuchteAutos(station)));
						
					}

					//create a new XML Element for auto and add it below the root XML Element
				Element ampelXMLElement = new Element("ampel"); 
				rootXMLElement.addContent(ampelXMLElement);
					
				//create new XML Elements for ampelname,
				//and add the new Element as child of the car XML Element
				
			//	System.out.println(((String)   auto.getMessDaten().get(auto.getMessDaten().size()-1).get(0)    ));
				
				ampelXMLElement.addContent(new Element("ampelname").setText(station.getLabel() ));
				ampelXMLElement.addContent(new Element("insgesamtewartezeit").setText(""+insgesammtWartezeit ));
				ampelXMLElement.addContent(new Element("autoanzahl").setText("" + autoAnzahl));
				
				if(autoAnzahl == 0 || insgesammtWartezeit == 0) {
				ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("0"));
				}else {
					
					ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("" + ((double)insgesammtWartezeit) / ((double)autoAnzahl)));
					
					
					}

				}
				
			}
		
		 try {
	        	//the JDOM XML document is complete
	        	XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
	        	
	        	//Write the XML File
				xmlOutputter.output(theAmpelXmlDoc, new FileOutputStream(theAmpelXmlFile));
			
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	/**
	 * Created/Edited by Team 4
	 * writes the statistics for Auto in a XML File
	 * 
	 * Written Data:
	 * complete waiting time of all Autos on all Ampeln
	 */
	private static void writeCompareStatistics() {
		s
		long insgesammtWartezeit = 0;	
		
		for(Station station : Station.getAllStations()){

			if (station.getClass() == Ampel.class) {

				
				for(Auto auto : Auto.getAlleAutos()){

					insgesammtWartezeit = insgesammtWartezeit + auto.getWarteZeit(station);
					
				}
			}
		}
		
		
		
		Element compareXMLElement = new Element("statistics"); 
		compareXMLElement.addContent(compareXMLElement);
		compareXMLElement.addContent(new Element("insgesamtewartezeit").setText(""+insgesammtWartezeit ));
				
				 try {
			        	//the JDOM XML document is complete
			        	XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
			        	
			        	//Write the XML File
						xmlOutputter.output(theStatisticsXmlDoc, new FileOutputStream(theCompareXmlFile));
					
			        } catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}
	

	

	
	
}
