package controller;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * New main Class to run multiple Scenarios automatically and creates a gui to
 * Choose from json or xml scenarios per mouseclick on a corresponding button.
 * 
 * @author Team 4
 * @version 11-2018
 *
 */

public class Main {

	JFrame theFrame;

	/** Scenario Folder */
	private static String scenarioFolder;

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("XML1")) {
			e.setSource(scenarioFolder = "Szenario 1");

		} else if (e.getActionCommand().equals("XML2")) {
			e.setSource(scenarioFolder = "Szenario 2");
		} else if (e.getActionCommand().equals("JSON1")) {

		}

		else if (e.getActionCommand().equals("JSON2")) {

		}
	}

	/**
	 * starts the simulation and creates the Frame with buttons to choose from
	 * whether u want xml or json.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChoosingButtons c = new ChoosingButtons();
		c.createAndShowGUI();

		/*
		 * scenarioFolder = "Szenario 1"; System.out.println("in Main " +
		 * scenarioFolder); new Simulation(scenarioFolder);
		 * 
		 * /* // Try to run all scenarios after each other automatically (abandoned
		 * because of time) while(scenario_1.getIsRunning() == false) { }
		 * while(scenario_1.getIsRunning() == true) { } scenarioFolder = "Szenario 2";
		 * System.out.println("in Main "+scenarioFolder); Simulation scenario_2 = new
		 * Simulation(scenarioFolder);
		 */
	}

}

class ChoosingButtons extends JFrame {

	public ChoosingButtons() {

	}

	@SuppressWarnings("unused")
	public void createAndShowGUI()

	{

		ButtonListener xmlListener = new ButtonListener();
		JFrame theFrame = new JFrame("Auswahlfeld");

		JPanel contentPanel = new JPanel(new FlowLayout());
		theFrame.setContentPane(contentPanel);

		JButton xmlButton1 = new JButton("XML1");
		xmlButton1.addActionListener(xmlListener);
		JButton xmlButton2 = new JButton("XML2");
		xmlButton2.addActionListener(xmlListener);

		JButton jsonButton1 = new JButton("JSON1");
		jsonButton1.addActionListener(xmlListener);
		JButton jsonButton2 = new JButton("JSON2");
		jsonButton2.addActionListener(xmlListener);

		theFrame.getContentPane().add(xmlButton1);
		theFrame.getContentPane().add(xmlButton2);
		theFrame.getContentPane().add(jsonButton1);
		theFrame.getContentPane().add(jsonButton2);
		theFrame.pack();
		theFrame.setSize(400, 200);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ButtonListener implements ActionListener {

	/** Scenario Folder */
	private static String scenarioFolder;

	public void actionPerformed(ActionEvent e) {
		
		

		if (e.getActionCommand().equals("XML1")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "XML");
		} else if (e.getActionCommand().equals("XML2")) {
			ButtonListener.scenarioFolder = "Szenario 2";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "XML");
		} else if (e.getActionCommand().equals("JSON1")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "JSON");
		} else if (e.getActionCommand().equals("JSON2")) {
			ButtonListener.scenarioFolder = "Szenario 1";
			System.out.println("in Main " + scenarioFolder);
			new Simulation(scenarioFolder, "JSON");
		}
	}
}
