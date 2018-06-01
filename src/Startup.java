import java.awt.event.MouseListener;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JDialog;

// 
// Decompiled by Procyon v0.5.30
// 

public class Startup extends JDialog
{
    private JPanel contentPane;
    JTextField textField;
    JTextField textField_1;

    
    public Startup() {
        this.setModal(true);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 450, 300);
        (this.contentPane = new JPanel()).setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JLabel lblNewLabel = new JLabel("ID:");
        lblNewLabel.setBounds(36, 145, 31, 27);
        this.contentPane.add(lblNewLabel);
        final JLabel label = new JLabel("AuthKey: ");
        label.setBounds(10, 85, 57, 27);
        this.contentPane.add(label);
        final JLabel lblPleaseEnterIn = new JLabel("Please enter in your auth key and player ID:");
        lblPleaseEnterIn.setFont(new Font("Tahoma", 0, 16));
        lblPleaseEnterIn.setBounds(36, 11, 344, 56);
        this.contentPane.add(lblPleaseEnterIn);
        (this.textField = new JTextField()).setText("76561198056991903");
        this.textField.setBounds(61, 88, 301, 20);
        this.contentPane.add(this.textField);
        this.textField.setColumns(10);
        (this.textField_1 = new JTextField()).setText("AQJA4OXA02QF");
        this.textField_1.setBounds(61, 148, 301, 20);
        this.contentPane.add(this.textField_1);
        this.textField_1.setColumns(10);
        final JButton btnOkay = new JButton("Okay");
        btnOkay.addMouseListener((MouseListener)new StartupMouseListener(this));
        btnOkay.setBounds(160, 216, 89, 23);
        this.contentPane.add(btnOkay);
    }
}
