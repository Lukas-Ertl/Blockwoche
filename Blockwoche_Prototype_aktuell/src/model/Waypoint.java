package model;

import java.util.Collection;

/**
 * A basic station that is used to make the simulation look more realistic
 * @author Team 4
 * @version 2018-12
 *
 */
public class Waypoint extends SimpleStation {
	
	/**
	 * 
	 * @param label name
	 * @param inQueue inQueue
	 * @param outQueue OutQueue
	 * @param xPos Graphical x position
	 * @param yPos Graphical y position
	 * @param image image used, is left blank
	 */
	private Waypoint(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image)
	{
		super(label, inQueue, outQueue, xPos, yPos, image);
	}
	
	/**
	 * create method called by Factories
	 * calls the constructor
	 * 
	 * @param label name
	 * @param inQueue inQueue
	 * @param outQueue OutQueue
	 * @param xPos Graphical x position
	 * @param yPos Graphical y position
	 * @param image image used, is left blank
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image)
	{
		new Waypoint(label, inQueue, outQueue, xPos, yPos, image);
	}

	@Override
	/**
	 * simply puts the object directly into the outqueue instead of doing any processing
	 */
	protected void handleObject(TheObject theObject) {
		theObject.enterOutQueue(this);
	}

	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {
		
	}

	@Override
	protected Collection<TheObject> getNextInQueueObjects() {
		return null;
	}

	@Override
	protected Collection<TheObject> getNextOutQueueObjects() {
		return null;
	}

}
