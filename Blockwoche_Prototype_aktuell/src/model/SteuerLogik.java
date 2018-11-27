package model;

import controller.Simulation;

public class SteuerLogik extends Actor
{
	//time that an Ampel stays green
	private long gruenPhase;
	//time that an Ampel stays red
	private long rotPhase;
	//TODO replace with a list of Ampeln
	private Ampel myAmpel;
	//time before the next wave of cars start TODO change to list
	private long wellenZeitPunkt;
	//TODO replace with list of WellenGenerators
	private WellenGenerator myWellenGenerator;
	
	//the time being waited for to send signals to Ampeln and WellenGeneratorn
	private long ampelWaitTime, wellenGeneratorWaitTime;
	//boolean for deciding between rotPhase and gruenPhase time
	private boolean isGruen = true;
	
	protected SteuerLogik(String label, int xPos, int yPos, long rotPhase, long gruenPhase, Ampel ampel, long wellenZeitPunkt, WellenGenerator wellenGenerator)
	{
		super(label, xPos, yPos);
		this.myAmpel = ampel;
		this.rotPhase = rotPhase;
		this.gruenPhase = gruenPhase;
		this.wellenZeitPunkt = wellenZeitPunkt;
		this.myWellenGenerator = wellenGenerator;
		
		this.ampelWaitTime = this.gruenPhase;
		this.wellenGeneratorWaitTime = this.wellenZeitPunkt;
	}
	
	public static void create(String label, int xPos, int yPos, long rotPhase, long gruenPhase, String ampel, long wellenZeitPunkt, String wellenGenerator)
	{
		Ampel a = Ampel.getAmpel(ampel);
		WellenGenerator w = WellenGenerator.getWellenGeneratorByLabel(wellenGenerator);
		new SteuerLogik(label, xPos, yPos, rotPhase, gruenPhase, ampel, wellenZeitPunkt, wellenGenerator);
	}
	

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
		}
		if(simTime > wellenGeneratorWaitTime)
		{
			updateWellenGenerator(this.myWellenGenerator);
			this.wellenGeneratorWaitTime = Simulation.getGlobalTime() + this.wellenZeitPunkt;
		}
		return false;
	}
	
	//change state of Ampeln (form Green to Red, and from Red to Green)
	private void updateAmpeln(Ampel a)
	{
		a.switchState();
	}
	
	//send WellenGenerator a notice that it should send cars
	private void updateWellenGenerator(WellenGenerator w)
	{
		w.sendWave();
	}
}
