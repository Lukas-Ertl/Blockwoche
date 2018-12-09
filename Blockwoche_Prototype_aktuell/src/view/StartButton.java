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
 * @author Jaeger, Schmidt edited by Team 4
 * @version 2018-12
 */
@SuppressWarnings("serial")
public class StartButton extends JButton implements ActionListener{

	public StartButton(){
		super("START");
		this.addActionListener(this);
		
	}
	
	/**
	 * edited by Team 4
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//set the simulation on
		Simulation.isRunning = true;
	}

	
}
