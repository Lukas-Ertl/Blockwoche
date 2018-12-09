package model;

import java.util.ArrayList;
import java.util.Observable;

import view.LiveCoverage;

/** 
 * Die Autos die in unserer Simulation fahren
 * 
 * @author Team 4
 * @version 2018-11
 */
public class Auto extends TheObject{
	
	/**Long values used to measure how long a car waits at an Ampel*/
	private long timerStart, timerEnd;
	
	/** arraylist of all created cars */
	private static ArrayList<Auto> alleAutos= new ArrayList<Auto>();
	/** measured statistics of cars */
	private ArrayList<ArrayList<Object>> messDaten = new ArrayList<ArrayList<Object>>();
	
	/** observable each car owns to be able to be observer */
	private InnerObservable inObserv;

	/** Constructor for Auto
	 * 
	 * @param label Auto name
	 * @param stationsToGo list of stations to visit
	 * @param processtime the processing time of the object, affects treatment by a station
	 * @param speed speed of the car
	 * @param xPos x position of the object#
	 * @param yPos y position of the object
	 * @param image image of the object
	 */
	public Auto(String label, ArrayList<String> stationsToGo, int processtime, int speed, int xPos, int yPos, String image)
	{
		super(label,stationsToGo,processtime,speed,xPos,yPos,image);
		inObserv = new InnerObservable();
		inObserv.addObserver( LiveCoverage.getInstance() );
		alleAutos.add(this);
	}
	
	/** Create a new Auto model
	 *
	 * @param label of the object 
	 * @param stationsToGo the stations to go
	 * @param processtime the processing time of the object, affects treatment by a station
	 * @param speed the moving speed of the object
	 * @param xPos x position of the object
	 * @param yPos y position of the object
	 * @param image image of the object
	 */
	public static void create(String label, ArrayList<String> stationsToGo, int processtime, int speed ,int xPos, int yPos, String image)
	{
		new Auto(label, stationsToGo, processtime, speed, xPos, yPos, image);
		
	}
	
	/** Chooses a suited incoming queue of the given station and enter it 
	 * 
	 * @param station the station from where the queue should be chosen
	 * 
	 */
	@Override
	protected void enterInQueue(Station station){
		
		//get the stations incoming queues
		ArrayList<SynchronizedQueue> inQueues = station.getAllInQueues();
		
		SynchronizedQueue queueBuffer = inQueues.get(0);
		int queueSize = queueBuffer.size();
		
		//Looking for the shortest queue (in a simple way)
		for (SynchronizedQueue inQueue : inQueues) {
			
			if(inQueue.size() < queueSize) {
				queueBuffer = inQueue;
				queueSize = inQueue.size();
			}
		}
		
		//if the station is not a traffic light, if the traffic light is green, or there are already waiting cars go into the inqueue
		if(station.getClass() != Ampel.class || ! ((Ampel) station).getIsGreen() || !queueBuffer.isEmpty() )
		{
			if(station.getClass() == Ampel.class)
				this.inObserv.waiting();
			
			//start Timer
			timerStart = controller.Simulation.getGlobalTime();
	
			//enter the Queue
			queueBuffer.offer(this);
			
			//set actual station to the just entered station
			this.actualStation = station;
		}
		else
		{
			this.messDaten.add(new ArrayList<Object>());
			
			//Für aktuelle Ampel label in Collection eintragen
			this.messDaten.get(messDaten.size()-1).add(station.label);
			//Für aktuelle Ampel label in Collection eintragen
			this.messDaten.get(messDaten.size()-1).add( new Long(0) );
			work();
		}
	}
	
	/**schreibt Daten jeder besuchter Ampel in Collection und 
	 * Chooses a suited outgoing queue of the given station and enter it
	 * 
	 * @param station the station from where the queue should be chosen
	 */
	@Override
	void enterOutQueue(Station station){
		
		//End Timer
		timerEnd = controller.Simulation.getGlobalTime();
		
		// schreibt daten in Collection
		if (station.getClass() == Ampel.class) {
			this.messDaten.add(new ArrayList<Object>());
	
			//Für aktuelle Ampel label in Collection eintragen
			this.messDaten.get(messDaten.size()-1).add(station.label);
			//Für aktuelle Ampel label in Collection eintragen
			this.messDaten.get(messDaten.size()-1).add(this.timerEnd-timerStart);
			
			//System.out.println(this.messDaten.get(messDaten.size()-1).get(0));
		}
		
		//get the stations outgoing queues
		ArrayList<SynchronizedQueue> outQueues = station.getAllOutQueues();
		
		//there is just one queue, enter it
		if(outQueues.size()==1) outQueues.get(0).offer(this);
		
		//Do we have more than one outgoing queue?
		//We have to make a decision which queue we choose -> your turn 
		else{
			
			//get the first queue and it's size
			SynchronizedQueue queueBuffer = outQueues.get(0);
			int queueSize = queueBuffer.size();
							
			//Looking for the shortest queue (in a simple way)
			for (SynchronizedQueue inQueue : outQueues) {
					
				if(inQueue.size() < queueSize) {
					queueBuffer = inQueue;
					queueSize = inQueue.size();
				}
			}
			
			//enter the queue
			queueBuffer.offer(this);
		}
		if(actualStation.getClass() == Ampel.class)
			this.inObserv.continuing();
		
	}
	
	/**
	 * getter for messDaten
	 * @return messDaten the measured statistics
	 */
	public ArrayList<ArrayList<Object>> getMessDaten() {
		
		return messDaten;
	}
	/**
	 * getter for all cars
	 * @return alleAutos all cars
	 */
	public static ArrayList<Auto> getAlleAutos(){
		return alleAutos;
	}
	/**
	 * checks if the car has waited at the given Ampel, and if yes returns how long it waited
	 * @param aktuelleAmpel which Ampel to check for
	 * @return Waittime time waited at the light
	 */
	public long getWarteZeit(Station aktuelleAmpel)
	{
		//nachschauen ob das auto bei dieser ampel war, wenn ja zeit zurückgeben
		for (ArrayList<Object> besuchteAmpel: this.messDaten) {
			if(aktuelleAmpel.getLabel() == ((String)(besuchteAmpel.get(0))))
				//return ((long) this.messDaten.get(this.messDaten.size()-1).get(1)) ;
				return ((long)(besuchteAmpel.get(1)));
		}
		return 0;
	}
	
	/**
	 * returns all cars that waited at a specific traffic light
	 * @param aktuelleAmpel which Ampel to check for
	 * @return cars that waited at the light
	 */
	public long getBesuchteAutos(Station aktuelleAmpel)
	{
		//nachschauen ob das auto bei dieser ampel war, wenn ja zeit zurückgeben
		for (ArrayList<Object> besuchteAmpel: this.messDaten)
		{
			if(aktuelleAmpel.getLabel() == ((String)(besuchteAmpel.get(0))))
				return 1;
		}
		return 0;
	}
	
	/**
	 * Inner observable class to extend the functionality of Auto
	 * 
	 * @author Team 4
	 *
	 */
	private class InnerObservable extends Observable
	{
		/** final boolean value that is passd to the observer when the car starts waiting */
		private final boolean WAITING = true;
		/** final boolean value that is passd to the observer when the car continues driving */
		private final boolean CONTINUING = false;
		
		/** called when the car stops at a light */
		void waiting()
		{
			System.out.println("waiting");
			this.setChanged();
			notifyObservers(WAITING);
		}
		/** called when a car starts driving after having stopped at a light */
		void continuing()
		{
			System.out.println("continuing");
			this.setChanged();
			notifyObservers(CONTINUING);
		}
	}
	
}
