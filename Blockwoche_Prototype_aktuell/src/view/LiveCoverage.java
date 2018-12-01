package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import controller.Simulation;
import model.CustomPoint;

public final class LiveCoverage implements Observer
{
	private static LiveCoverage instance = null;
	private int autosWaiting = 0;
	private PlotterPane graph;

	private LiveCoverage()
	{
		graph = new PlotterPane(new ArrayList<CustomPoint>(), 400, 200, true, "Zeit", "Autos die Warten", "Staut es sich?");
		graph.addPoint( new CustomPoint(0,0) );
	}
	public static void create()
	{
		if(LiveCoverage.instance == null)
			LiveCoverage.instance = new LiveCoverage();
	}

	public static LiveCoverage getInstance()
	{
		return LiveCoverage.instance;
	}
	
	@Override
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
