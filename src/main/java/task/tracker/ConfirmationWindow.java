package task.tracker;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;

public class ConfirmationWindow extends JOptionPane {

	private static final long serialVersionUID = 1L;

	private static Font font = new Font("Arial", Font.BOLD, 20);
	private static JOptionPane frame;

	public static int showConfirmDialog(Component parentComponent, Object message) throws HeadlessException {
		return showConfirmDialog(parentComponent, message, UIManager.getString("OptionPane.titleText"),
				YES_NO_OPTION);
	}

//	public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.WHITE);
//        g.setFont(font);
//        
//        String counterStr = "Total tasks: "+df0.format(tasks)+"\nTime in task: "+df2.format(milToSec(timeInTask))+"\nTotal time: "+df2.format(milToSec(totalTime))+"\nAHT: "+df2.format(averageHandleTime)+"\nTPH: "+df2.format(tasksPerHour);
//
//        printedVariables = 0;
//        maxPrintedVariables = printedVariables;
//        for (String line : counterStr.split("\n")) {
//        	widthNow = g.getFontMetrics().stringWidth(line);
//        	heightNow = g.getFontMetrics().getHeight();
//            printedVariables++;
//            g.drawString(line, (getWidth()-widthNow)/2, printedVariables*heightNow);
//            if(maxPrintedVariables < printedVariables) maxPrintedVariables = printedVariables;
//            if((width-40)< widthNow) width = widthNow+40;
//            if((height)< heightNow) height = (int)(heightNow);
//        }
//        if(frame.getWidth() != width || frame.getHeight() != height*maxPrintedVariables+40+g.getFontMetrics().getDescent()){
//        	frame.setSize(width, height*maxPrintedVariables+40+g.getFontMetrics().getDescent());
//        	System.out.println("Size change "+width+"x"+height);
//        	System.out.println("Now sizes "+widthNow+"x"+heightNow);
//        }
//        System.out.println(maxPrintedVariables);
//    }
}
