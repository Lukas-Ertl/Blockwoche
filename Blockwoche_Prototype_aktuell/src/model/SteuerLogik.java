package model;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Simulation;

/**
 * Steuerung fuer alle Ampeln und WellenGeneratoren
 * TODO make a Singleton
 * 
 * @author Team 4
 * @version 2018-11
 */
public class SteuerLogik extends Actor
{
	//TODO make a Singleton
	/**the single instance of SteuerLogik*/
	private static SteuerLogik instance;
	
	/**time that an Ampel stays green*/
	private long gruenPhase;
	
	/**time that an Ampel stays red*/
	private long rotPhase;
	
	//TODO replace with a list of Ampeln
	/**the Ampel that SteuerLogik controls*/
	private Ampel myAmpel;
	
	//TODO change to list
	/**time before the next wave of cars start*/
	private long wellenZeitPunkt;
	
	//TODO replace with list of WellenGenerators
	/**the WellenGenerator that SteuerLogik controls*/
	private WellenGenerator myWellenGenerator;
	
	/**the time being waited for to send signals to Ampeln and WellenGeneratorn*/
	private long ampelWaitTime, wellenGeneratorWaitTime;
	
	/**boolean used to differentiate between rotPhase and gruenPhase*/
	private boolean isGruen = true;
	
	/** (private) Constructor for the SteuerLogik
	 * 
	 * @param label of this SteuerLogik 
	 * @param xPos x position of the SteuerLogik 
	 * @param yPos y position of the SteuerLogik 
	 * @param rotPhase time that the controlled Ampel stays red
	 * @param gruenPhase time that the controlled Ampel stays green
	 * @param ampel the controlled Ampel
	 * @param wellenZeitPunkt time between waves of cars sent by the controlled WellenGenerator
	 * @param wellenGenerator the controlled WellenGenerator
	 */
	private SteuerLogik(String label, int xPos, int yPos, long rotPhase, long gruenPhase, Ampel ampel, long wellenZeitPunkt, WellenGenerator wellenGenerator)
	{
		super(label, xPos, yPos);
		/*
		for(Object[] ampelnList : ampelnListen)
		{
			for(String ampelList: (ArrayList<String>) ampelnList[0])
			{
				//add ampel to a new collection OF THE SAME STRUCTURE for the constructor
			}
			//add the list of ampel OBJECTS and their times to something that will be controllable
		}
		
		For()
		{
			WellenGenerator w = WellenGenerator.getWellenGeneratorByLabel();//wellenGenerator);
		}
		*/
		this.myAmpel = ampel;
		this.rotPhase = rotPhase;
		this.gruenPhase = gruenPhase;
		this.wellenZeitPunkt = wellenZeitPunkt;
		this.myWellenGenerator = wellenGenerator;
		
		this.ampelWaitTime = this.gruenPhase;
		this.wellenGeneratorWaitTime = this.wellenZeitPunkt;
		SteuerLogik.instance = this;
	}
	
	/** create the SteuerLogik
	 * 
	 * @param label of this SteuerLogik 
	 * @param xPos x position of the SteuerLogik 
	 * @param yPos y position of the SteuerLogik
	 * @param ampelnListen the lists of Ampeln that run together, with their rotPhase and gruenPhase in the order of [ArrayList<Ampel>, rotPhase, gruenPhase]
	 * @param wellenGeneratoren the controlled WellenGenerator in an array with their associated wait time
	 */
	public static void create(String label, int xPos, int yPos, ArrayList<Object[]> ampelnListen, ArrayList<Object[]> wellenGeneratoren)
	{
		//new arraylist that will be passed to the constructor for ampelnListen
		ArrayList<Object[]> tempAmpelnListen = new ArrayList<Object[]>();
		
		//for each element of the overarching ampeln list
		for(int i = 0; i<ampelnListen.size(); i++)
		{
			//get the ArrayList of Ampel labels
			ArrayList<String> tempList = (ArrayList<String>) ampelnListen.get(i)[0];
			//for the list of Ampel labels
			for(int j = 0; j<tempList.size(); j++)
			{
				//get the Ampel associated with the label
				Ampel a = Ampel.getAmpelByLabel( tempList.get(j) );
				//add the Ampel to the collection of Ampeln OF THE SAME STRUCTURE for the constructor
				
			}
			//add the list of Ampel OBJECTS and their times to something that will be controllable
		}
		
		//new arraylist that will be passed to the constructor for wellenGeneratoren
		ArrayList<Object[]> tempWellenGeneratoren = new ArrayList<Object[]>();
		//for each element of the wellenGeneratoren list
		for(int i = 0; i<wellenGeneratoren.size(); i++)
		{
			//get the WellenGenerator by its label
			WellenGenerator w = WellenGenerator.getWellenGeneratorByLabel( (String) wellenGeneratoren.get(i)[0] );
			//add the WellenGenerator to the collection of WellenGeneratoren OF THE SAME STRUCTURE for the constructor
		}
		
		new SteuerLogik(label, xPos, yPos, rotPhase, gruenPhase, a, wellenZeitPunkt, w);
	}
	
	/**void run method to overwrite the actor's method to avoid sleeping*/
	@Override
	public void run() {
				
		//run the actor
		while(true){
					
			try {
							
			//let the thread sleep for a little time
			//without that we've got a running problem 
			//Actor.sleep(Simulation.CLOCKBEAT);
					
				act(); 
				
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**void act method to overwrite the actor's method to avoid sleeping*/
	private synchronized void act(){
		
		/* 
		 * Let the thread wait only, if the simulation is still not running or, 
		 * more important, if there is no more work to do for the moment
		 */
		
		if ((!Simulation.isRunning) || (!work())){	
			/*
			//wait until a wake up (notify) instruction comes in
			try {
				Statistics.show(this.getLabel() + " wait()");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
			
	}

	/** work methode die immer laeuft waerend die Simulation laeuft
	 * @return boolean that depends on whether there is work left to do (Currently only returns false)
	 */
	protected boolean work()
	{
		long simTime = Simulation.getGlobalTime();
		
		if(simTime > ampelWaitTime)
		{
			updateAmpeln(this.myAmpel);
			
			if(isGruen)
				this.ampelWaitTime = Simulation.getGlobalTime() + rotPhase;
			else
				this.ampelWaitTime = Simulation.getGlobalTime() + gruenPhase;
			isGruen = !isGruen;
		}
		if(simTime > wellenGeneratorWaitTime)
		{
			this.updateWellenGenerator(this.myWellenGenerator);
			this.wellenGeneratorWaitTime = Simulation.getGlobalTime() + this.wellenZeitPunkt;
		}
		return false;
	}
	
	/**change state of Ampeln (form Green to Red, and from Red to Green)*/
	private void updateAmpeln(Ampel a)
	{
		a.wakeUp();
		a.switchState();
	}
	
	/**send WellenGenerator a notice that it should send cars*/
	private void updateWellenGenerator(WellenGenerator w)
	{
		w.wakeUp();
		w.sendWave();
	}
	
	/** get the SteuerLogik instance
	 * 
	 * @return the SteuerLogik instance
	 */
	public static SteuerLogik getSteuerLogik()
	{
		return SteuerLogik.instance;
	}
	
	/**A private inner Class that is used to regulate which Ampel set or WellenGenerator is next
	 * has a central integer that is incremented until it reaches a given maximum
	 * when it is incremented and becomes larger than the maximum, it returns to value 0
	 */
	private class overflowTicker
	{
		/**the current integer tick*/
		private int tick = 0;
		/**the maximum integer tick before returning back to 0*/
		private int max = 0;
		
		/** (Default) Constructor
		 * assumes start of 0
		 * @param max maximum integer value before returning to 0 
		 */
		overflowTicker(int max)
		{
			this.max = max;
		}
		/** Specific Constructor
		 * @param max maximum integer value before returning to 0 
		 * @param start start value in case a Class needs to start with a value other than 0
		 */
		overflowTicker(int max, int start)
		{
			this(max);
			this.tick = start;
		}
		
		/** Increment the ticker and return the new value
		 * 
		 * @return new tick value
		 */
		int tick()
		{
			this.increment();
			if(this.tick>max)
				this.tick = 0;
			return this.tick;
		}
		/** Increment method that adds 1 to the ticker */
		void increment()
		{
			this.tick = this.tick+1;
		}
		/**get tick value*/
		int getTick()
		{
			return this.tick;
		}
	}
}
