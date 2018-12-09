package model;

import java.util.ArrayList;

/**
 * Stores the information required by SteuerLogik to enable clearer and more concise code
 * @author Team 4
 *
 */
public class SteuerInfo {
	/**arraylist of arraylist for Ampeln*/
	private ArrayList<ArrayList<Ampel>> ampelSets;
	/**arraylist of doubles for gruenPhase*/
	private ArrayList<Long> gruenPhasenSets;
	
	/**arraylist of WellenGeneratoren*/ 
	private ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren;
	/**arraylist of WellenGeneratoren timings*/
	private ArrayList<Long> wellenGeneratorTimes;
	
	/**
	 * 
	 * @param ampelSets the Ampel sets
	 * @param gruenPhasenSets times for green lights
	 * @param wellenGeneratoren the WellenGenerator sets
	 * @param wellenGeneratorTimes times for when the WellenGenerator should send cars
	 */
	public SteuerInfo(ArrayList<ArrayList<Ampel>> ampelSets, ArrayList<Long> gruenPhasenSets,
			ArrayList<ArrayList<WellenGenerator>> wellenGeneratoren, ArrayList<Long> wellenGeneratorTimes)
	{
		this.ampelSets = ampelSets;
		this.gruenPhasenSets = gruenPhasenSets;
		this.wellenGeneratoren = wellenGeneratoren;
		this.wellenGeneratorTimes = wellenGeneratorTimes;
	}
	
	/**
	 * @param set which Ampel set
	 * @return AmpelSet for said set number
	 */
	public ArrayList<Ampel> getAmpelSet(int set)
	{
		return this.ampelSets.get(set);
	}
	/**
	 * @param set which WellenGenerator set
	 * @return WellenGeneratorSet for said set number
	 */
	public ArrayList<WellenGenerator> getWellenGeneratorSet(int set)
	{
		return this.wellenGeneratoren.get(set);
	}
	
	/**
	 * @param set which Ampel set
	 * @return Green times for said set
	 */
	public long getGruenPhase(int set)
	{
		return this.gruenPhasenSets.get(set);
	}
	/**
	 * @param set which WellenGenerator set
	 * @return Send times for said set
	 */
	public long getWellenGeneratorTime(int set)
	{
		return this.wellenGeneratorTimes.get(set);
	}
	
	/**
	 * returns the size of the AmpelSets arraylist
	 * @return ampelSets size
	 */
	public int getAmpelSetSize()
	{
		return this.ampelSets.size();
	}
	/**
	 * returns the size of the WellenGeneratorSets
	 * @return WellenGeneratoren size
	 */
	public int getWellenGeneratorSetSize()
	{
		return this.wellenGeneratoren.size();
	}
}
