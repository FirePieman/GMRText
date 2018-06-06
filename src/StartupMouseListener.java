/*
 * Handles moving from the startup window to the next pane
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

class StartupMouseListener
extends MouseAdapter {
	Startup startup;
	
    StartupMouseListener(Startup s) {
    	this.startup = s;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        Main.setId(startup.textField.getText());
        Main.setAuth(startup.textField_1.getText());
        startup.setVisible(false);
        startup.dispose();
    }
}
