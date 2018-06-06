/*
 * The window that handles associating a player with their phone number
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Phone
extends JDialog {
    private JPanel contentPane;
    private JTextField textField;

    public Phone(String playerName, PlayerContact playa) {
        this.setModal(true);
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        JLabel lblPlayer = new JLabel("Player:");
        lblPlayer.setBounds(31, 105, 46, 14);
        this.contentPane.add(lblPlayer);
        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setBounds(155, 105, 94, 14);
        this.contentPane.add(lblPhoneNumber);
        JLabel lblNewLabel = new JLabel("Phone Carrier:");
        lblNewLabel.setBounds(313, 105, 111, 14);
        this.contentPane.add(lblNewLabel);
        this.textField = new JTextField();
        this.textField.setBounds(155, 130, 77, 20);
        this.textField.setText("4255555555");
        this.contentPane.add(this.textField);
        this.textField.setColumns(10);
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(313, 130, 64, 20);
        comboBox.addItem("AT&T");
        comboBox.addItem("Verizon");
        comboBox.addItem("T-Mobile");
        this.contentPane.add(comboBox);
        JLabel lblPlayer_1 = new JLabel(playerName);
        lblPlayer_1.setBounds(31, 133, 125, 14);
        this.contentPane.add(lblPlayer_1);
        JButton btnNext = new JButton("Next");
        btnNext.addMouseListener((MouseListener)new PhoneMouseListener(this, textField, playa, (JComboBox)comboBox));
        btnNext.setBounds(335, 227, 89, 23);
        this.contentPane.add(btnNext);
        JLabel lblPleaseEnterIn = new JLabel("Please enter the phone number and carrier of the current player:");
        lblPleaseEnterIn.setFont(new Font("Tahoma", 0, 12));
        lblPleaseEnterIn.setBounds(31, 57, 376, 37);
        this.contentPane.add(lblPleaseEnterIn);
    }
}
