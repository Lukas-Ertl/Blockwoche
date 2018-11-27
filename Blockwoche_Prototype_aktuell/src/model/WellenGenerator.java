package model;

import java.util.ArrayList;
import java.util.Collection;

import controller.Simulation;

public class WellenGenerator extends Ampel {

	/** instance of the Wellengenerator*/
	private static WellenGenerator TheWellenGenerator;
	private int wellengroese;
	private boolean send = false;

	
	/** (private!) Constructor, creates a new Wellengenerator
	 * 
	 * @param label of the Wellengenerator 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos x position of the Wellengenerator
	 * @param yPos y position of the Wellengenerator
	 * @param image image of the Wellengenerator 
	 */
	private WellenGenerator(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,int wellengroese){
		
		super(label, inQueue, outQueue, xPos, yPos, image);
		
		this.wellengroese = wellengroese;
		
	}
	
	/** creates a new start station
	 *
	 * @param label of the Wellengenerator 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos x position of the Wellengenerator 
	 * @param yPos y position of the Wellengenerator
	 * @param image image of the Wellengenerator
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,int wellengroese){
	
		TheWellenGenerator = new WellenGenerator(label, inQueue, outQueue, xPos, yPos, image, wellengroese);
		
	}
	
	
	
	

	@Override
	protected void handleObject(TheObject theObject){
				
		//the object chooses an outgoing queue and enter it
		theObject.enterOutQueue(this);
		
		//let the next objects start with a little delay
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**Get the Wellengenerator
	 * 
	 * @return Wellengenerator
	 */
	public static WellenGenerator getWellengenerator() {
		return TheWellenGenerator;
	}
	
	

	
	@Override
	protected boolean work() {
		
	while(send)	{
		//let the thread wait only if there are no objects in the incoming and outgoing queues
		if (numberOfInQueueObjects() == 0 && numberOfOutQueueObjects() == 0) return false;
		
		//If there is an inqueue object found, handle it
		if (numberOfInQueueObjects() > 0) {
			
			System.out.println(wellengroese);
			this.handleObjects(this.getnextWave());
			
			
		}
				
		//If there is an object in the out queue -> wake it up
		if(numberOfOutQueueObjects() > 0){
			ArrayList<SynchronizedQueue> wellenListe = getAllOutQueues();
			//TheObject myObject = (TheObject) this.getNextOutQueueObject();//get the object
			for(Object o: wellenListe.get(0)) {
			//instruct the object to move to the next station
			((TheObject)o).wakeUp();
			}
			
				
		}
				send = false;
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
		//maybe there is more work to do
		return true;
	}
	return false;
	}
	
	
	private ArrayList<TheObject> getnextWave() {
		
		ArrayList<TheObject> welle = new ArrayList<TheObject>();
		
		for (int counter = 0; counter < wellengroese; counter++) {
			
			welle.add(getNextInQueueObject());
			
			
		}
		
		return welle;
		
	}
	
	
	public void sendWave() {
//		
//		ArrayList<TheObject> nextWave = this.getnextWave();
//		handleObjects(nextWave);
//		
		this.send=true;
		
		
	}
	
	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {

		//the object chooses an outgoing queue and enter it
		//theObject.enterOutQueue(this);
		
		for (TheObject o: theObjects) {
			
			o.enterOutQueue(this);
			
		}
		

			
		
	}
	
	protected void handleObjects(Collection<TheObject> theObjects,int anzahl) {

		//the object chooses an outgoing queue and enter it
		//theObject.enterOutQueue(this);
		
		for (TheObject o: theObjects) {
			
			o.enterOutQueue(this);
			
		}
		
		//let the next objects start with a little delay
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
		
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
