package model;

import java.util.Collection;

public class Waypoint extends SimpleStation {
	
	public Waypoint(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos)
	
	{
		super(label, inQueue, outQueue, yPos, yPos, label);
		
	}

	@Override
	protected void handleObject(TheObject theObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Collection<TheObject> getNextInQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<TheObject> getNextOutQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
