/*
 * Main window, displays the current players turn and amount of time until their turn is skipped.
 * Generated with Java Swing UI.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Display
extends JFrame {
    private JPanel contentPane;

    public Display(String player, String game, String time, int notifications, int x, int y) {
        this.setDefaultCloseOperation(3);
        this.setBounds(x, y, 550, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        JLabel lblCurrentPlayer = new JLabel("It is currently " + player + "'s turn in " + game);
        lblCurrentPlayer.setFont(new Font("Tahoma", 0, 20));
        lblCurrentPlayer.setBounds(42, 11, 469, 71);
        this.contentPane.add(lblCurrentPlayer);
        JLabel lblTimeLeft = new JLabel("They have " + time + " to do their turn");
        lblTimeLeft.setBounds(85, 80, 487, 14);
        this.contentPane.add(lblTimeLeft);
        JLabel lblNumNotifications = new JLabel("They have been texted " + notifications + " times");
        lblNumNotifications.setBounds(85, 105, 209, 14);
        this.contentPane.add(lblNumNotifications);
    }
}
