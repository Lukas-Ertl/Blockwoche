package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StackedBarChart
{
	private final int PARAL_DIST = 10;
	private final int ADJ_DIST = 20;
	
	private final int LINE_BUFFER = 25;
	private final int BAR_BUFFER = 25;
	
	private final Color LINE_COLOR = Color.BLACK;
	
    private String label;
    private String xLabel;
    private String[] barLabels;
    
    private Integer[][] intArr;
    private Color[] colArr;
    
    private Color backgroundColor;
    
    private int displayMax = 10;
	
    public static void main(String[] args)
    {
    	Integer[][] intArr = new Integer[3][3];
    	intArr[2][0] = 10;
    	intArr[2][1] = 3;
    	intArr[2][2] = 3;
    	intArr[1][0] = 5;
    	intArr[1][1] = 7;
    	intArr[1][2] = 1;
    	intArr[0][0] = 10;
    	intArr[0][1] = 3;
    	intArr[0][2] = 3;
    	String[] strArr = new String[3];
    	strArr[0] = "Szenario 1";
    	strArr[1] = "Szenario 2";
    	strArr[2] = "Szenario 3";
    	Color[] c = new Color[3];
    	c[0] = Color.GREEN;
    	c[1] = Color.BLUE;
    	c[2] = Color.MAGENTA;
    	Color backCol = Color.CYAN;
        StackedBarChart s = new StackedBarChart(intArr, "Stacked Bar Chart Frame", "Waiting Time", strArr, intArr, backCol, c);
    }

    private void createAndShowGUI()
    {
        JFrame f = new JFrame(this.label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add( new MyPanel() );//xLabel, barLabels, intArr) );
        f.pack();
        f.setVisible(true);
    }
    
    public StackedBarChart(Integer[][] bars, String label, String xLabel, String[] barLabels, Integer[][] intArr, Color backgroundColor, Color[] colArr)
    {
    	this.backgroundColor = backgroundColor;
    	this.colArr = colArr;
    	this.intArr = intArr;
    	this.barLabels = barLabels;
    	this.label = label;
    	this.xLabel = xLabel;
    	
    	createAndShowGUI();
    }
    
    private class MyPanel extends JPanel
	{
		private MyPanel()
	    {
	    	int biggestBar = 0;
	    	for(int i=0; i<intArr.length; i++)
	    	{
	    		int total = 0;
	    		for(int j=0; j<intArr[i].length; j++)
	    		{
	    			total += intArr[i][j];
	    		}
	    		if(total>biggestBar)
	    			biggestBar = total;
	    	}
	    	displayMax = biggestBar;
	        setBorder(BorderFactory.createLineBorder(LINE_COLOR));
	    }
	
	    public Dimension getPreferredSize()
	    {
	        return new Dimension(500,350);
	    }
	    
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
}