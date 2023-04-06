package task.tracker;

import javax.swing.LookAndFeel;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CustomComboBox extends BasicComboBoxUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.uninstallBorder(comboBox); //Uninstalls the LAF border for both button and label of combo box.
    }
}
