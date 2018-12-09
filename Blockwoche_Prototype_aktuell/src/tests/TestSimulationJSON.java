package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.Simulation;
import model.Actor;
import model.Ampel;
import model.EndStation;
import model.SteuerLogik;

/**
 * Tests the running of a XML simulation run
 * 
 * @author Team 4
 * @version 2018-12
 *
 */
public class TestSimulationJSON {

	@Test
	/**
	 * tests the JSON simulation by starting it and seeing if all cars arrive
	 */
	public void testJSON() {

		int outqueueSize = 0;

		Simulation s = new Simulation("SzenarioTest", "JSON");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Simulation.isRunning = true;

		// while(!s.getIsRunning()) {}
		while (s.getIsRunning()) {
			// int a = 0;
			// warum ????????
			System.out.print("");
		}

		// get Endstation
		for (Actor theActor : Actor.getAllActors()) {

			if (theActor.getClass() == EndStation.class) {
				// get size of outqueue of endstation
				outqueueSize = ((EndStation) theActor).getAllOutQueues().get(0).size();

			}

		}

		assertEquals(1, outqueueSize);

	}

}