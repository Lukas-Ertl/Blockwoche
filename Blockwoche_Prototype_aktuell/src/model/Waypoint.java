package model;

import java.util.Collection;

public class Waypoint extends SimpleStation {

	public Waypoint(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos)

	{
		super(label, inQueue, outQueue, xPos, yPos, "");

	}

	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos) {
		new Waypoint(label, inQueue, outQueue, xPos, yPos);
	}

	/**
	 * the method handles the cars like the Traffic light by putting them in the outqueue.
	 */
	@Override
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
	protected boolean work() {
		 
			return super.work();
	}

}
