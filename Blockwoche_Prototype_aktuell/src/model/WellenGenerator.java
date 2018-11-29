package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import controller.Simulation;

public class WellenGenerator extends Ampel {

	/** instance of the Wellengenerator*/
	protected static Map theWellenGeneratorMap = Collections.synchronizedMap(new HashMap());
	private int wellenGroesse;
	private boolean send = false;

	
	/** (private!) Constructor, creates a new Wellengenerator
	 * 
	 * @param label of the Wellengenerator 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos x position of the Wellengenerator
	 * @param yPos y position of the Wellengenerator
	 * @param image image of the Wellengenerator 
	 * @param größe der Wellen an Autos
	 */
	private WellenGenerator(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,int wellenGroesse){
		
		super(label, inQueue, outQueue, xPos, yPos, image);
		
		this.theWellenGeneratorMap.put(label, this);
		this.wellenGroesse = wellenGroesse;
		
	}
	
	/** creates a new Wellengenerator
	 *
	 * @param label of the Wellengenerator 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos x position of the Wellengenerator 
	 * @param yPos y position of the Wellengenerator
	 * @param image image of the Wellengenerator
	 * @param größe der Wellen an Autos
	 */
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,int wellenGroesse){
	
		//TheWellenGenerator = 
		new WellenGenerator(label, inQueue, outQueue, xPos, yPos, image, wellenGroesse);
		
	}
	
	
	
	
	/** legt Objekte in die Outqueue
	 * 
	 * @param theObject das in die Outqueue zu legende Objekt
	 */
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
	
	/**Get the Wellengenerator by Label
	 * 
	 * @return Wellengenerator
	 */
	public static WellenGenerator getWellenGeneratorByLabel(String label) {
		
		return (WellenGenerator) theWellenGeneratorMap.get(label);
	}
	
	

	/**Verarbeitet Objekte und sendet sie in Wellen los
	 * 
	 * @return Wellengenerator
	 */
	@Override
	protected boolean work() {
		
	while(send)	{
		//let the thread wait only if there are no objects in the incoming and outgoing queues
		if (numberOfInQueueObjects() == 0 && numberOfOutQueueObjects() == 0) return false;
		
		//If there is an inqueue object found, handle it
		if (numberOfInQueueObjects() > 0) {
			
			
			this.handleObjects(this.getNextWave());
			
			
		}

		//If there is an object in the out queue -> wake it up
		if(numberOfOutQueueObjects() > 0){
			
			
			ArrayList<Auto> wellenListe = new ArrayList<Auto>();
			int autoLimit = this.outGoingQueue.size();
			for(int i = 0; i< autoLimit; i++) {

				wellenListe.add((Auto) this.getNextOutQueueObject());
			}

			//TheObject myObject = (TheObject) this.getNextOutQueueObject();//get the object
			for(Auto a: wellenListe) {
			//instruct the object to move to the next station
			a.wakeUp();
			

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
	
	
	
	/**Erstellt Wellen mit der Größe, wie im XML deffiniert
	 * 
	 * @return eine Welle mit der größe, wie sie in der XML angegeben wurde
	 */
	private ArrayList<TheObject> getNextWave() {
		
		ArrayList<TheObject> welle = new ArrayList<TheObject>();
		
		for (int counter = 0; counter < wellenGroesse; counter++) {
			
			if(this.inComingQueue.isEmpty())break;
			welle.add(getNextInQueueObject());
			
			
		}
		
		return welle;
		
	}
	
	/**Wird von der SteuerLogik aufgerufen und startet das senden der Welle
	 * 
	 */
	public void sendWave() {
		
		this.send=true;

		
		
	}
	
	/**Verarbeitet eine Collection von Objekten und legt sie in die Outqueue
	 * 
	 */
	@Override
	protected void handleObjects(Collection<TheObject> theObjects) {

		//the object chooses an outgoing queue and enter it
		//theObject.enterOutQueue(this);
		
		for (TheObject o: theObjects) {
			
			o.enterOutQueue(this);
			
		}
		

			
		
	}
	
//	protected void handleObjects(Collection<TheObject> theObjects,int anzahl) {
//
//		//the object chooses an outgoing queue and enter it
//		//theObject.enterOutQueue(this);
//		
//		for (TheObject o: theObjects) {
//			
//			o.enterOutQueue(this);
//			
//		}
//		
//		//let the next objects start with a little delay
//		try {
//			Thread.sleep(Simulation.CLOCKBEAT);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//			
//		
//	}
	
	
	
	@Override
	protected Collection<TheObject> getNextInQueueObjects() {
		return null;
	}

	@Override
	protected Collection<TheObject> getNextOutQueueObjects() {
		return null;
	}
	
	
	
	

}
