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

import model.Ampel;
import model.Auto;
import model.Station;
import model.SynchronizedQueue;
import model.WellenGenerator;
import view.QueueViewText;

/**
 * Fuer Bonusaufgabe Einlesen der Statistik und bearbeiten dieser
 * @author Team 4
 */

public class Auswertung {
/*	use in merge others only for testing
	private static String theStatistikAuswertungdatafile = "xml/"+Factory.getFolder+"/auswertung.xml";
	private static String theAutoStatistikdatafile = "xml/"+Factory.getFolder+"/autostatistics.xml";
*/
	
	private static String theStatistikAuswertungdatafile = "xml/auswertung.xml";
	private static String theAutoStatistikdatafile = "xml/autostatistics.xml";
	private ArrayList<StatistikHolder> autoList = new ArrayList<StatistikHolder>();
	private ArrayList<StatistikHolder> ampelList = new ArrayList<StatistikHolder>();
	
	public Auswertung() {
		readXML();
		writeXML();
	}
	
	private void readXML() {
		try {
			
		
		//read the information from the XML file into a JDOM Document
		Document theXMLDoc = new SAXBuilder().build(theAutoStatistikdatafile);
		
		//the <settings> ... </settings> node
		Element root = theXMLDoc.getRootElement();
		
 		List <Element> statistiken = root.getChildren("auto");
		
 		for (Element theStatistik : statistiken) {
 			Element ampelGroup = theStatistik.getChild("ampel");
			
			String label = null;
 			int wartezeit = 0;
		
		//get the label
		label = ampelGroup.getChildText("ampelname");
		
		//get the position
		wartezeit = Integer.parseInt(ampelGroup.getChildText("wartezeit"));
		
		autoList.add(new StatistikHolder(label, wartezeit,1)); 
		}
 		
 		
 		ampelList.add(autoList.get(0));
 		for(int listPosition=1; listPosition<autoList.size();listPosition++) {
 			
 			for(int position = 0; position<ampelList.size();position++)
 			if(ampelList.get(position).ampelName.equals(autoList.get(listPosition).ampelName)) {
 				ampelList.get(position).addWartezeit(autoList.get(listPosition).wartezeit);
 				ampelList.get(position).addAuto();
 			}
 			else {
 			ampelList.add(autoList.get(listPosition));
 			}
 			
 		
 		
 		
 		}		
		
	} catch (JDOMException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}
	
	
	
	
	
	private void writeXML() {

				//the new JDOM XML document 
				Document theStatistikAuswertunsXmlFile= new Document();
				
				//set the documents XML root element  
				Element rootXMLElement = new Element("statistics");
				theStatistikAuswertunsXmlFile.setRootElement(rootXMLElement);

				for(int listPosition=0; listPosition<ampelList.size();listPosition++) {
		 			int gesWartezeit =+ ampelList.get(listPosition).wartezeit;
		 			int anzAutos =+ ampelList.get(listPosition).anzAutos;
		 			String ampelName = ampelList.get(listPosition).ampelName;
		 		
						//create a new XML Element for auto and add it below the root XML Element
						Element ampelXMLElement = new Element("ampel"); 
						rootXMLElement.addContent(ampelXMLElement);
							
						//create new XML Elements for ampelname,
						//and add the new Element as child of the car XML Element
						
						
						ampelXMLElement.addContent(new Element("ampelname").setText(ampelName));
						ampelXMLElement.addContent(new Element("insgesamtewartezeit").setText(""+gesWartezeit ));
						ampelXMLElement.addContent(new Element("autoanzahl").setText("" + anzAutos));
						
						if(anzAutos == 0 || gesWartezeit == 0) {
							ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("0"));
						}
						else 
						{
							
							ampelXMLElement.addContent(new Element("durchschnittswartezeit").setText("" + ((double)gesWartezeit) / ((double)anzAutos)));
			
						}

						
						}
						
					

				 try {
			        	//the JDOM XML document is complete
			        	XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
			        	
			        	//Write the XML File
						xmlOutputter.output(theStatistikAuswertunsXmlFile, new FileOutputStream(theStatistikAuswertungdatafile));
					
			        } catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
		
		
		
	
	
	protected class StatistikHolder {
		String ampelName;
		int wartezeit;
		int anzAutos;
		public  StatistikHolder(String name, int zeit,int anzAutos){
			ampelName = name;
			wartezeit = zeit;
			this.anzAutos = anzAutos;
		}
		protected void addWartezeit(int zeit) {
			wartezeit +=zeit;
		}
		protected void addAuto() {
			wartezeit ++;
		}
	}

}
