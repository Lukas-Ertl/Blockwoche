package model;

import java.util.Collection;

import controller.Simulation;

/**
 * This is the station that will represent the traffic
 * lights themselves
 * 
 * @author JMaier
 * @version 2018-11-24
 */
public class Ampel extends SimpleStation {

	protected Ampel(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos,
			String image) {
		super(label, inQueue, outQueue, xPos, yPos, image);
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
		
		//let the thread wait only if there are no objects in the incoming and outgoing queues
		if (numberOfInQueueObjects() == 0 && numberOfOutQueueObjects() == 0) return false;
		
		//If there is an inqueue object found, handle it
		while (numberOfInQueueObjects() > 0) {
			
			if(new Long (Simulation.getGlobalTime()).intValue() % 5 == 0)
					this.handleObject(this.getNextInQueueObject());
			
		}
				
		//If there is an object in the out queue -> wake it up
		if(numberOfOutQueueObjects() > 0){
			
			TheObject myObject = (TheObject) this.getNextOutQueueObject();//get the object
			
			//instruct the object to move to the next station
			myObject.wakeUp();
				
		}
				
		//maybe there is more work to do
		return true;
		
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
