package model;

import java.util.ArrayList;

import controller.Simulation;

/** 
 * Die Autos die in unserer Simulation fahren
 * 
 * @author Team 4
 * @version 2018-11
 */
public class Auto  extends TheObject{
	
	/**Long values used to measure how long a car waits at an Ampel*/
	private long timerStart, timerEnd;
	
	
	private static ArrayList<Auto> alleAutos= new ArrayList<Auto>();
	private ArrayList<ArrayList<Object>> messDaten = new ArrayList<ArrayList<Object>>();


	
	
	


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
		
		
		
		//start Timer
		timerStart = controller.Simulation.getGlobalTime();
		
		
		
		
		//get the stations incoming queues
		ArrayList<SynchronizedQueue> inQueues = station.getAllInQueues();
		
		//there is just one queue, enter it
		if(inQueues.size()==1) inQueues.get(0).offer(this);
		
		//Do we have more than one incoming queue?
		//We have to make a decision which queue we choose -> your turn 
		else{
			
			//get the first queue and it's size
			SynchronizedQueue queueBuffer = inQueues.get(0);
			int queueSize = queueBuffer.size();
							
			//Looking for the shortest queue (in a simple way)
			for (SynchronizedQueue inQueue : inQueues) {
					
				if(inQueue.size() < queueSize) {
					queueBuffer = inQueue;
					queueSize = inQueue.size();
				}
			}
			
			//enter the queue
			queueBuffer.offer(this);
							
		}

		//set actual station to the just entered station
		this.actualStation = station;
			
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
		messDaten.add(new ArrayList<Object>());
		

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
	}
	
	public ArrayList<ArrayList<Object>> getMessDaten() {
		
		return messDaten;
	}
	
	/**Zählt die Besuchten Ampeln
	 * 
	 * 
	 */

	
	

	
	
	public static ArrayList<Auto> getAlleAutos(){
		
		return alleAutos;
		
	}
	
	
	
	public long getWarteZeit(Station aktuelleAmpel) {
		
		
		
		//nachschauen ob das auto bei dieser ampel war, wenn ja zeit zurückgeben
		for (ArrayList<Object> besuchteAmpel: this.messDaten) {
			
		
		
		if(aktuelleAmpel.getLabel() == ((String)(besuchteAmpel.get(0)))) {
			
			
			
			
			//return ((long) this.messDaten.get(this.messDaten.size()-1).get(1)) ;
			return ((long)(besuchteAmpel.get(1)));
		}
		
		}
		
		return 0;
		
	}
	
	public long getBesuchteAutos(Station aktuelleAmpel) {
		
		
		
		//nachschauen ob das auto bei dieser ampel war, wenn ja zeit zurückgeben
		for (ArrayList<Object> besuchteAmpel: this.messDaten) {
			
		
		
		if(aktuelleAmpel.getLabel() == ((String)(besuchteAmpel.get(0)))) {
			
			return 1;
			
		}
		
		}
		
		return 0;
		
	}
	
	
	
}




