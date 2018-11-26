package model;

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
	
	protected SteuerLogik(String label, int xPos, int yPos) {
		super(label, xPos, yPos);
		
	}

	protected boolean work()
	{
		
		return false;
	}
	
	//change state of Ampeln (form Green to Red, and from Red to Green)
	private void updateAmpeln()
	{
		
	}
	
	//send WellenGenerator a notice that it should send cars
	private void updateWellenGenerator()
	{
		
	}
	
	//temp class
	private class WellenGenerator
	{
		public void sendWave() {};
	}
}
