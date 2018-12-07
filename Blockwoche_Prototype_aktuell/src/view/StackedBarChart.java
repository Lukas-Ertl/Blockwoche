package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A Stacked Bar Graph class for display
 * 
 * @author Team 4
 * @version 2018-12
 */
public class StackedBarChart
{
	/** Distance to the parallel running axis */
	private final int PARAL_DIST = 10;
	/** Distance to the adjacent running axis */
	private final int ADJ_DIST = 20;
	
	/** Buffer for line spacing */
	private final int LINE_BUFFER = 25;
	/** Buffer for upper window spacing */
	private final int BAR_BUFFER = 50;
	
	/** Color of base lines */
	private final Color LINE_COLOR = Color.BLACK;
	
	/** Overall Chart Label */
    private String label;
    /** X Axist Label */
    private String xLabel;
    /** Array of Labels for the bars */
    private String[] barLabels;
    
    /** Values of the bars */
    private Integer[][] intArr;
    /** Array for the different colors of the bar elements */
    private Color[] colArr;
    
    /** Background color */
    private Color backgroundColor;
    
    /** Maximum x axis label value, defaults to 10 */
    private int displayMax = 10;
	
    /** example bar chart */
    public static void example()
    {
    	Integer[][] intArr = new Integer[3][];
    	intArr[0] = new Integer[7];
    	intArr[1] = new Integer[3];
    	intArr[2] = new Integer[2];
    	
    	intArr[0][0] = 10;
    	intArr[0][1] = 2;
    	intArr[0][2] = 2;
    	intArr[0][3] = 2;
    	intArr[0][4] = 2;
    	intArr[0][5] = 2;
    	intArr[0][6] = 2;
    	intArr[1][0] = 5;
    	intArr[1][1] = 7;
    	intArr[1][2] = 1;
    	intArr[2][0] = 10;
    	intArr[2][1] = 3;
    	
    	String[] strArr = new String[3];
    	strArr[0] = "Szenario 1";
    	strArr[1] = "Szenario 2";
    	strArr[2] = "Szenario 3";
    	
    	Color[] c = new Color[7];
    	c[0] = Color.decode("#00FF00");
    	c[1] = Color.decode("#0000FF");
    	c[2] = Color.decode("#00AAAA");
    	c[3] = Color.decode("#FFFFFF");
    	c[4] = Color.decode("#000000");
    	c[5] = Color.decode("#FFFF00");
    	c[6] = Color.decode("#FF00FF");
    	
    	Color backCol = Color.CYAN;
    	
        try {
			StackedBarChart s = new StackedBarChart("Stacked Bar Chart Frame", "Waiting Time", strArr, intArr, backCol, c);
		} catch (InputArraysNotEquivalent e) {
			e.printStackTrace();
		}
    }

    /** displays the chart in a new frame */
    private void createAndShowGUI()
    {
        JFrame f = new JFrame(this.label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add( new MyPanel() );
        f.pack();
        f.setVisible(true);
    }
    
    /**
     * 
     * @param label chart label
     * @param xLabel x axis label
     * @param barLabels label array for the bars
     * @param intArr values for the bar elements
     * @param backgroundColor background color
     * @param colArr array of colors for the bar elements
     * @throws InputArraysNotEquivalent throws exception if there aren't enough colors for bar elements, or not enough labels for bars
     */
    public StackedBarChart(String label, String xLabel, String[] barLabels, Integer[][] intArr, Color backgroundColor, Color[] colArr) throws InputArraysNotEquivalent
    {
    	//find out what the largest number of elements in a bar is (useful for checking if there are enough colors in the array to display these elements)
    	int biggestArray = 0;
    	//find out how large the large bar is (useful for scaling the drawing)
    	int biggestBar = 0;
    	for(int i=0; i<intArr.length; i++)
    	{
    		if(intArr[i].length>biggestArray)
    			biggestArray = intArr[i].length;
    		
    		int total = 0;
    		for(int j=0; j<intArr[i].length; j++)
    		{
    			total += intArr[i][j];
    		}
    		if(total>biggestBar)
    			biggestBar = total;
    	}
    	displayMax = biggestBar;
    	
    	//check there are enough colors for bar elements, and enough labels for bars
    	if( barLabels.length != intArr.length || colArr.length < biggestArray )
    		throw new InputArraysNotEquivalent("Input arrays for StackedBarChart do not match");
    	
    	//save values
    	this.backgroundColor = backgroundColor;
    	this.colArr = colArr;
    	this.intArr = intArr;
    	this.barLabels = barLabels;
    	this.label = label;
    	this.xLabel = xLabel;
    	
    	//display the Chart
    	createAndShowGUI();
    }
    
    /**
     * internal Panel that actually displays the Chart
     * 
     * @author Team 4
     *@version 2018-12
     */
    private class MyPanel extends JPanel
	{
    	/**
    	 * Constructor for Panel, creates a Border
    	 */
		private MyPanel()
	    {
	        setBorder(BorderFactory.createLineBorder(LINE_COLOR));
	    }
	
		/**
		 * returns the preferred window size
		 */
	    public Dimension getPreferredSize()
	    {
	        return new Dimension(500,350);
	    }
	    
	    /** paints the graph, is called automatically */
	    protected void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        
	        //fill the Panel
	        g.setColor(backgroundColor);
	        g.fillRect(0, 0, getWidth(), getHeight());
	        
	        //paint the x axis label
	        g.setColor(LINE_COLOR);
	        g.drawString(xLabel, PARAL_DIST, ADJ_DIST);
	        
	        //paint the x and y axis
	        g.drawLine(LINE_BUFFER, getHeight()-LINE_BUFFER, LINE_BUFFER, LINE_BUFFER);
	        g.drawLine(LINE_BUFFER, getHeight()-LINE_BUFFER, getWidth()-ADJ_DIST, getHeight()-LINE_BUFFER);
	        
	        //paint the marker values
	        int maxValueMarkerPosition = getHeight()-LINE_BUFFER-45;
	        for(int i=0; i<maxValueMarkerPosition; i=i+50)
	        {
	        	g.drawString( Integer.toString( i*displayMax / maxValueMarkerPosition ), 2, getHeight()-(i+LINE_BUFFER) );
	        }
	        
	        //paint the bars
	        for(int i=0; i<intArr.length; i++)
	        {
	        	//paint the bar labels
		        int tempSpacer = LINE_BUFFER + BAR_BUFFER;
		        int tempPlacer = (getWidth()- tempSpacer ) / barLabels.length;
		        
		        //paint the string
		        g.setColor(LINE_COLOR);
		        g.drawString(barLabels[i], i*tempPlacer + tempSpacer, getHeight()-5);
		        
		        //paint the bars themselves
		        int multiBarPlacement = 0;
		        int barWidth = tempPlacer / 2;
		        for(int j=0; j<intArr[i].length; j++)
		        {
		        	g.setColor(colArr[j]);
		        	
		        	int barVisualHeight = (getHeight()-tempSpacer)*intArr[i][j] / displayMax;
		        	int barXPosition = i*tempPlacer + tempSpacer;//
		        	int barYPosition = getHeight()-(LINE_BUFFER)-barVisualHeight - multiBarPlacement;
		        	g.fillRect(barXPosition, barYPosition, barWidth, barVisualHeight);
		        	multiBarPlacement += barVisualHeight;
		        }
	        }
	    }  
	}
    
    /**
     * Exception for inequivalent inputs in the StackedBarChart constructor
     * 
     * @author Team 4
     * @version 2018-12
     */
    public class InputArraysNotEquivalent extends Exception
    {
    	private String exceptionMessage;
    	
    	InputArraysNotEquivalent(String exceptionMessage)
    	{
    		this.exceptionMessage = exceptionMessage;
    	}
    	public String toString()
    	{
    		return(exceptionMessage);
    	}
    }
}