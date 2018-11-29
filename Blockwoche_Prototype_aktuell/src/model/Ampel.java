package model;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the Station that will represent the traffic
 * lights themselves 
 * 
 * @author Team 4 
 * @version 2018-11-24
 */
public class Ampel extends SimpleStation {
	
	 /** the boolean green is being used as the current state of the Ampel.
	  * 
	  */
	private boolean green = false;
	
	protected static Map ampelMap = Collections.synchronizedMap(new HashMap());

/** 
 * 
 * 
 * Constructor that constructs the initial states for the Ampel Objects 
 * it puts the Ampel objects in a hashMap and gives them a keyvalue.
 * @param label
 * @param inQueue
 * @param outQueue
 * @param xPos
 * @param yPos
 * @param image
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
	}
	
	/**
	 * Creates a new Ampel Object 
	 * 
	 * @param label
	 * @param inQueue
	 * @param outQueue
	 * @param xPos
	 * @param yPos
	 * @param image
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image){
		
		new Ampel(label, inQueue, outQueue, xPos, yPos, image);
		
	}	
	
	
	
	
	/**
	 *  returns a super call for the worker constructor of Simplestation 
	 * and loops it while the Ampel is green.
	 */
	@Override 
	protected boolean work() {
		while(green) {
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
	 * Everytime switchstate is being called the boolean green is being inverted.
	 * 
	 */
	public void switchState()
	
	{
		green=!green;
	
	}
	/**
	 * getter method to return the Ampel keyValues in the hashMap
	 * @param label
	 * @return
	 */
	public static Ampel getAmpelByLabel(String label)
	{
		return (Ampel) ampelMap.get(label);
		
	}
	@Override
	/**
	 * Should handle Multiple Objects in the future
	 */
	protected void handleObjects(Collection<TheObject> theObjects)
	{
		
	}
	/**
	 * placeholder should return multiple Objects in the future
	 */
	@Override
	protected Collection<TheObject> getNextInQueueObjects()
	{
		return null;
	}
	/**
	 * placeholder should return muliple objects in the future
 	*/
	@Override
	protected Collection<TheObject> getNextOutQueueObjects()
	{
		return null;
	}
	

}
