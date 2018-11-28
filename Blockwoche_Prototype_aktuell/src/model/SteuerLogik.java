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
	
	/**List of Ampeln that SteuerLogik controls with their rot- and gruenPhasen times*/
	private ArrayList<Object[]> myAmpeln;
	
	/**List of WellenGeneratoren that SteuerLogik controls with their associated wellenZeitPunkt times*/
	private ArrayList<Object[]> myWellenGeneratoren;
	
	/**the time being waited for to send signals to Ampeln and WellenGeneratorn*/
	private long ampelWaitTime, wellenGeneratorWaitTime;
	
	/**boolean used to differentiate between rotPhase and gruenPhase*/
	private boolean isGruen = true;
	
	/**tickers to keep track of what Ampeln or WellenGeneratoren are up*/
	private OverflowTicker ampTick, welTick;
	
	/** (private) Constructor for the SteuerLogik
	 * 
	 * @param label of this SteuerLogik 
	 * @param xPos x position of the SteuerLogik 
	 * @param yPos y position of the SteuerLogik
	 * @param ampelnListen the lists of Ampeln that run together, with their rotPhase and gruenPhase in the order of [ArrayList<Ampel>, rotPhase, gruenPhase]
	 * @param wellenGeneratoren the controlled WellenGenerator in an array with their associated wait time
	 */
	private SteuerLogik(String label, int xPos, int yPos, ArrayList<Object[]> ampelnListen, ArrayList<Object[]> wellenGeneratoren)
	{
		super(label, xPos, yPos);
		
		this.myAmpeln = ampelnListen;
		this.myWellenGeneratoren = wellenGeneratoren;
		SteuerLogik.instance = this;
		this.ampTick = new OverflowTicker( ampelnListen.size() );
		this.welTick = new OverflowTicker( ampelnListen.size() );
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
			//add a new set of Ampeln to the arraylist that will be passed to the constructor
			tempAmpelnListen.add( new Object[3] );
			tempAmpelnListen.get(i)[0] = new ArrayList<Ampel>();
			
			//get the ArrayList of Ampel labels
			ArrayList<String> tempList = (ArrayList<String>) ampelnListen.get(i)[0];
			//for the list of Ampel labels
			for(int j = 0; j<tempList.size(); j++)
			{
				//get the Ampel associated with the label
				Ampel a = Ampel.getAmpelByLabel( tempList.get(j) );
				//add the Ampel to the collection of Ampeln OF THE SAME STRUCTURE for the constructor
				((ArrayList<Ampel>) tempAmpelnListen.get(i)[0]).add(a);
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
		
		new SteuerLogik(label, xPos, yPos, tempAmpelnListen, tempWellenGeneratoren);
	}
	
	/**void run method to overwrite the actor's method to avoid sleeping*/
	@Override
	public void run() {
				
		//run the actor
		while(true){
					
			try {					
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
			if(isGruen)
				this.ampelWaitTime = Simulation.getGlobalTime() + (long) this.myAmpeln.get( this.ampTick.getTick() )[2];
			else
				this.ampelWaitTime = Simulation.getGlobalTime() + (long) this.myAmpeln.get( this.ampTick.getTick() )[1];
			isGruen = !isGruen;

			updateAmpeln( this.ampTick.tick() );
		}
		
		if(simTime > wellenGeneratorWaitTime)
		{
			this.wellenGeneratorWaitTime = Simulation.getGlobalTime() + (long) this.myWellenGeneratoren.get( this.welTick.getTick() )[1];
			this.updateWellenGenerator( this.welTick.tick() );
		}
		return false;
	}
	
	/**change state of Ampeln (from Green to Red, and from Red to Green)*/
	private void updateAmpeln(int set)
	{
		for(Ampel a: (ArrayList<Ampel>) this.myAmpeln.get(set)[0] )
		{
			a.wakeUp();
			a.switchState();
		}
	}
	
	/**send WellenGenerator a notice that it should send cars*/
	private void updateWellenGenerator(int set)
	{
		for(WellenGenerator w: (ArrayList<WellenGenerator>) this.myWellenGeneratoren.get(set)[0] )
		{
			w.wakeUp();
			w.switchState();
		}
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
	private class OverflowTicker
	{
		/**the current integer tick*/
		private int tick = 0;
		/**the maximum integer tick before returning back to 0*/
		private int max = 0;
		
		/** (Default) Constructor
		 * assumes start of 0
		 * @param max maximum integer value before returning to 0 
		 */
		OverflowTicker(int max)
		{
			this.max = max;
		}
		/** Specific Constructor
		 * @param max maximum integer value before returning to 0 
		 * @param start start value in case a Class needs to start with a value other than 0
		 */
		OverflowTicker(int max, int start)
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
			int val = this.tick;
			this.increment();
			return val;
		}
		/** Increment method that adds 1 to the ticker */
		void increment()
		{
			this.tick++;
			if(this.tick>max)
				this.tick = 0;
		}
		/**get tick value*/
		int getTick()
		{
			return this.tick;
		}
	}
}
