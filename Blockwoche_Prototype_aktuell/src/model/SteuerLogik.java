package model;

import controller.Simulation;

/**
 * Controller for all Ampeln and WellenGeneratoren
 * implemented as a Singleton
 * 
 * @author Team 4
 * @version 2018-11
 */
public final class SteuerLogik extends Actor
{
	/**the single instance of SteuerLogik*/
	private static SteuerLogik instance = null;
	
	/**used to stop the thread*/
	private boolean run = true;
	
	/**all of the Ampeln, WellenGeneratoren and all wait times (read from XML)*/
	private SteuerInfo steuerInfo;
	
	/**wait times for Ampel sets and WellenGenerator sets*/
	private long ampelWaitTime, wellenGeneratorWaitTime;
	
	/**tickers to keep track of what Ampeln or WellenGeneratoren are up*/
	private OverflowTicker ampTick, welTick;
	
	/** (private) Constructor for the SteuerLogik
	 * 
	 * @param label of this SteuerLogik 
	 * @param xPos x position of the SteuerLogik 
	 * @param yPos y position of the SteuerLogik
	 * @param info class used to store information about Ampeln, WellenGeneratoren and wait times for clean code
	 */
	private SteuerLogik(String label, int xPos, int yPos, SteuerInfo info)
	{
		super(label, xPos, yPos);
		
		this.steuerInfo = info;
		int ampelSetSize = this.steuerInfo.getAmpelSetSize(); 
		this.ampTick = new OverflowTicker( ampelSetSize-1, ampelSetSize-1 );
		this.welTick = new OverflowTicker( this.steuerInfo.getWellenGeneratorSetSize()-1 );
		this.ampelWaitTime = (long) 0;
		this.wellenGeneratorWaitTime = this.steuerInfo.getWellenGeneratorTime(0); 
		
		//set the one instance to be this
		SteuerLogik.instance = this;
		this.updateAmpeln( ampelSetSize-1 );
	}
	
	/** create the SteuerLogik
	 * only creates a new SteuerLogik if there is not already a SteuerLogik object
	 * 
	 * @param label of this SteuerLogik 
	 * @param xPos x position of the SteuerLogik 
	 * @param yPos y position of the SteuerLogik
	 * @param info class used to store information about Ampeln, WellenGeneratoren and wait times for clean code
	 */
	public static void create(String label, int xPos, int yPos, SteuerInfo info)
	{
		if(SteuerLogik.instance == null)
			new SteuerLogik(label, xPos, yPos, info);			
	}
	
	/**void run method to overwrite the actor's method to avoid sleeping*/
	@Override
	public void run() {
		if( this.steuerInfo.getAmpelSetSize()>1 )
		{
			//run the actor
			while(run)
			{
				try
				{
					act(); 
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			//run the actor
			while(run)
			{
				try
				{					
					act(true); 
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
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
	/**
	 * void act method to overwrite the actor's method to avoid sleeping
	 * @param single is this a single light scenario?
	 */
	private synchronized void act(boolean single){
		
		/* 
		 * Let the thread wait only, if the simulation is still not running or, 
		 * more important, if there is no more work to do for the moment
		 */
		if ((!Simulation.isRunning) || (!work(single))){	
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
	
	/**to end the simulation*/
	public static void end()
	{
		SteuerLogik.instance.run = false;
	}

	/** work method that always runs while the Simulation is running
	 * @return boolean that depends on whether there is work left to do (Currently only returns false)
	 */
	protected boolean work()
	{
		//current time of the simulation
		long simTime = Simulation.getGlobalTime();
		//if the time until the Ampel should switch has arrived
		if(simTime > ampelWaitTime)
		{
			//wake up Ampeln and have them switch states
			this.updateAmpeln( this.ampTick.getTick() );
			this.updateAmpeln( this.ampTick.tick() );

			//set the new wait time (which is different depending on if the Ampel is green or red)
			this.ampelWaitTime = Simulation.getGlobalTime() + this.steuerInfo.getGruenPhase( this.ampTick.getTick() );
		}
		
		//if the time until the WellenGenerator should send has arrived
		if(simTime > wellenGeneratorWaitTime)
		{
			//set the new wait time
			this.wellenGeneratorWaitTime = Simulation.getGlobalTime() + this.steuerInfo.getWellenGeneratorTime( this.welTick.getTick() );
			//wake up WellenGeneratoren and have them send a new wave
			this.updateWellenGenerator( this.welTick.tick() );
		}
		return false;
	}
	/** work method that always runs while the Simulation is running
	 * @return boolean that depends on whether there is work left to do (Currently only returns false)
	 * @param single is this a single light scenario?
	 */
	protected boolean work(boolean single)
	{
		//current time of the simulation
		long simTime = Simulation.getGlobalTime();
		
		//if the time until the Ampel should switch has arrived
		if(simTime > ampelWaitTime)
		{
			//wake up Ampeln and have them switch states
			updateAmpeln( this.ampTick.getLast() );

			//set the new wait time (which is different depending on if the Ampel is green or red)
			this.ampelWaitTime = Simulation.getGlobalTime() + this.steuerInfo.getGruenPhase( this.ampTick.tick() );
		}
		
		//if the time until the WellenGenerator should send has arrived
		if(simTime > wellenGeneratorWaitTime)
		{
			//set the new wait time
			this.wellenGeneratorWaitTime = Simulation.getGlobalTime() + this.steuerInfo.getWellenGeneratorTime( this.welTick.getTick() );
			//wake up WellenGeneratoren and have them send a new wave
			this.updateWellenGenerator( this.welTick.tick() );
		}
		return false;
	}
	
	/**
	 * change state of Ampeln in the given set (from Green to Red, and from Red to Green)
	 * @param set which Ampel set should switch
	 */
	private void updateAmpeln(int set)
	{
		for( Ampel a: this.steuerInfo.getAmpelSet(set) )
		{
			a.wakeUp();
			a.switchState();
		}
	}
	
	/**
	 * send WellenGenerator a notice that it should send cars
	 * @param set which set of WellenGeneratoren should switch 
	 */
	private void updateWellenGenerator(int set)
	{
		for( WellenGenerator w: this.steuerInfo.getWellenGeneratorSet(set) )
		{
			w.wakeUp();
			w.sendWave();
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
			this.increment();
			return this.tick;
		}
		/**Increment method that adds 1 to the ticker
		 * 
		 * included in case the ticker needs to be incremented without a return value
		 */
		void increment()
		{
			this.tick++;
			if(this.tick>max)
				this.tick = 0;
		}
		/**
		 * get tick value
		 * @return tick what tick we are on
		 */
		int getTick()
		{
			return this.tick;
		}
		/**
		 * get the previously ticked value
		 * @return tick-1 return the previous tick (if there is one, if tick is currently at 0, return "overflowed" max)
		 */
		int getLast()
		{
			if(this.tick==0)
				return this.max;
			else
				return this.tick-1;
		}
	}
}
