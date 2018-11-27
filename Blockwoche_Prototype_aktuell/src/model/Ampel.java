package model;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the station that will represent the traffic
 * lights themselves
 * 
 * @author JMaier
 * @version 2018-11-24
 */
public class Ampel extends SimpleStation {
	
	private boolean green = true;
	
	
	
	protected static Map hm = Collections.synchronizedMap(new HashMap());


	protected Ampel(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos,
			String image) {
		super(label, inQueue, outQueue, xPos, yPos, image);
		
		hm.put(label, this);
	}
	


	@Override
	protected void handleObject(TheObject theObject) {
		theObject.enterOutQueue(this);
	}
	
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image){
		
		new Ampel(label, inQueue, outQueue, xPos, yPos, image);
		
	}	
	
	
	
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
	 * Checks if the Lights are green then returns true for green and returns red when false
	 * @return
	 */
	
	
	public void switchState()
	
	{
		green=!green;
	
	}
	public static Ampel getAmpelByLabel(String label)
	{
		return (Ampel) hm.get(label);
		
	}
	@Override
	protected void handleObjects(Collection<TheObject> theObjects)
	{
		
	}
	
	@Override
	protected Collection<TheObject> getNextInQueueObjects()
	{
		return null;
	}

	@Override
	protected Collection<TheObject> getNextOutQueueObjects()
	{
		return null;
	}
	

}
