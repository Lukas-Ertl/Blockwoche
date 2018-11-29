package model;

import java.util.Collection;

public class Waypoint extends SimpleStation {
	
	private Waypoint(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image)
	{
		super(label, inQueue, outQueue, xPos, yPos, image);
	}
	
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image)
	{
		new Waypoint(label, inQueue, outQueue, xPos, yPos, image);
	}

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

}
