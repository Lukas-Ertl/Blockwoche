package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StackedBarChart
{
    private String label;
    private String xLabel;
    private String[] barLabels;
    
    private Integer[][] intArr;
	
    public static void main(String[] args)
    {
    	Integer[][] intArr = new Integer[2][1];
    	intArr[1][0] = 5;
    	intArr[0][0] = 10;
    	String[] strArr = new String[2];
    	strArr[0] = "LOOK A STRING";
    	strArr[1] = "LOOK A STRING 2";
        StackedBarChart s = new StackedBarChart(intArr, "Stacked Bar Chart Frame", "Waiting Time", strArr, intArr);
    }

    private void createAndShowGUI()
    {
        JFrame f = new JFrame(this.label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add( new MyPanel(xLabel, barLabels, intArr) );
        f.pack();
        f.setVisible(true);
    }
    
    public StackedBarChart(Integer[][] bars, String label, String xLabel, String[] barLabels, Integer[][] intArr)
    {
    	this.intArr = intArr;
    	this.barLabels = barLabels;
    	this.label = label;
    	this.xLabel = xLabel;
    	
    	createAndShowGUI();
    }
}

class MyPanel extends JPanel
{
	private final int PARAL_DIST = 10;
	private final int ADJ_DIST = 20;
	
	private final int LINE_BUFFER = 25;
	private final int BAR_BUFFER = 25;
	
	private String xLabel;
	private String[] barLabels;
	
	private Integer[][] intArr;
	
	private int displayMax = 10;
	
    public MyPanel(String xLabel, String[] barLabels, Integer[][] intArr)
    {
    	this.intArr = intArr;
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
    	this.displayMax = biggestBar;
    	this.barLabels = barLabels;
    	this.xLabel = xLabel;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(500,350);
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //fill the Panel
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //paint the x axis label
        g.setColor(Color.BLACK);
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
        for(int i=0; i<barLabels.length; i++)
        {
        	//paint the bar labels
	        int tempSpacer = LINE_BUFFER + BAR_BUFFER;
	        int tempPlacer = (getWidth()- tempSpacer ) / barLabels.length;
	        		//paint the string
	        g.drawString(barLabels[i], i*tempPlacer + tempSpacer, getHeight()-5);
	        
	        //paint the bars themselves
	        int barWidth = tempPlacer / 2;
	        int barHeight = (getHeight()-LINE_BUFFER-BAR_BUFFER)*intArr[i][0] / displayMax;
	        g.fillRect(i*tempPlacer + tempSpacer, getHeight()-(LINE_BUFFER)-barHeight, barWidth, barHeight);
        }
    }  
}