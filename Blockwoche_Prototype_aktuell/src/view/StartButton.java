package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import controller.Simulation;
import model.CustomPoint;
import model.SteuerLogik;


/**
 * A simple JButton class for a start button
 * 
 * @author Jaeger, Schmidt
 * @version 2016-07-07
 */
@SuppressWarnings("serial")
public class StartButton extends JButton implements ActionListener{

	public StartButton(){
		super("START");
		this.addActionListener(this);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//set the simulation on
		Simulation.isRunning = true;
		
		List<CustomPoint> dataPoints = new ArrayList<CustomPoint>();
		dataPoints.add(new CustomPoint(10, 10));
		dataPoints.add(new CustomPoint(20, 20));
		dataPoints.add(new CustomPoint(30, 40));
		PlotterPane p = new PlotterPane(dataPoints, 500, 500, true, "something", "is for", "losers");
		
		//wake up the SteuerLogik -> lets the simulation run
		SteuerLogik.getSteuerLogik().wakeUp();
	}

	
}
