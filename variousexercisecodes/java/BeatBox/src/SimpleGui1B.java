import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author Christophe
 */
public class SimpleGui1B implements ActionListener{
    JButton button;
    
    public static void main(String args[]){
        SimpleGui1B gui = new SimpleGui1B();
        gui.go();
    }
    
    public void go() {
        JFrame frame = new JFrame();
        button = new JButton("cllick me");
        button.addActionListener(this);
        
        // quit the program as soon as the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.getContentPane().add(button);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent event) {
        button.setText("I've been clicked");
    }
}
