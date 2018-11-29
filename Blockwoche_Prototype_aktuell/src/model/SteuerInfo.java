package model;

import java.util.ArrayList;

public class SteuerInfo {
	/**arraylist of arraylist for Ampeln*/
	private ArrayList<ArrayList<Ampel>> ampelSets;
	/**arraylist of doubles for rotPhase and gruenPhase*/
	private ArrayList<Double> rotPhasenSets, gruenPhasenSets;
	
	/**arraylist of WellenGeneratoren*/ 
	private ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren;
	/**arraylist of WellenGeneratoren timings*/
	private ArrayList<Double> wellenGeneratorTimes;
	
	SteuerInfo(ArrayList<ArrayList<Ampel>> ampelSets, ArrayList<Double> rotPhasenSets, ArrayList<Double> gruenPhasenSets,
			ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren, ArrayList<Double> wellenGeneratorTimes)
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
	public double getGruenPhase(int set)
	{
		return this.gruenPhasenSets.get(set);
	}
	public double getRotPhase(int set)
	{
		return this.rotPhasenSets.get(set);
	}
	public double getWellenGeneratorTime(int set)
	{
		return this.wellenGeneratorTimes.get(set);
	}
}
