package task.tracker;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.LookAndFeel;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CustomComboBox extends BasicComboBoxUI {
    @Override
    protected void installDefaults() {
        super.installDefaults();
        LookAndFeel.uninstallBorder(comboBox); //Uninstalls the LAF border for both button and label of combo box.
    }

    @Override
    protected JButton createArrowButton() {
        final Color background = Color.BLACK;      			//Default is UIManager.getColor("ComboBox.buttonBackground").
        final Color pressedButtonBorderColor = Color.BLACK; //Default is UIManager.getColor("ComboBox.buttonShadow"). The color of the border of the button, while it is pressed.
        final Color triangle = Color.BLACK;               	//Default is UIManager.getColor("ComboBox.buttonDarkShadow"). The color of the triangle.
        final Color highlight = background;               	//Default is UIManager.getColor("ComboBox.buttonHighlight"). Another color to show the button as highlighted.
        final JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, background, pressedButtonBorderColor, triangle, highlight);
        button.setName("ComboBox.arrowButton"); //Mandatory, as per BasicComboBoxUI#createArrowButton().
        return button;
    }
}
