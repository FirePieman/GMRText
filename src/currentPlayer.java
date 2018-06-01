/*
 * Decompiled with CFR 0_115.
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class currentPlayer
extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public static void main(String[] args) {
        try {
            currentPlayer dialog = new currentPlayer();
            dialog.setDefaultCloseOperation(2);
            dialog.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public currentPlayer() {
        this.setBounds(100, 100, 450, 300);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setLayout(new FlowLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add((Component)this.contentPanel, "Center");
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(2));
        this.getContentPane().add((Component)buttonPane, "South");
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}
