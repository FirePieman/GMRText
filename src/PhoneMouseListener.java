/*
 * Setting a players phone number listenter.
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

class PhoneMouseListener
extends MouseAdapter {
    private final  PlayerContact player;
    private final  JComboBox comboBox;
    private final Phone phone; 
    private JTextField textField; 

    PhoneMouseListener(Phone phone, JTextField textField, PlayerContact playerContact, JComboBox jComboBox) {
        this.player = playerContact;
        this.comboBox = jComboBox;
        this.phone = phone; 
        this.textField = textField; 
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        this.player.setNumber(textField.getText()); 
        this.player.setCarrier((String)this.comboBox.getSelectedItem()); 
        phone.setVisible(false); 
        phone.dispose(); 
    }
}
