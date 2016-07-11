import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Modifier;
import javax.swing.*;
/**
 *
 * @author Christophe
 */
public class JTextField1 implements ActionListener{
    public static void main(String args[]) {
        JTextField1 gui = new JTextField1();
        gui.go();
    }
    
    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JTextField field = new JTextField(20);
        JLabel label = new JLabel("Your Name");
        
        field.setText("test");
        field.addActionListener(this);
        
        // Select highlight the text in the field
        field.selectAll();
        field.requestFocus();
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JButton button = new JButton("shock me");
        
        panel.add(button);
        panel.add(field);
        panel.add(label);
        
        frame.getContentPane().add(BorderLayout.NORTH,panel);
        frame.setSize(250,200);
        frame.setVisible(true);
    }
    
    public void actionPerformed (ActionEvent event) {
       
    }
}
