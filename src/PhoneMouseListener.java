/*
 * Decompiled with CFR 0_115.
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

class PhoneMouseListener
extends MouseAdapter {
    private final /* synthetic */ PlayerContact val$playa;
    private final /* synthetic */ JComboBox val$comboBox;
    private final Phone phone; //mas
    private JTextField textField; //mas

    PhoneMouseListener(Phone phone, JTextField textField, PlayerContact playerContact, JComboBox jComboBox) { //mas
        this.val$playa = playerContact;
        this.val$comboBox = jComboBox;
        this.phone = phone; //mas
        this.textField = textField; //mas
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        this.val$playa.setNumber(textField.getText()); //mas
        this.val$playa.setCarrier((String)this.val$comboBox.getSelectedItem()); 
        phone.setVisible(false); //mas
        phone.dispose(); //mas
    }
}
