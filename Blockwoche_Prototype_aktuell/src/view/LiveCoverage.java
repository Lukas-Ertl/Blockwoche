package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import controller.Simulation;
import model.CustomPoint;

/**
 * Creates a live coverage of the cars waiting in line by watching car objects' inner observable class
 * Singleton!
 * Observer!
 * 
 * @author Team 4
 *
 */
public final class LiveCoverage implements Observer
{
	/** single instance of the LiveCoverage */
	private static LiveCoverage instance = null;
	/** number of cars waiting */
	private int autosWaiting = 0;
	/** the graph for display */
	private PlotterPane graph;

	/**
	 * creates a new graph starting at point (0:0)
	 */
	private LiveCoverage()
	{
		graph = new PlotterPane(new ArrayList<CustomPoint>(), 400, 200, true, "Zeit", "Autos die Warten", "Staut es sich?");
		graph.addPoint( new CustomPoint(0,0) );
	}
	/**
	 * creates a new LiveCoverage if one does not exist
	 */
	public static void create()
	{
		if(LiveCoverage.instance == null)
			LiveCoverage.instance = new LiveCoverage();
	}

	/**
	 * 
	 * @return instance the single instance of LiveCoverage
	 */
	public static LiveCoverage getInstance()
	{
		if(LiveCoverage.instance == null)
			LiveCoverage.instance = new LiveCoverage();
		return LiveCoverage.instance;
	}
	
	@Override
	/**
	 * updates the graph with new points depending on the number of cars currently waiting
	 * 
	 * @param o the observable that notified the LiveCoverage
	 * @param arg the Object that the observable sent
	 */
	public void update(Observable o, Object arg) {
		boolean waiting = (boolean) arg;
		if(waiting)
			autosWaiting++;
		else
			autosWaiting--;
		this.graph.addPoint( new CustomPoint( (int) Simulation.getGlobalTime(), autosWaiting ) );
		//redraw graph
	}

}
