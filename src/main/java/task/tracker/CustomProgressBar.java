package task.tracker;

import javax.swing.LookAndFeel;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBar extends BasicProgressBarUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.uninstallBorder(progressBar); //Uninstalls the LAF border for both button and label of combo box.
    }
}
