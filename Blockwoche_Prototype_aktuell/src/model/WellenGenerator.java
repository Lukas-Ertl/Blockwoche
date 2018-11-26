package model;

import java.util.ArrayList;
import java.util.Collection;

import controller.Simulation;
import view.StationView;

public class WellenGenerator extends Station {
	private int waveSize;
	private int waitTime;

	public WellenGenerator(String label, int xPos, int yPos, String image, int waveSize, int waitTime) {
		super(label, xPos, yPos, image);

		
		this.waveSize=waveSize;
		this.waitTime=waitTime;
	}
	
	public static void create(String label, int xPos, int yPos, String image, int waveSize, int waitTime){
		
		new WellenGenerator(label, xPos, yPos, image, waveSize, waitTime);
		
	}

	
	@Override
	protected boolean work() {
		
		//work until all objects are handled
		while(numberOfInQueueObjects() > 0) {
				
			//send wave
			for(int counter=0; counter<waveSize; counter++) {
				//move object to out queue
				this.handleObject(this.getNextInQueueObject());
						
				//If there is an object in the out queue -> wake it up
				if(numberOfOutQueueObjects() > 0){
					
					TheObject myObject = (TheObject) this.getNextOutQueueObject();//get the object
					
					//instruct the object to move to the next station
					myObject.wakeUp();
						
				}	
			}

			//wait 
			try {
				Thread.sleep(waitTime);
						
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	
	
	
	@Override
	//Move Object to outQueue
	protected void handleObject(TheObject theObject) {
		theObject.enterOutQueue(this);

	}

	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int numberOfInQueueObjects() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int numberOfOutQueueObjects() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected TheObject getNextInQueueObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<TheObject> getNextInQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TheObject getNextOutQueueObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<TheObject> getNextOutQueueObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SynchronizedQueue> getAllInQueues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<SynchronizedQueue> getAllOutQueues() {
		// TODO Auto-generated method stub
		return null;
	}

}
