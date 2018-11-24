package model;

import java.util.ArrayList;
import java.util.Collection;

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
