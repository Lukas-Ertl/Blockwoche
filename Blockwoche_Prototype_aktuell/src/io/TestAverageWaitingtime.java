package io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAverageWaitingtime {

	@Test
	void test1() {
 
		long vergleich = 20;

		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(1000, 50));

	}
	
	
	@Test
	void test2() {

		long vergleich = 0;

		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(0, 50));

	}
	
	@Test
	void test3() {

		long vergleich = 40;
		
		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(400, 10));

	}

}
