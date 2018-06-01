/*
 * Decompiled with CFR 0_115.
 */
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class StartupSwingAction
extends AbstractAction {
	
    public StartupSwingAction() { 
    	this.putValue("Name", "SwingAction");
        this.putValue("ShortDescription", "Some short description");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
