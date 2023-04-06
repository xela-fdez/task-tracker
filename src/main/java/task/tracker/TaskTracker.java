package task.tracker;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class TaskTracker extends JPanel implements NativeKeyListener {
    
    private static final long serialVersionUID = 1L;
    
    //This are the buttons being used, by default they are in order Enter, PgUp, End, PLUS, MINUS, HOME
    private final int addButton = NativeKeyEvent.VC_ENTER;
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
    private static float targetPercent = 0F;
    
    private boolean isPaused = false;
    
    private int printedVariables = 0;
    private int maxPrintedVariables = 0;
    
    private static int width = 1;
    private static int height = 1;
    private int widthNow = 1;
    
    private static int fontSize = 20;
    private static Font font = new Font("Arial", Font.BOLD, fontSize);
    private static Color fontColor = Color.WHITE;
    
    private static JFrame frame;
    private static JComboBox<String> chooseAdProgram;
    private static JProgressBar progressBar;
    
    static DecimalFormat df2 = new DecimalFormat("0.00");
    static DecimalFormat df0 = new DecimalFormat("0");
    static DecimalFormat percent = new DecimalFormat("0%");
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
        if (e.getKeyCode() == addButton && !isPaused) { 
        	tasks++;
        	targetPercent+=(1/AdProgram.SPTarget);
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
            targetPercent-=(1/AdProgram.SPTarget);
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
        	if(0.9F*width > 126) {
        		font = font.deriveFont(font.getSize()*0.9F);
            	width=(int) (0.9F*width);
            	height=(int) (0.9F*height);
        	}
        	
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
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(fontColor);
        g.setFont(font);
        g.setPaintMode();
        
        
        
        String counterStr = "Total tasks: "+df0.format(tasks)+"\nTime in task: "+TimeFormatter.timeFormat(timeInTask)+"\nTotal time: "+TimeFormatter.timeFormat(totalTime)+"\nAHT: "+df2.format(averageHandleTime)+"\nTPH: "+df2.format(tasksPerHour);

        printedVariables = 0;
        maxPrintedVariables = printedVariables+2; 
        
        for (String line : counterStr.split("\n")) {
        	widthNow = g.getFontMetrics().stringWidth(line);
        	height = g.getFontMetrics().getHeight();
            printedVariables++;
            g.drawString(line, (getWidth()-widthNow)/2, printedVariables*height);
            
            if(maxPrintedVariables < printedVariables+2) maxPrintedVariables = printedVariables+2;
            if((width-40)< widthNow) width = widthNow+40;
        }
        
        if(frame.getWidth() != width || frame.getHeight() != height*maxPrintedVariables+40+g.getFontMetrics().getDescent()){
        	frame.setSize(width+15, height*maxPrintedVariables+40+g.getFontMetrics().getDescent());
//        	frame.setSize(width+15+1000, 1000+height*maxPrintedVariables+40+g.getFontMetrics().getDescent());
        	System.out.println("Size change "+width+"x"+height);
        	System.out.println("Now sizes "+widthNow+"x"+height);
        }
        
        progressBar.setBounds(0,height*(maxPrintedVariables-2)+g.getFontMetrics().getDescent(),width,height);
        progressBar.setFont(font);
        progressBar.setValue((int) targetPercent);
        
        
        chooseAdProgram.setBounds(0, height*(maxPrintedVariables-1)+g.getFontMetrics().getDescent(),width, height+1);
        chooseAdProgram.setFont(font);
		chooseAdProgram.setBackground(Color.LIGHT_GRAY);
		chooseAdProgram.setForeground(Color.BLACK);
		
		chooseAdProgram.getComponent(0).setBounds(0,0,0,0);
		
		//((JLabel) chooseAdProgram.getComponent(1)).setBounds(0, height*(maxPrintedVariables-1),10, 10);
		
		frame.getContentPane().add(progressBar);
        frame.getContentPane().add(chooseAdProgram);
        
        chooseAdProgram.repaint();
        progressBar.repaint();
        
        this.setBounds(0, 0, width, height*maxPrintedVariables);
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
        
        chooseAdProgram = new JComboBox<String>(); 
        chooseAdProgram.setUI(new CustomComboBox());
	chooseAdProgram.setFont(font);
	chooseAdProgram.setModel(new DefaultComboBoxModel<String>(new String[] { "SP", "SD", "SB", "SBV", "Stores", "Books", "Store Spotlight"}));
	((JLabel) chooseAdProgram.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	chooseAdProgram.setFocusable(false);

	UIManager.put("ProgressBar.selectionBackground", Color.WHITE);
	progressBar = new JProgressBar(0,100);
	progressBar.setUI(new CustomProgressBar());
	progressBar.setFont(font);
	progressBar.setBackground(Color.BLACK);
	progressBar.setForeground(new Color(76,175,80));
	progressBar.setValue((int) targetPercent);
	progressBar.setStringPainted(true);
	progressBar.setBounds(0, 0, 300, 300);
	progressBar.setVisible(true);


	frame.repaint();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}
   
