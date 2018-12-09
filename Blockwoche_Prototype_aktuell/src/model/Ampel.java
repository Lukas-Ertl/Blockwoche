package model;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import controller.Simulation;

/**
 * This is the Station that will represent the traffic
 * lights themselves 
 * 
 * @author Team 4 
 * @version 2018-11-24
 */
public class Ampel extends SimpleStation {
	
	 /** the boolean isGreen is being used as the current state of the Ampel */
	private boolean isGreen = false;
	/** hashmap of all ampeln mapped to their names */
	protected static Map ampelMap = Collections.synchronizedMap(new HashMap());

/** 
 * 
 * 
 * Constructor that constructs the initial states for the Ampel Objects 
 * it puts the Ampel objects in a hashMap and gives them a keyvalue.
 * @param label Ampel name
 * @param inQueue the InQueue
 * @param outQueue the OutQueue
 * @param xPos Graphical x position
 * @param yPos Graphical y position
 * @param image Graphical image of the ampel
 */
	protected Ampel(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos,
			String image) {
		super(label, inQueue, outQueue, xPos, yPos, image);
		
		ampelMap.put(label, this);
	}
	

	/**
	 * this method handles the Cars by putting them in the outQueue
	 * to simulate that the cars are driving.
	 */
	@Override
	protected void handleObject(TheObject theObject) {
		theObject.enterOutQueue(this);
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
					
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Ampel Object 
	 * 
	 * @param label Ampel name
	 * @param inQueue the InQueue
	 * @param outQueue the OutQueue
	 * @param xPos Graphical x position
	 * @param yPos Graphical y position
	 * @param image Graphical image of the ampel
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image){
		
		new Ampel(label, inQueue, outQueue, xPos, yPos, image);
		
	}	
	
	
	
	
	/**
	 *  returns a super call for the worker constructor of Simplestation 
	 * and loops it while the Ampel is isGreen.
	 */
	@Override 
	protected boolean work() {
		while(isGreen) {
			return super.work();
			/*
			//let the thread wait only if there are no objects in the incoming and outgoing queues
			if (numberOfInQueueObjects() == 0 && numberOfOutQueueObjects() == 0) return false;
			
			//If there is an inqueue object found, handle it
			if (numberOfInQueueObjects() > 0) this.handleObject(this.getNextInQueueObject());
			
					
			//If there is an object in the out queue -> wake it up
			if(numberOfOutQueueObjects() > 0){
				
				TheObject myObject = (TheObject) this.getNextOutQueueObject();//get the object
				
				//instruct the object to move to the next station
				myObject.wakeUp();
					
			}
				
		//maybe there is more work to do
			//return true;
		*/
		}
		return false;
	}
	/**
	 * Everytime switchstate is being called the boolean isGreen is being inverted.
	 * 
	 */
	public void switchState()
	
	{
		isGreen=!isGreen;
	
	}
	/**
	 * getter method to return the Ampel keyValues in the hashMap
	 * @param label return the Ampel for a given ampellabel
	 * @return ampel return the ampel for the name
	 */
	public static Ampel getAmpelByLabel(String label)
	{
		return (Ampel) ampelMap.get(label);
		
	}
	
	/**
	 * Getter for isGreen
	 * @return isGreen returns whether the Ampel is currently green or red
	 */
	public boolean getIsGreen()
	{
		return this.isGreen;
	}


	@Override
	/**
	 * inherited unused method 
	 */
	protected void handleObjects(Collection<TheObject> theObjects) {
		// TODO Auto-generated method stub
		
	}


	@Override
	/**
	 * inherited unused method
	 * @return null doesn't do anything
	 */
	protected Collection<TheObject> getNextInQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	/**
	 * inherited unused method
	 * @return null doesn't do anything
	 */
	protected Collection<TheObject> getNextOutQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
