
import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Christophe
 */
public class Panel1 {
    public static void main(String args[]) {
        Panel1 gui = new Panel1();
        gui.go();
    }
    
    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        
        panel.setBackground(Color.darkGray);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button = new JButton("shock me");
        JButton buttonTwo = new JButton("bliss");
        
        panel.add(button);
        panel.add(buttonTwo);
        
        frame.getContentPane().add(BorderLayout.EAST,panel);
        frame.setSize(250,200);
        frame.setVisible(true);
    }
}
