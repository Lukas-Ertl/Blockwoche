package model;

import java.util.Collection;

public class Ampel extends SimpleStation {

	protected Ampel(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos,
			String image) {
		super(label, inQueue, outQueue, xPos, yPos, image);
	}

	@Override
	protected void handleObject(TheObject theObject) {
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

	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {
		// TODO Auto-generated method stub
		
	}

}
