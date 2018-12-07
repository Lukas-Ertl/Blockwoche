package io;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class FactoryJSON {
	
	/** Scenario Folder */
	private static String scenarioFolder;
	private static JSONObject jsonObject;
	
	
//	/** the objects json data file (not in use)*/
//	private static String theObjectDataFile = "SzenarienJSON/"+scenarioFolder+"/object.json"; 
	
	/** the Ampeln json data file */
	private static String theAmpelnDataFile = "SzenarienJSON/"+scenarioFolder+"/ampeln.json";

	
	/** the ProcessStations json data file */
	private static String theProcessStationDataFile = "SzenarienJSON/"+scenarioFolder+"/processstation.json"; 
	
	/** the SteuerLogik json data file */
	private static String theSteuerLogikDataFile = "SzenarienJSON/"+scenarioFolder+"/steuerlogik.json"; 
	
//	/** the start station json data file (not in use)*/
//	private static String theStartStationDataFile = "SzenarienJSON/"+scenarioFolder+"/startstation.json"; 
	
	/** the end station json data file */
	private static String theEndStationDataFile = "SzenarienJSON/"+scenarioFolder+"/endstation.json"; 
	
	/** the Auto json data file */
	private static String theAutoDataFile = "SzenarienJSON/"+scenarioFolder+"/auto.json"; 
	
	/** the Wellengenerator json data file */
	private static String theWellengeneratorDataFile = "SzenarienJSON/"+scenarioFolder+"/wellengenerator.json"; 
	
	/** the Waypoint json data file */
	private static String theWaypointDataFile = "SzenarienJSON/"+scenarioFolder+"/waypoint.json";

	
	
    /**
     * Singleton for a jsonObject.
     * If jsonObject is not initialized 
     * 	- load json file
     * 	- create new instance of JSON Object
     * 
     * @return
     * 		the JSONObject
     */
    public static JSONObject getJSONObject(String filepath){
		
        	try {
        		// load the JSON-File into a String
    			FileReader fr = new FileReader(filepath);
    			BufferedReader br = new BufferedReader(fr);
    			String json = "";
    			for(String line=""; line!=null; line = br.readLine())
    				json+=line;
    			br.close();
    			
    			// create a new JSON Object with the 
    	    	jsonObject = new JSONObject(json);
    	 
    	  
    	    	
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    	
    	return jsonObject;
    }
	
	
	
	
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
		setFilePath();
		//createStartStation();
		
		//createObjects();
		createWellenGenerator();
		createAmpeln();
		//createProcessStations();
		createEndStation();
		createSteuerLogik();
		//createWaypoint();
		//createAutos();
	}
	
	
	
	private static void setFilePath() {
		theAmpelnDataFile = "SzenarienJSON/"+scenarioFolder+"/ampeln.json";
		theProcessStationDataFile = "SzenarienJSON/"+scenarioFolder+"/processstation.json"; 
		theSteuerLogikDataFile = "SzenarienJSON/"+scenarioFolder+"/steuerlogik.json"; 
		theEndStationDataFile = "SzenarienJSON/"+scenarioFolder+"/endstation.json";
		theAutoDataFile = "SzenarienJSON/"+scenarioFolder+"/auto.json"; 
		theWellengeneratorDataFile = "SzenarienJSON/"+scenarioFolder+"/wellengenerator.json"; 
		theWaypointDataFile = "SzenarienJSON/"+scenarioFolder+"/waypoint.json";
	}

	 /**
     * create the Wellengenerator
     * 
     * Liest Daten aus dem json FILE wellengenerator.json aus und
     * ruft die Methode create() des Wellengenerators auf um den Wellengenerator
     * zu erstellen
     * 
     */
     private static void createWellenGenerator(){
    	
    
    		
    	 JSONObject settings = (JSONObject) getJSONObject(theWellengeneratorDataFile).get("settings");
  
    		
    		
    		JSONArray tablesWellenGenerator = settings.getJSONArray("wellenGenerator");
    		
    		
    		
    		
    		for(Iterator i = tablesWellenGenerator.iterator(); i.hasNext();){
    			JSONObject theWellenGenerator = (JSONObject) i.next();


    			
    			String label = null;
     			int xPos = 0;
     			int yPos = 0;
     			String image = null;
     			int wellengroese;
    		
    		
     		//get the label
    		label = theWellenGenerator.getString("label");
    		
    	
    		
    		//get the position
    		xPos = theWellenGenerator.getInt("x_position");
    		yPos = theWellenGenerator.getInt("y_position");
    		
    		//the <view> ... </view> node
    		
    		JSONObject view = (JSONObject) theWellenGenerator.get("view");
    		
    		image = view.getString("image");
    		
    		
    
    		
    		
    		//get the Wellengroese
    		wellengroese = theWellenGenerator.getInt("wellenGroesse");
    	
  

    		
    		//CREATE THE INQUEUE
    		//the <inqueue> ... </inqueue> node
    		JSONObject inqueueGroup = (JSONObject) theWellenGenerator.get("inqueue");
    		
    		// the positions
    		int xPosInQueue = inqueueGroup.getInt("x_position");
    		int yPosInQueue = inqueueGroup.getInt("y_position");
    		
    		//create the inqueue
    		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
    		//CREATE THE OUTQUEUE
    		//the <outqueue> ... </outqueue> node
    		JSONObject outqueueGroup = (JSONObject) theWellenGenerator.get("outqueue");
    		
    		// the positions
    		int xPosOutQueue = outqueueGroup.getInt("x_position");
    		int yPosOutQueue = outqueueGroup.getInt("y_position");
    		
    		//create the outqueue
    		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
    		    		
    		//creating a new StartStation object
    		WellenGenerator.create(label, theInQueue, theOutQueue, xPos, yPos, image,wellengroese);
    		}
    	
    	
		
     }
    
     private static void createWaypoint()
     {
    	 try {
      		//read the information from the json file into a JDOM Document
      		Document thejsonDoc = new SAXBuilder().build(theWaypointDataFile);
      		
      		//the <settings> ... </settings> node
      		Element root = thejsonDoc.getRootElement();
      		
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
      * Liest Daten aus dem json FILE auto.json aus und
      * ruft die Methode create() von Auto auf mehrere Autos
      * zu erstellen
      * 
      */
     private static void createAutos(){
    	
    	try {
    		
    		
		
    		//read the information from the json file into a JDOM Document
    		Document thejsonDoc = new SAXBuilder().build(theAutoDataFile);
    		
    		//the <settings> ... </settings> node, this is the files root Element
    		Element root = thejsonDoc.getRootElement();
    		
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
      * Liest Daten aus dem json FILE steuerlogik.json aus und
      * ruft die Methode create() von SteuerLogik auf um mehrere Autos
      * zu erstellen
      * 
      */
     private static void createSteuerLogik()
     {
    	 
    	
    		
    	 JSONObject settings = (JSONObject) getJSONObject(theSteuerLogikDataFile).get("settings");
    	  
 		
 		 JSONObject steuerlogik = (JSONObject) settings.get("steuerLogik");
    	 
			String label = null;
			int xPos = 0;
			int yPos = 0;

    		//get the label
    		label = steuerlogik.getString("label");
    		
    		//get the position
    		xPos = steuerlogik.getInt("x_position");
    		yPos= steuerlogik.getInt("y_position");
    		
    		ArrayList<ArrayList<Ampel>> amp = new ArrayList<ArrayList<Ampel>>();
    		ArrayList<Long> rot = new ArrayList<Long>();
    		ArrayList<Long> gruen = new ArrayList<Long>();
			ArrayList<ArrayList<WellenGenerator>> wel = new ArrayList<ArrayList<WellenGenerator>>();
			ArrayList<Long> welTime = new ArrayList<Long>();
			
		
			
			
			//get all info necessary for the just above things
			{
				
				JSONArray ampelSets = steuerlogik.getJSONArray("ampelSet");
				
				
				for (Object ampelSet : ampelSets)
				{
					String rotPhasenString = ((JSONObject) ampelSet).getString("rotPhase");
					rot.add( Long.parseLong( rotPhasenString ) );
					
					String gruenPhasenString = ((JSONObject) ampelSet).getString("gruenPhase");
					gruen.add( Long.parseLong( gruenPhasenString ) );
					
					ArrayList<Ampel> ampelnInSet = new ArrayList<Ampel>();
					
					JSONArray ampelLabels = (JSONArray) ((JSONObject) ampelSet).get("ampelLabel");
					
					for(Object ampelLabel : ampelLabels)
					{
						String ampel = ((String)ampelLabel);
						
						
						ampelnInSet.add( Ampel.getAmpelByLabel(ampel) );
					}
					
					amp.add(ampelnInSet);
				}
				
				JSONArray wellenGeneratorSets = steuerlogik.getJSONArray("wellenGeneratorSet");
				for (Object wellenGeneratorSet : wellenGeneratorSets)
				{
					ArrayList<WellenGenerator> tempWelEle = new ArrayList<WellenGenerator>();
					
					String wellenZeitPunktString = ((JSONObject) wellenGeneratorSet).getString("wellenZeitpunkt");
					welTime.add( Long.parseLong( wellenZeitPunktString ) );		
					
					
					
					

					JSONArray wellenGeneratoren = (JSONArray) ((JSONObject) wellenGeneratorSet).get("wellenGenerator");
					for(Object wellenGenerator : wellenGeneratoren)
					{
						String wellenGeneratorLabel = ((JSONObject) wellenGenerator).getString("wellenGeneratorLabel");
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
      * Liest Daten aus dem json FILE ampel.json aus und
      * ruft die Methode create() von Ampel auf um mehrere Ampeln
      * zu erstellen
      * 
      */
      private static void createAmpeln(){
     	

    	    
  		
     	 JSONObject settings = (JSONObject) getJSONObject(theAmpelnDataFile).get("settings");
   
   
     		
	JSONArray tablesAmpel = settings.getJSONArray("station");
    		
    		
    		
    		
    		for(Iterator i = tablesAmpel.iterator(); i.hasNext();){
    			JSONObject theAmpel = (JSONObject) i.next();
     		
     		
     	
         
     			
     			String label = null;
      			int xPos = 0;
      			int yPos = 0;
      			String image = null;
      
     		
     		
      		//get the label
     		label = theAmpel.getString("label");
     		
     	
     		
     		//get the position
     		xPos = theAmpel.getInt("x_position");
     		yPos = theAmpel.getInt("y_position");
     		
     		//the <view> ... </view> node
     		
     		JSONObject view = (JSONObject) theAmpel.get("view");
     		
     		image = view.getString("image");
     		

     		//CREATE THE INQUEUE
     		//the <inqueue> ... </inqueue> node
     		JSONObject inqueueGroup = (JSONObject) theAmpel.get("inqueue");
     		
     		// the positions
     		int xPosInQueue = inqueueGroup.getInt("x_position");
     		int yPosInQueue = inqueueGroup.getInt("y_position");
     		
     		//create the inqueue
     		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
     		//CREATE THE OUTQUEUE
     		//the <outqueue> ... </outqueue> node
     		JSONObject outqueueGroup = (JSONObject) theAmpel.get("outqueue");
     		
     		// the positions
     		int xPosOutQueue = outqueueGroup.getInt("x_position");
     		int yPosOutQueue = outqueueGroup.getInt("y_position");
     		
     		//create the outqueue
     		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
     		    		
     		//creating a new StartStation object
     		
     		Ampel.create(label, theInQueue, theOutQueue, xPos, yPos, image);

     		}
     	
     	
     }
     
    /**
     * create some process stations out of the json file
     * 
     */
     private static void createProcessStations(){
    	
    	try {
    		
    		//read the information from the json file into a JDOM Document
    		Document thejsonDoc = new SAXBuilder().build(theProcessStationDataFile);
    		
    		//the <settings> ... </settings> node
    		Element root = thejsonDoc.getRootElement();
    		
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
    	
    	
    		
      		
        	 JSONObject settings = (JSONObject) getJSONObject(theEndStationDataFile).get("settings");
      
        	 JSONObject theEndStation = (JSONObject) settings.get("endStation");
        	 
      			
      			String label = null;
       			int xPos = 0;
       			int yPos = 0;
       			String image = null;
       		
      		
      		
       		//get the label
      		label = theEndStation.getString("label");
      		
      	
      		
      		//get the position
      		xPos = theEndStation.getInt("x_position");
      		yPos = theEndStation.getInt("y_position");
      		
      		//the <view> ... </view> node
      		
      		JSONObject view = (JSONObject) theEndStation.get("view");
      		
      		image = view.getString("image");
      		

      		//CREATE THE INQUEUE
      		//the <inqueue> ... </inqueue> node
      		JSONObject inqueueGroup = (JSONObject) theEndStation.get("inqueue");
      		
      		// the positions
      		int xPosInQueue = inqueueGroup.getInt("x_position");
      		int yPosInQueue = inqueueGroup.getInt("y_position");
      		
      		//create the inqueue
      		SynchronizedQueue theInQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosInQueue, yPosInQueue);
      		//CREATE THE OUTQUEUE
      		//the <outqueue> ... </outqueue> node
      		JSONObject outqueueGroup = (JSONObject) theEndStation.get("outqueue");
      		
      		// the positions
      		int xPosOutQueue = outqueueGroup.getInt("x_position");
      		int yPosOutQueue = outqueueGroup.getInt("y_position");
      		
      		//create the outqueue
      		SynchronizedQueue theOutQueue = SynchronizedQueue.createQueue(QueueViewText.class, xPosOutQueue, yPosOutQueue);
      		    		
      		//creating a new StartStation object
      		
      		EndStation.create(label, theInQueue, theOutQueue, xPos, yPos, image);
      		
      		
    		
    	    
     
    	
     }
     
     /**
      * get for Scenario Folder
      * @return scenarioFolder
      */
     
     public String getScenario() {
    	 return scenarioFolder;
     }
        
}
