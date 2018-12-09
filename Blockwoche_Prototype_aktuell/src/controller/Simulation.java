package controller;

import view.SimulationView;
import io.Factory;
import io.FactoryJSON;
import io.Statistics;
import java.util.concurrent.atomic.AtomicLong;
import model.Actor;


/**
 * Controls the flow of the simulation
 * 
 * @author Jaeger, Schmidt, edited by Team 4
 * @version 2016-07-07
 */
public class Simulation {
	
	/** is the simulation running*/
	public static boolean isRunning = false;  
	
	/** a speed factor for the clock to vary the speed of the clock in a simple way*/
	public static int SPEEDFACTOR = 1;  
	
	/**the beat or speed of the clock, e.g. 300 means one beat every 300 milli seconds*/
	public static final int CLOCKBEAT = 300 * SPEEDFACTOR; 
	
	/**the global clock */
	//the clock must be thread safe -> AtomicLong. The primitive type long isn't, even if synchronized
	private static AtomicLong clock = new AtomicLong(0); 
	
	/** Strings used by other classes to navigate between scenarios */
	public static String folder;
	/** Strings used by other classes to navigate between JSON and XML */
	public static String szenarioType;
	
	/**
	 * Constructor 
	 * 	calls init
	 * 
	 * @author Jaeger, Schmidt, edited by Team 4
	 * @version 2018-12
	 * 
	 * @param folder Base scenario folder
	 * @param szenarioType String of either XML or JSON
	 */
	public Simulation(String folder, String szenarioType) {
		Simulation.folder = folder;
		Simulation.szenarioType = szenarioType;
		System.out.println("in Simulation Constructor "+folder);
		init(folder, szenarioType);
	}
	
	/**
	 * edited by Team 4
	 * 
	 * initialize the simulation
	 * @param folder Base scenario folder
	 * @param szenarioType String of either XML or JSON
	 */
	private void init(String folder, String szenarioType){
		System.out.println("in Simulation init "+folder);
		//create all stations and objects for the starting scenario out of XML
		if(szenarioType.equals("XML"))
		{
			Factory.createStartScenario(folder);
		}
		else if(szenarioType.equals("JSON"))
		{
			FactoryJSON.createStartScenario(folder);
		}
		else
		{
			System.exit(0);
		}
				
		//the view of our simulation
		new SimulationView();
					
		// set up the the heartbeat (clock) of the simulation
		new HeartBeat().start();
		 		
		Statistics.show("---- Simulation gestartet ---\n");
				
		// start all the actor threads
		for (Actor actor : Actor.getAllActors()) {
			actor.start();		
						
		}
		
		/*
		 * Hinweis: wenn nicht über den Startbutton gestartet werden soll oder die Simulation ohne View laufen soll,
		 * den auskommentierten Code unten verwenden 
		 */
				
		/*
		//Zeitpuffer vor Start -> sonst läuft der letzte manchmal nicht los
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		//wake up the start station -> lets the simulation run
		StartStation.getStartStation().wakeUp();
		
		*/
		
	}
			
	
	/**
	 * The heartbeat (the pulse) of the simulation, controls the clock.
	 * 
	 */
	private class HeartBeat extends Thread {
		
		@Override
		public void run() {
			
			while(true){
				
				try {
				
					Thread.sleep(CLOCKBEAT);
					
					//Increase the global clock
					clock.incrementAndGet();
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	/** Get the global time
	 * 
	 * @return the global time
	 */
	public static long getGlobalTime() {
		return clock.get();
	}
	
	
	/** Getter for isRunning
	 * @author Team 4
	 * 
	 * @return isRunning
	 */
	public boolean getIsRunning() {
		return isRunning;
	}
	
	/** Getter for Szenario folder
	 * @author Team 4
	 * 
	 * @return Szenarien ordner (SzenarienXML / SzenarienJSON)
	 */
	public static String getFolder()
	{
		return "Szenarien"+Simulation.szenarioType;
	}
	
	/** Getter for specific scenario
	 * @author Team 4
	 * 
	 * @return folder
	 */
	public static String getScenario()
	{
		return Simulation.folder;
	}
	
	/** Get scenario type
	 * @author Team 4
	 * 
	 * @return szenarioType
	 */
	public static String getScenarioType()
	{
		return Simulation.szenarioType;
	}
	
}
