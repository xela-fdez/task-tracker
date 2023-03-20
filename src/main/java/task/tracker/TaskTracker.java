package task.tracker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class TaskTracker extends JPanel implements NativeKeyListener {
    
    private static final long serialVersionUID = 1L;
    
    //This are the buttons being used, by default they are in order PgDown, PgUp, End, PLUS, MINUS
    private final int addButton = NativeKeyEvent.VC_PAGE_DOWN;
    private final int substractButton =  NativeKeyEvent.VC_PAGE_UP;
    private final int resetButton =  NativeKeyEvent.VC_END;
    private final int biggerUI = NativeKeyEvent.VC_EQUALS;
    private final int smallerUI = NativeKeyEvent.VC_MINUS;
    
    //Variables
    private float tasks = 0;
    private long timeInTask = 0;
    private float averageHandleTime = 0;
    private float tasksPerHour = 0;
    private long totalTime = 0;
    private static long startTime = 0;
    private int printedVariables = 0;
    private int maxPrintedVariables = 0;
    private static int width = 1;
    private static int height = 1;
    private int widthNow = 1;
    private int heightNow = 1;
    private static int fontSize = 20;
    private static Font font = new Font("Arial", Font.BOLD, fontSize);
    private static JFrame frame;
    static DecimalFormat df2 = new DecimalFormat("0.00");
    static DecimalFormat df0 = new DecimalFormat("0");
    static DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
    
    public TaskTracker() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        GlobalScreen.addNativeKeyListener(this);
    }
    
    private float milToSec(long miliseconds) {
    	return (float)miliseconds/1000;
    }
    
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == addButton) { 
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
        } else if (e.getKeyCode() == substractButton) {
            tasks--;
            repaint();
        }
        else if (e.getKeyCode() == resetButton  && frame.isFocused()) {
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
        System.out.println(e.getKeyCode());
    }
    
    public void nativeKeyReleased(NativeKeyEvent e) {}
    
    public void nativeKeyTyped(NativeKeyEvent e) {}
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(font);
        
        String counterStr = "Total tasks: "+df0.format(tasks)+"\nTime in task: "+TimeFormatter.timeFormat(timeInTask)+"\nTotal time: "+TimeFormatter.timeFormat(totalTime)+"\nAHT: "+df2.format(averageHandleTime)+"\nTPH: "+df2.format(tasksPerHour);

        printedVariables = 0;
        maxPrintedVariables = printedVariables;
        for (String line : counterStr.split("\n")) {
        	widthNow = g.getFontMetrics().stringWidth(line);
        	heightNow = g.getFontMetrics().getHeight();
            printedVariables++;
            g.drawString(line, (getWidth()-widthNow)/2, printedVariables*heightNow);
            if(maxPrintedVariables < printedVariables) maxPrintedVariables = printedVariables;
            if((width-40)< widthNow) width = widthNow+40;
            if((height)< heightNow) height = (int)(heightNow);
        }
        if(frame.getWidth() != width || frame.getHeight() != height*maxPrintedVariables+40+g.getFontMetrics().getDescent()){
        	frame.setSize(width, height*maxPrintedVariables+40+g.getFontMetrics().getDescent());
        	System.out.println("Size change "+width+"x"+height);
        	System.out.println("Now sizes "+widthNow+"x"+heightNow);
        }
        System.out.println(maxPrintedVariables);
    }
    
    public static void main(String[] args){    
    	decimalFormatSymbols.setDecimalSeparator('.');
        df2.setDecimalFormatSymbols(decimalFormatSymbols);
        df0.setDecimalFormatSymbols(decimalFormatSymbols);
    	TaskTracker ui = new TaskTracker();
        frame = new JFrame();
        frame.setTitle("Task Tracker");
        frame.add(ui);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
   