package model;

import controller.Simulation;

public class WellenGenerator extends Ampel {

	/** instance of the Wellengenerator*/
	private static WellenGenerator TheWellenGenerator;
	private int wellengroese;
	
	/** (private!) Constructor, creates a new Wellengenerator
	 * 
	 * @param label of the Wellengenerator 
	 * @param inQueue the incoming queue
	 * @param outQueue the outgoing queue
	 * @param xPos x position of the Wellengenerator
	 * @param yPos y position of the Wellengenerator
	 * @param image image of the Wellengenerator 
	 */
	private WellenGenerator(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,String wellengroese){
		
		super(label, inQueue, outQueue, xPos, yPos, image);
		
		this.wellengroese = Integer.parseInt(wellengroese);
		
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
	public static void create(String label, SynchronizedQueue inQueue, SynchronizedQueue outQueue, int xPos, int yPos, String image,String wellengroese){
	
		TheWellenGenerator = new WellenGenerator(label, inQueue, outQueue, xPos, yPos, image, wellengroese);
		
	}
	
	
	
	
	@Override
	protected void handleObject(TheObject theObject){
				
		//the object chooses an outgoing queue and enter it
		Auto.enterOutQueue(this);
		
		//let the next objects start with a little delay
		try {
			Thread.sleep(Simulation.CLOCKBEAT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	@Override
	protected boolean work() {
		
		//so viele aus der Queue losschicken, wie die wellengröße groß ist
		
		
		
		
		return false;
	}
	
	
	public void sendWave() {
		
		
		
		
	}
	

}
