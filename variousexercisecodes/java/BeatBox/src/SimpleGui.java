import javax.swing.*;
/**
 *
 * @author Christophe
 */
public class SimpleGui {
    public static void main(String args[]){
        JFrame frame = new JFrame();
        JButton button = new JButton("cllick me");
        
        // quit the program as soon as the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(button);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
}
