package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.PersistentFile;

/**
 * Tests the average calculation of PersistentFile
 * @author Team 4
 * @version 2018-12
 */
class TestAverageWaitingtime {

	/** test 1 */
	@Test
	void test1() {
 
		int a = 1000;
		int b = 50;
		long vergleich = a/b;

		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(a, b));

	}
	
	/** test 2 */
	@Test
	void test2() {

		int a = 0;
		int b = 50;
		long vergleich = a/b;

		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(a, b));

	}
	
	/** test 3 */
	@Test
	void test3() {

		int a = 400;
		int b = 10;
		long vergleich = a/b;
		
		assertEquals(vergleich,PersistentFile.calculateAverageWaitingtime(a, b));

	}

}
