package controller;

/**
 * New main Class to run multiple Scenarios automatically
 * @author Team 4
 * @version 11-2018
 *
 */

public class Main {

	/** Scenario Folder */
	private static String scenarioFolder;
	
	/**
	 * starts the simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
		
		
		
		
		
		scenarioFolder = "Szenario 1";
		System.out.println("in Main "+scenarioFolder);
		Simulation scenario_1 = new Simulation(scenarioFolder);
		while(scenario_1.getIsRunning() == false) {
		}
		while(scenario_1.getIsRunning() == true) {
		}
		scenarioFolder = "Szenario 2";
		System.out.println("in Main "+scenarioFolder);
		Simulation scenario_2 = new Simulation(scenarioFolder);
	}

}
