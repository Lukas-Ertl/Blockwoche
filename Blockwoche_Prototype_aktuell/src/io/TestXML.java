package io;

import static org.junit.Assert.*;

import org.junit.Test;

import controller.Simulation;
import model.Actor;
import model.Ampel;
import model.EndStation;

public class TestXML {

	@Test
	public void test() {
		
		
		
	
		
		Simulation s = new Simulation("SzenarioTest", "XML");
		Simulation.isRunning = true;
		//while(!s.getIsRunning()) {}
		while(s.getIsRunning()) {}

//		//get Endstation
//		for(Actor theActor: Actor.getAllActors()) {
//			
//			System.out.println("ein actor");
//			
//			if(theActor.getClass() == EndStation.class) {
//				
//				System.out.println("endstation gefunden");
//				
//			}
//			
//			
//			
//		}
		
		
		
		//assertEquals(1,);
		
	}

}
