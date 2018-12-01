package io;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import model.Ampel;
import model.Auto;
import model.EndStation;
import model.ProcessStation;
import model.Station;
import model.SteuerInfo;
import model.SteuerLogik;
import model.SynchronizedQueue;
import model.Waypoint;
import model.WellenGenerator;
import view.QueueViewJPanel;
import view.QueueViewText;

/**
 * This is an abstract factory that creates instances
 * of actor types like objects, stations and their queues 
 * 
 * @author Jaeger, Schmidt, modified by Team 4
 * @version 2018-11-24
 */
public class Factory {
	
	/** Scenario Folder */
	private static String scenarioFolder;
	
//	/** the objects XML data file (not in use)*/
//	private static String theObjectDataFile = "xml/"+scenarioFolder+"/object.xml"; 
	
	/** the Ampeln XML data file */
	private static String theAmpelnDataFile = "xml/"+scenarioFolder+"/ampeln.xml";
	
	/** the ProcessStations XML data file */
	private static String theProcessStationDataFile = "xml/"+scenarioFolder+"/processstation.xml"; 
	
	/** the SteuerLogik XML data file */
	private static String theSteuerLogikDataFile = "xml/"+scenarioFolder+"/steuerlogik.xml"; 
	
//	/** the start station XML data file (not in use)*/
//	private static String theStartStationDataFile = "xml/"+scenarioFolder+"/startstation.xml"; 
	
	/** the end station XML data file */
	private static String theEndStationDataFile = "xml/"+scenarioFolder+"/endstation.xml"; 
	
	/** the Auto XML data file */
	private static String theAutoDataFile = "xml/"+scenarioFolder+"/auto.xml"; 
	
	/** the Wellengenerator XML data file */
	private static String theWellengeneratorDataFile = "xml/"+scenarioFolder+"/wellengenerator.xml"; 
	
	/** the Waypoint XML data file */
	private static String theWaypointDataFile = "xml/"+scenarioFolder+"/waypoint.xml";

	
	/**
     * set Folder
     * create the actors for the starting scenario
     * 
     */
	public static void createStartScenario(String folder){
		
		/*NOTE: The start station must be created first,
		* because the objects constructor puts the objects into the start stations outgoing queue
		*/ 
		
		scenarioFolder = folder;
		//createStartStation();
		createWellenGenerator();
		//createObjects();
		createAmpeln();
		
		//createProcessStations();
		createEndStation();
		createSteuerLogik();
		createWaypoint();
		createAutos();
	}

	 /**
     * create the Wellengenerator
     * 
     * Liest Daten aus dem XML FILE wellengenerator.xml aus und
     * ruft die Methode create() des Wellengenerators auf um den Wellengenerator
     * zu erstellen
     * 
     */
     private static void createWellenGenerator(){
    	
    	try {
    		
    		
    		//read the information from the XML file into a JDOM Document
    		Document theXMLDoc = new SAXBuilder().build(theWellengeneratorDataFile);
    		
    		//the <settings> ... </settings> node
    		Element root = theXMLDoc.getRootElement();
    		
    		
    		//get all the Wellengeneratoren into a List 
     		List <Element> wellengeneratoren = root.getChildren("wellenGenerator");
    		
    		
     		for (Element theWellengenerator : wellengeneratoren) {
    			
    			
    			String label = null;
     			int xPos = 0;
     			int yPos = 0;
     			String image = null;
     			int wellengroese;
    		
    		
    		//get the label
    		label = theWellengenerator.getChildText("label");
    		
    		//get the position
    		xPos = Integer.parseInt(theWellengenerator.getChildText("x_position"));
    		yPos = Integer.parseInt(theWellengenerator.getChildText("y_position"));
    		
    		//the <view> ... </view> node
    		Element viewGroup = theWellengenerator.getChild("view");
    		
    		//get the Wellengroese
    		wellengroese = Integer.parseInt(theWellengenerator.getChildText("wellenGroesse"));
    		
    		
    		// the image
    		image = viewGroup.getChildText("image");
    		
    		//CREATE THE INQUEUE
    		//the <inqueue> ... </inqueue> node
    		Element inqueueGroup = theWellengenerator.getChild("inqueue");
    		
    		// the positions
    		int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
    		int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));
    		
    		//create the inqueue
    		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
    		//CREATE THE OUTQUEUE
    		//the <outqueue> ... </outqueue> node
    		Element outqueueGroup = theWellengenerator.getChild("outqueue");
    		
    		// the positions
    		int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
    		int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));
    		
    		//create the outqueue
    		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
    		    		
    		//creating a new StartStation object
    		WellenGenerator.create(label, theInQueue, theOutQueue, xPos, yPos, image,wellengroese);
    		}
    	
    	} catch (JDOMException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
     }
    
     private static void createWaypoint()
     {
    	 try {
      		//read the information from the XML file into a JDOM Document
      		Document theXMLDoc = new SAXBuilder().build(theWaypointDataFile);
      		
      		//the <settings> ... </settings> node
      		Element root = theXMLDoc.getRootElement();
      		
      		//get all the stations into a List object
      		List <Element> stations = root.getChildren("waypoint");
      		
      		//separate every JDOM "station" Element from the list and create Java Station objects
      		for (Element station : stations) {
      			
      			// data variables:
      			String label = null;
      			int xPos = 0;
      			int yPos = 0;
      			String image = null;
      			    			
      			// read data
      			label = station.getChildText("label");
          		xPos = Integer.parseInt(station.getChildText("x_position"));
          		yPos = Integer.parseInt(station.getChildText("y_position"));
          		        		
          		//the <view> ... </view> node
          		Element viewGroup = station.getChild("view");
          		// read data
          		image = viewGroup.getChildText("image");
          		        		
          		//CREATE THE INQUEUES
          		Element inqueue = station.getChild("inqueue");
          		int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
          		int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));
          		SynchronizedQueue theInqueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);
          		
          		//CREATE THE OUTQUEUES
          		Element outqueue = station.getChild("outqueue");
          		int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
          		int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));
          		SynchronizedQueue theOutqueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosOutQueue, yPosOutQueue);
          		
          		//creating a new Station object
          		Waypoint.create(label, theInqueue, theOutqueue, xPos, yPos, image);
          	}
      	}
    	catch (JDOMException e) {
 				e.printStackTrace();
 		} catch (IOException e) {
 				e.printStackTrace();
 		}
     }
	 
	 /**
      * create the Autos
      * 
      * Liest Daten aus dem XML FILE auto.xml aus und
      * ruft die Methode create() von Auto auf mehrere Autos
      * zu erstellen
      * 
      */
     private static void createAutos(){
    	
    	try {
    		
    		
		
    		//read the information from the XML file into a JDOM Document
    		Document theXMLDoc = new SAXBuilder().build(theAutoDataFile);
    		
    		//the <settings> ... </settings> node, this is the files root Element
    		Element root = theXMLDoc.getRootElement();
    		
    		//get all the objects into a List object
    		List <Element> allAutos = root.getChildren("auto");
    		
    		//separate every JDOM "object" Element from the list and create Java TheObject objects
    		for (Element dasauto : allAutos) {
    			
    			// data variables:
    			String label = null;
    			int processtime = 0;
    			int speed = 0;
    			int xPos = 0;
     			int yPos = 0;
    			String image = null;
    			    			
    			// read data
    			label = dasauto.getChildText("label");
    			processtime = Integer.parseInt(dasauto.getChildText("processtime"));
    			speed = Integer.parseInt(dasauto.getChildText("speed"));
        		        		
        		//the <view> ... </view> node
        		Element viewGroup = dasauto.getChild("view");
        		// read data
        		image = viewGroup.getChildText("image");
        		
        		//get all the stations, where the object wants to go to
        		//the <sequence> ... </sequence> node
        		Element sequenceGroup = dasauto.getChild("sequence");
        		
        		List <Element> allStations = sequenceGroup.getChildren("station");
        		
        		//get the elements into a list
        		ArrayList<String> stationsToGo = new ArrayList<String>();
        		
        		for (Element theStation : allStations) {
        			
        			stationsToGo.add(theStation.getText());
        			
        			
        		
        			for(Station aktuelleStation : Station.getAllStations()) {
        				
        				
        				
        			if(theStation.getText().equals(aktuelleStation.getLabel()) && aktuelleStation.getClass() == WellenGenerator.class) {
        				
        				xPos = aktuelleStation.getXPos();
        				yPos = aktuelleStation.getYPos();
        				
        			}
        			}
        			
        		}
        	  		
        		//creating a new TheObject object
        		Auto.create(label, stationsToGo, processtime, speed, xPos, yPos, image);
        		
			}
    	
    	} catch (JDOMException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
    }
	
	 /**
      * create the SteuerLogik
      * 
      * Liest Daten aus dem XML FILE steuerlogik.xml aus und
      * ruft die Methode create() von SteuerLogik auf um mehrere Autos
      * zu erstellen
      * 
      */
     private static void createSteuerLogik()
     {
    	 
    	try {
    		
    		//read the information from the XML file into a JDOM Document
    		Document theXMLDoc = new SAXBuilder().build(theSteuerLogikDataFile);
    		
    		//the <settings> ... </settings> node
    		Element root = theXMLDoc.getRootElement();
    		
    		//get the steuerlogik into a List object
    		Element steuerLogik = root.getChild("steuerLogik");
    		
    		//get the label
    		String label = steuerLogik.getChildText("label");
    		
    		//get the position
    		int xPos = Integer.parseInt(steuerLogik.getChildText("x_position"));
    		int yPos= Integer.parseInt(steuerLogik.getChildText("y_position"));
    		
    		ArrayList<ArrayList<Ampel>> amp = new ArrayList<ArrayList<Ampel>>();
    		ArrayList<Long> rot = new ArrayList<Long>();
    		ArrayList<Long> gruen = new ArrayList<Long>();
			ArrayList<ArrayList<WellenGenerator>> wel = new ArrayList<ArrayList<WellenGenerator>>();
			ArrayList<Long> welTime = new ArrayList<Long>();
			
			//get all info necessary for the just above things
			{
				List<Element> ampelSets = steuerLogik.getChildren("ampelSet");
				for (Element ampelSet : ampelSets)
				{
					String rotPhasenString = ampelSet.getChildText("rotPhase");
					rot.add( Long.parseLong( rotPhasenString ) );
					
					String gruenPhasenString = ampelSet.getChildText("gruenPhase");
					gruen.add( Long.parseLong( gruenPhasenString ) );
					
					ArrayList<Ampel> ampelnInSet = new ArrayList<Ampel>();
					
					List<Element> ampelLabels = ampelSet.getChildren("ampelLabel");
					for(Element ampelLabel : ampelLabels)
					{
						String ampel = ampelLabel.getText();
						ampelnInSet.add( Ampel.getAmpelByLabel(ampel) );
					}
					
					amp.add(ampelnInSet);
				}
				
				List<Element> wellenGeneratorSets = steuerLogik.getChildren("wellenGeneratorSet");
				for (Element wellenGeneratorSet : wellenGeneratorSets)
				{
					ArrayList<WellenGenerator> tempWelEle = new ArrayList<WellenGenerator>();
					
					String wellenZeitPunktString = wellenGeneratorSet.getChildText("wellenZeitpunkt");
					welTime.add( Long.parseLong( wellenZeitPunktString ) );						

					List<Element> wellenGeneratoren = wellenGeneratorSet.getChildren("wellenGenerator");
					for(Element wellenGenerator : wellenGeneratoren)
					{
						String wellenGeneratorLabel = wellenGenerator.getChildText("wellenGeneratorLabel");
						//System.out.println(wellenGeneratorLabel);
						//System.out.println( WellenGenerator.getWellenGeneratorByLabel(wellenGeneratorLabel) );
						tempWelEle.add( WellenGenerator.getWellenGeneratorByLabel(wellenGeneratorLabel) );
					}
					
					wel.add(tempWelEle);
				}
			}
			SteuerInfo info = new SteuerInfo(amp, gruen, wel, welTime);
			//System.out.println(info.getWellenGeneratorSet(0).toString());
    		
    		//creating a new StartStation object
    		SteuerLogik.create(label, xPos, yPos, info);//rotPhase, gruenPhase, ampel, wellenZeitPunkt, wellenGenerator);
    	    
    	
    	} catch (JDOMException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
     }
	
//	/**
//     * create the start station
//     * 
//     */
//     private static void createStartStation(){
//    	
//    	try {
//    		
//    		//read the information from the XML file into a JDOM Document
//    		Document theXMLDoc = new SAXBuilder().build(theStartStationDataFile);
//    		
//    		//the <settings> ... </settings> node
//    		Element root = theXMLDoc.getRootElement();
//    		
//    		//get the start_station into a List object
//    		Element startStation = root.getChild("start_station");
//    		
//    		//get the label
//    		String label = startStation.getChildText("label");
//    		    		    		
//    		//get the position
//    		XPOS_STARTSTATION = Integer.parseInt(startStation.getChildText("x_position"));
//    		YPOS_STARTSTATION = Integer.parseInt(startStation.getChildText("y_position"));
//    		
//    		//the <view> ... </view> node
//    		Element viewGroup = startStation.getChild("view");
//    		// the image
//    		String image = viewGroup.getChildText("image");
//    		
//    		//CREATE THE INQUEUE
//    		//the <inqueue> ... </inqueue> node
//    		Element inqueueGroup = startStation.getChild("inqueue");
//    		
//    		// the positions
//    		int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
//    		int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));
//    		
//    		//create the inqueue
//    		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
//    		
//    		//CREATE THE OUTQUEUE
//    		//the <outqueue> ... </outqueue> node
//    		Element outqueueGroup = startStation.getChild("outqueue");
//    		
//    		// the positions
//    		int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
//    		int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));
//    		
//    		//create the outqueue
//    		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
//    		    		
//    		//creating a new StartStation object
//    		StartStation.create(label, theInQueue, theOutQueue, XPOS_STARTSTATION, YPOS_STARTSTATION, image);
//    	    
//    	
//    	} catch (JDOMException e) {
//				e.printStackTrace();
//		} catch (IOException e) {
//				e.printStackTrace();
//		}
//     }
	
//	/**
//     * create some objects out of the XML file
//     * 
//     */
//     private static void createObjects(){
//    	
//    	try {
//		
//    		//read the information from the XML file into a JDOM Document
//    		Document theXMLDoc = new SAXBuilder().build(theObjectDataFile);
//    		
//    		//the <settings> ... </settings> node, this is the files root Element
//    		Element root = theXMLDoc.getRootElement();
//    		
//    		//get all the objects into a List object
//    		List <Element> allObjects = root.getChildren("object");
//    		
//    		//separate every JDOM "object" Element from the list and create Java TheObject objects
//    		for (Element theObject : allObjects) {
//    			
//    			// data variables:
//    			String label = null;
//    			int processtime = 0;
//    			int speed = 0;
//    			String image = null;
//    			    			
//    			// read data
//    			label = theObject.getChildText("label");
//    			processtime = Integer.parseInt(theObject.getChildText("processtime"));
//    			speed = Integer.parseInt(theObject.getChildText("speed"));
//        		        		
//        		//the <view> ... </view> node
//        		Element viewGroup = theObject.getChild("view");
//        		// read data
//        		image = viewGroup.getChildText("image");
//        		
//        		//get all the stations, where the object wants to go to
//        		//the <sequence> ... </sequence> node
//        		Element sequenceGroup = theObject.getChild("sequence");
//        		
//        		List <Element> allStations = sequenceGroup.getChildren("station");
//        		
//        		//get the elements into a list
//        		ArrayList<String> stationsToGo = new ArrayList<String>();
//        		
//        		for (Element theStation : allStations) {
//        			
//        			stationsToGo.add(theStation.getText());
//        			
//        		}
//        	  		
//        		//creating a new TheObject object
//        		TheObject.create(label, stationsToGo, processtime, speed, XPOS_STARTSTATION, YPOS_STARTSTATION, image);
//        		
//			}
//    	
//    	} catch (JDOMException e) {
//				e.printStackTrace();
//		} catch (IOException e) {
//				e.printStackTrace();
//		}
//    }
    
	 /**
      * create the Ampels
      * 
      * Liest Daten aus dem XML FILE ampel.xml aus und
      * ruft die Methode create() von Ampel auf um mehrere Ampeln
      * zu erstellen
      * 
      */
      private static void createAmpeln(){
     	
     	try {
     		
     		//read the information from the XML file into a JDOM Document
     		Document theXMLDoc = new SAXBuilder().build(theAmpelnDataFile);
     		
     		//the <settings> ... </settings> node
     		Element root = theXMLDoc.getRootElement();
     		
     		//get all the stations into a List object
     		List <Element> stations = root.getChildren("station");
     		
     		//separate every JDOM "station" Element from the list and create Java Station objects
     		for (Element station : stations) {
     			
     			// data variables:
     			String label = null;
     			int xPos = 0;
     			int yPos = 0;
     			String image = null;
     			    			
     			// read data
     			label = station.getChildText("label");
         		xPos = Integer.parseInt(station.getChildText("x_position"));
         		yPos = Integer.parseInt(station.getChildText("y_position"));
         		        		
         		//the <view> ... </view> node
         		Element viewGroup = station.getChild("view");
         		// read data
         		image = viewGroup.getChildText("image");
         		        		
         		//CREATE THE INQUEUES
         		Element inqueue = station.getChild("inqueue");
         		int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
         		int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));
         		SynchronizedQueue theInqueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue);
         		
         		//CREATE THE OUTQUEUES
         		Element outqueue = station.getChild("outqueue");
         		int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
         		int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));
         		SynchronizedQueue theOutqueue = SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosOutQueue, yPosOutQueue);
         		
         		//creating a new Station object
         		Ampel.create(label, theInqueue, theOutqueue, xPos, yPos, image);
         		
 			}
     		
     	
     	} catch (JDOMException e) {
 				e.printStackTrace();
 		} catch (IOException e) {
 				e.printStackTrace();
 		}
     	
     }
     
    /**
     * create some process stations out of the XML file
     * 
     */
     private static void createProcessStations(){
    	
    	try {
    		
    		//read the information from the XML file into a JDOM Document
    		Document theXMLDoc = new SAXBuilder().build(theProcessStationDataFile);
    		
    		//the <settings> ... </settings> node
    		Element root = theXMLDoc.getRootElement();
    		
    		//get all the stations into a List object
    		List <Element> stations = root.getChildren("station");
    		
    		//separate every JDOM "station" Element from the list and create Java Station objects
    		for (Element station : stations) {
    			
    			// data variables:
    			String label = null;
    			double troughPut = 0;
    			int xPos = 0;
    			int yPos = 0;
    			String image = null;
    			    			
    			// read data
    			label = station.getChildText("label");
    			troughPut = Double.parseDouble(station.getChildText("troughput"));
        		xPos = Integer.parseInt(station.getChildText("x_position"));
        		yPos = Integer.parseInt(station.getChildText("y_position"));
        		        		
        		//the <view> ... </view> node
        		Element viewGroup = station.getChild("view");
        		// read data
        		image = viewGroup.getChildText("image");
        		        		
        		//CREATE THE INQUEUES
        		
        		//get all the inqueues into a List object
        		List <Element> inqueues = station.getChildren("inqueue");
        		
        		//create a list of the stations inqueues 
        		ArrayList<SynchronizedQueue> theInqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created inqueues
        		
        		for (Element inqueue : inqueues) {
        			
        			int xPosInQueue = Integer.parseInt(inqueue.getChildText("x_position"));
            		int yPosInQueue = Integer.parseInt(inqueue.getChildText("y_position"));
            		
            		//create the actual inqueue an add it to the list
            		theInqueues.add(SynchronizedQueue.createQueue(QueueViewJPanel.class, xPosInQueue, yPosInQueue));
            	}
        		        		
        		//CREATE THE OUTQUEUES
        		
        		//get all the outqueues into a List object
        		List <Element> outqueues = station.getChildren("outqueue");
        		
        		//create a list of the stations outqueues 
        		ArrayList<SynchronizedQueue> theOutqueues = new ArrayList<SynchronizedQueue>(); //ArrayList for the created outqueues
        		
        		for (Element outqueue : outqueues) {
        			
        			int xPosOutQueue = Integer.parseInt(outqueue.getChildText("x_position"));
            		int yPosOutQueue = Integer.parseInt(outqueue.getChildText("y_position"));
            		
            		//create the actual outqueue an add it to the list
            		theOutqueues.add(SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue));
            	}
        		
        		//creating a new Station object
        		ProcessStation.create(label, theInqueues, theOutqueues, troughPut, xPos, yPos, image);
        		
			}
    		
    	
    	} catch (JDOMException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
    	
    }
    
     /**
     * create the end station
     * 
     */
     private static void createEndStation(){
    	
    	try {
    		
    		//read the information from the XML file into a JDOM Document
    		Document theXMLDoc = new SAXBuilder().build(theEndStationDataFile);
    		
    		//the <settings> ... </settings> node
    		Element root = theXMLDoc.getRootElement();
    		
    		//get the end_station into a List object
    		Element endStation = root.getChild("endStation");
    		
    		//get label
    		String label = endStation.getChildText("label");
    		    		    		
    		//position
    		int xPos = Integer.parseInt(endStation.getChildText("x_position"));
    		int yPos = Integer.parseInt(endStation.getChildText("y_position"));
    		
    		//the <view> ... </view> node
    		Element viewGroup = endStation.getChild("view");
    		// the image
    		String image = viewGroup.getChildText("image");
    		
    		//CREATE THE INQUEUE
    		//the <inqueue> ... </inqueue> node
    		Element inqueueGroup = endStation.getChild("inqueue");
    		
    		// the positions
    		int xPosInQueue = Integer.parseInt(inqueueGroup.getChildText("x_position"));
    		int yPosInQueue = Integer.parseInt(inqueueGroup.getChildText("y_position"));
    		
    		//create the inqueue
    		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
    		
    		//CREATE THE OUTQUEUE
    		//the <outqueue> ... </outqueue> node
    		Element outqueueGroup = endStation.getChild("outqueue");
    		
    		// the positions
    		int xPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("x_position"));
    		int yPosOutQueue = Integer.parseInt(outqueueGroup.getChildText("y_position"));
    		
    		//create the outqueue
    		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
    		
    		//creating a new EndStation object
    		EndStation.create(label, theInQueue, theOutQueue, xPos, yPos, image);
    	    
    	
    	} catch (JDOMException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
     }
     
     /**
      * get for Scenario Folder
      * @return scenarioFolder
      */
     
     public String getScenario() {
    	 return scenarioFolder;
     }
        
}
