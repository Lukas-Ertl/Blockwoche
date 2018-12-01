package view;

import java.util.Observable;
import java.util.Observer;

public final class LiveCoverage implements Observer
{
	private static LiveCoverage instance = null;
	private int autosWaiting = 0;

	private LiveCoverage()	{}
	public void create()
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
		//redraw graph
	}

}
