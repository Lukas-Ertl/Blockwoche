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
	private long TimerStart, TimerEnd;

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
		TimerStart = controller.Simulation.getGlobalTime();
		
		
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
	
	/** Chooses a suited outgoing queue of the given station and enter it
	 * 
	 * @param station the station from where the queue should be chosen
	 */
	@Override
	void enterOutQueue(Station station){
		
		//End Timer
		TimerEnd = controller.Simulation.getGlobalTime();
		
		System.out.println("TimerStart: " + TimerStart + " TimerEnd " + TimerEnd);
		
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
		
	//enterinQue überschreiben mit Timerstart
	//enteroutque überschreiben mit timer ende
}
