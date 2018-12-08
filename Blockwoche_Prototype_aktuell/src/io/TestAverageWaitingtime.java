package io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAverageWaitingtime {

	@Test
	void test1() {
		PersistendFile p1 = new PersistendFile(); 
		

		assertEquals(20,p1.calculateAverageWaitingtime(1000, 50));
		// calculateAverageWaitingtime(1000, 50);
	}
	
	
	@Test
	void test2() {
		PersistendFile p1 = new PersistendFile(); 
		

		assertEquals(0,p1.calculateAverageWaitingtime(0, 50));
		// calculateAverageWaitingtime(1000, 50);
	}
	
	@Test
	void test3() {
		PersistendFile p1 = new PersistendFile(); 
		

		assertEquals(40,p1.calculateAverageWaitingtime(400, 10));
		// calculateAverageWaitingtime(1000, 50);
	}

}
