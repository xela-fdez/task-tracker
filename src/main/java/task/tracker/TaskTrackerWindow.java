package task.tracker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class TaskTrackerWindow implements NativeKeyListener{


    private static final long serialVersionUID = 1L;
    
    //This are the buttons being used, by default they are in order PgDown, PgUp, End, PLUS, MINUS, HOME
    private final int addButton = NativeKeyEvent.VC_PAGE_DOWN;
    private final int substractButton =  NativeKeyEvent.VC_PAGE_UP;
    private final int resetButton =  NativeKeyEvent.VC_END;
    private final int biggerUI = NativeKeyEvent.VC_EQUALS;
    private final int smallerUI = NativeKeyEvent.VC_MINUS;
    private final int pause = NativeKeyEvent.VC_HOME;
    
    //Variables
    private float tasks = 0;					//The raw number of tasks done
    //private float tasksValue = 0;				//The tasks value depending on Ad program chosen
    private long timeInTask = 0;
    private float averageHandleTime = 0;
    private float tasksPerHour = 0;
    private long totalTime = 0;
    private long startTime = 0;
    private long pauseTime = 0;
    
    //Text fields for printing the information
    JLabel textTasks;
    JLabel textTimeInTask;
    JLabel textAverageHandleTime;
    JLabel textTasksPerHour;
    JLabel textTotalTime;
    JLabel textStartTime;
    JLabel textPauseTime;
    
    private boolean isPaused = false;
    
    private int printedVariables = 0;
    private int maxPrintedVariables = 0;
    
    private static int width = 189;
    private static int height = 207;
    private int widthNow = 1;
    private int heightNow = 1;
    
    private static int fontSize = 20;
    private static Font font = new Font("Arial", Font.BOLD, fontSize);
    private static Color fontColor = Color.WHITE;
    private static Color backgroundColor = Color.BLACK;
    
    private JFrame frame;
    private JComboBox<String> chooseAdProgram;
    
    static DecimalFormat df2 = new DecimalFormat("0.00");
    static DecimalFormat df0 = new DecimalFormat("0");
    static DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
    
    /**
     * @wbp.parser.entryPoint
     */
    public TaskTrackerWindow() {
        GlobalScreen.addNativeKeyListener(this);
        initialize();
    }
    
    /**
     * @wbp.parser.entryPoint
     */
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskTrackerWindow window = new TaskTrackerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	
    	try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
	}
    
    private void initialize() {
    	frame = new JFrame("Failstack calculator"); // Creates the window
		frame.setResizable(false);
		frame.setBounds(0, 0, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(backgroundColor);	
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
		
		textTasks = new JLabel("Total tasks: "+df0.format(tasks));
	    textTimeInTask = new JLabel("Time in task: "+TimeFormatter.timeFormat(timeInTask));
	    textTotalTime = new JLabel("Total time: "+TimeFormatter.timeFormat(totalTime));
	    textAverageHandleTime = new JLabel("AHT: "+df2.format(averageHandleTime));
	    textTasksPerHour = new JLabel("TPH: "+df2.format(tasksPerHour));
	    
	    textTasks.setBounds(0, (height/6)*1, width, (height/6));
	    
		textTasks = new JLabel("Total tasks: "+df0.format(tasks));
    	System.out.println(new JLabel().getFontMetrics(font));
	}

	private void repaint() {
		textTasks.setText("Total tasks: "+df0.format(tasks));
		textTimeInTask.setText("Time in task: "+TimeFormatter.timeFormat(timeInTask));
		textTotalTime.setText("Total time: "+TimeFormatter.timeFormat(totalTime));
		textAverageHandleTime.setText("AHT: "+df2.format(averageHandleTime));
		textTasksPerHour.setText("TPH: "+df2.format(tasksPerHour));
		
		formatJLabel(textTasks);
		formatJLabel(textTimeInTask);
		formatJLabel(textTotalTime);
		formatJLabel(textAverageHandleTime);
		formatJLabel(textTasksPerHour);
		
		textTasks.setBounds(0, (height/6)*1, width, height);
		
	}
    
//    private void repaint() {
//		String counterStr = "Total tasks: "+df0.format(tasks)+"\nTime in task: "+TimeFormatter.timeFormat(timeInTask)+"\nTotal time: "+TimeFormatter.timeFormat(totalTime)+"\nAHT: "+df2.format(averageHandleTime)+"\nTPH: "+df2.format(tasksPerHour);
//	
//	    printedVariables = 0;
//	    maxPrintedVariables = printedVariables+1;
//	    for (String line : counterStr.split("\n")) {
//	    	
//	    	widthNow = g.getFontMetrics().stringWidth(line);
//	    	heightNow = g.getFontMetrics().getHeight();
//	        printedVariables++;
//	        frame.drawString(line, (getWidth()-widthNow)/2, printedVariables*heightNow);
//	        if(maxPrintedVariables < printedVariables+1) maxPrintedVariables = printedVariables+1;
//	        if((width-40)< widthNow) width = widthNow+40;
//	        if((height)< heightNow) height = (int)(heightNow);
//	    }
//	    if(frame.getWidth() != width || frame.getHeight() != height*maxPrintedVariables+40+g.getFontMetrics().getDescent()){
//	    	frame.setSize(width, height*maxPrintedVariables+40+g.getFontMetrics().getDescent());
//	    	System.out.println("Size change "+width+"x"+height);
//	    	System.out.println("Now sizes "+widthNow+"x"+heightNow);
//	    }
//	    
//	    chooseAdProgram.setBounds(0, height*(maxPrintedVariables-1),width-15, height+g.getFontMetrics().getDescent()+1);
//	    chooseAdProgram.setFont(font);
//		chooseAdProgram.setBackground(Color.BLACK);
//		chooseAdProgram.setForeground(fontColor);
//		((JLabel) chooseAdProgram.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//	
//		//((JLabel) chooseAdProgram.getComponent(1)).setBounds(0, height*(maxPrintedVariables-1),10, 10);
//		
//	    frame.getContentPane().add(chooseAdProgram);
//	    
//	    chooseAdProgram.repaint();
//	    
//	    frame.setBounds(0, 0, width, height*maxPrintedVariables);
//	}
    
    
    
	private void formatJLabel (JLabel label) {
		label.setFont(font);
	}

    private float milToSec(long miliseconds) {
    	return (float)miliseconds/1000;
    }
    
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == addButton && !isPaused) { 
        	tasks++;
        	if(startTime == 0) {
        		startTime = System.currentTimeMillis();
        	}
        	else {
        		timeInTask=System.currentTimeMillis()-startTime;
        		totalTime+=timeInTask;
        		averageHandleTime=milToSec(totalTime)/tasks;
        		tasksPerHour = (tasks/milToSec(totalTime))*3600;
        		startTime = System.currentTimeMillis();
        	}
            repaint();
            System.out.println("Total tasks: "+tasks+"Start time: "+TimeFormatter.timeFormat(startTime)+"\nTime in task: "+TimeFormatter.timeFormat(timeInTask)+"\nTotal time: "+TimeFormatter.timeFormat(totalTime)+"\nAHT: "+averageHandleTime+"\nTPH: "+tasksPerHour);
        } else if (e.getKeyCode() == substractButton && !isPaused) {
            tasks--;
            repaint();
        }
        if (e.getKeyCode() == resetButton  && frame.isFocused()) {
        	//int confirmation = JOptionPane.showConfirmDialog(new Component(), "Are you sure you want to reset all numbers?");        	 
        	int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset all numbers?","Reset Confirmation",JOptionPane.YES_NO_OPTION);
        	if(confirmation == 0) {
        		tasks=0;
                timeInTask = 0;
                averageHandleTime = 0;
                tasksPerHour = 0;
                totalTime = 0;
                startTime = System.currentTimeMillis();
                repaint();
        	}
        }
        if (e.getKeyCode() == biggerUI && frame.isFocused()) {
        	font = font.deriveFont(font.getSize()*1.1F);
        	repaint();
        }
        if (e.getKeyCode() == smallerUI && frame.isFocused()) {
        	font = font.deriveFont(font.getSize()*0.9F);
        	width=(int) (0.9F*width);
        	height=(int) (0.9F*height);
        	repaint();
        }
        
        if (e.getKeyCode() == pause && frame.isFocused() && !isPaused) {
        	fontColor = Color.RED;
        	isPaused = true;
        	pauseTime = System.currentTimeMillis();
        	repaint();
        }
        else if(e.getKeyCode() == pause && frame.isFocused() && isPaused) {
        	fontColor = Color.WHITE;
        	isPaused = false;
        	startTime+=System.currentTimeMillis()-pauseTime;
        	pauseTime = 0;
        	repaint();
        }
        System.out.println(e.getKeyCode());
    }
    
    public void nativeKeyReleased(NativeKeyEvent e) {}
    
    public void nativeKeyTyped(NativeKeyEvent e) {}

}
