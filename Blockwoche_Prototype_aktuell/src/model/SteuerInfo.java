package model;

import java.util.ArrayList;

public class SteuerInfo {
	/**arraylist of arraylist for Ampeln*/
	private ArrayList<ArrayList<Ampel>> ampelSets;
	/**arraylist of doubles for rotPhase and gruenPhase*/
	private ArrayList<Long> rotPhasenSets, gruenPhasenSets;
	
	/**arraylist of WellenGeneratoren*/ 
	private ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren;
	/**arraylist of WellenGeneratoren timings*/
	private ArrayList<Long> wellenGeneratorTimes;
	
	public SteuerInfo(ArrayList<ArrayList<Ampel>> ampelSets, ArrayList<Long> rotPhasenSets, ArrayList<Long> gruenPhasenSets,
			ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren, ArrayList<Long> wellenGeneratorTimes)
	{
		this.ampelSets = ampelSets;
		this.rotPhasenSets = rotPhasenSets;
		this.gruenPhasenSets = gruenPhasenSets;
		this.wellenGeneratoren = wellenGeneratoren;
		this.wellenGeneratorTimes = wellenGeneratorTimes;
	}
	
	public ArrayList<Ampel> getAmpelSet(int set)
	{
		return this.ampelSets.get(set);
	}
	public ArrayList<WellenGenerator> getWellenGeneratorSet(int set)
	{
		return this.wellenGeneratoren.get(set);
	}
	
	public long getGruenPhase(int set)
	{
		return this.gruenPhasenSets.get(set);
	}
	public long getRotPhase(int set)
	{
		return this.rotPhasenSets.get(set);
	}
	public long getWellenGeneratorTime(int set)
	{
		return this.wellenGeneratorTimes.get(set);
	}
	
	public int getAmpelSetSize()
	{
		return this.ampelSets.size();
	}
	public int getWellenGeneratorSetSize()
	{
		return this.wellenGeneratoren.size();
	}
}
